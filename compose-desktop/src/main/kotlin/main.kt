import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import co.touchlab.kermit.Logger
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer
import com.mikepenz.common.di.initKoin
import com.mikepenz.common.repository.StoryblokRepositoryInterface
import com.mikepenz.storyblok.sdk.model.Story
import kotlinx.serialization.json.*
import java.awt.Desktop
import java.net.URI

private val koin = initKoin(enableNetworkLogs = true).koin

val logger = koin.get<Logger>()

fun main() = application {
    Window(title = "Mike Penz Blog | Storyblok mp SDK", onCloseRequest = ::exitApplication) {
        val repo = koin.get<StoryblokRepositoryInterface>()

        var storyState by remember { mutableStateOf(emptyList<Story>()) }
        var selectedStory by remember { mutableStateOf<Story?>(null) }
        var showOpenSource by remember { mutableStateOf(false) }

        LaunchedEffect(true) {
            storyState = repo.fetchStories()
            selectedStory = storyState.first()
        }


        val storyAwareUriHandler = object : UriHandler {
            override fun openUri(uri: String) {
                if (uri.startsWith("http")) {
                    Desktop.getDesktop().browse(URI(uri))
                } else {
                    val slug = uri.split("/").lastOrNull()
                    selectedStory = storyState.firstOrNull { it.slug == slug }
                }
            }
        }

        val backButton: @Composable (() -> Unit)? = if (showOpenSource) {
            {
                IconButton(onClick = { showOpenSource = !showOpenSource }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        } else {
            null
        }

        StoryblokTheme {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Mike Penz Blog | Storyblok mp SDK") },
                        navigationIcon = backButton,
                        actions = {
                            IconButton(onClick = { showOpenSource = !showOpenSource }) { Icon(Icons.Filled.Info, "Licenses") }
                        }
                    )
                }
            ) {
                Row(Modifier.fillMaxSize()) {
                    if (!showOpenSource) {
                        Box(Modifier.width(250.dp).fillMaxHeight().background(MaterialTheme.colors.background)) {
                            StoryList(storyState, selectedStory) {
                                selectedStory = it
                                logger.e { "${it.name}" }
                            }
                        }

                        Divider(modifier = Modifier.width(1.dp).fillMaxHeight())

                        Box(Modifier.fillMaxHeight()) {
                            CompositionLocalProvider(LocalUriHandler provides storyAwareUriHandler) {
                                selectedStory?.let {
                                    StoryDetailsView(it)
                                }
                            }
                        }
                    } else {
                        LibrariesContainer(useResource("aboutlibraries.json") {
                            it.bufferedReader().readText()
                        }, Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}

@Composable
fun StoryList(
    stories: List<Story>,
    selectedStory: Story?,
    storySelected: (story: Story) -> Unit
) {
    LazyColumn {
        items(stories) { story ->
            StoryView(story, selectedStory, storySelected)
        }
    }
}

@Composable
fun StoryView(
    story: Story,
    selectedStory: Story?,
    storySelected: (story: Story) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = {
            storySelected(story)
        }).padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                story.name,
                style = if (story.name == selectedStory?.name) MaterialTheme.typography.h6 else MaterialTheme.typography.body1
            )
            Text(text = story.fullSlug, style = MaterialTheme.typography.caption)
        }
    }
}

@Composable
fun StoryDetailsView(story: Story) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.padding(16.dp).fillMaxSize().verticalScroll(scrollState)
    ) {
        Text(story.name, style = MaterialTheme.typography.h2)
        Divider(modifier = Modifier.padding(12.dp))
        StoryContent(story)
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun StoryContent(story: Story) {
    story.content?.let {
        val elements = it["body"] as JsonArray
        elements.jsonArray.forEach { element ->
            val elem = element.jsonObject
            when (elem.componentType) {
                "ArticleText" -> ArticleMarkdownBlock(elem)
                "ArticleQuote" -> ArticleQuoteBlock(elem)
                "ArticleCode" -> ArticleCodeBlock(elem)
                "ArticleImage" -> ArticleImageBlock(elem)
            }
        }
    }
}


val JsonObject?.componentType: String?
    get() {
        this ?: return null
        return this["component"]?.jsonPrimitive?.content
    }

val Story.storyType: String?
    get() = content?.getValue("type")?.jsonPrimitive?.content
