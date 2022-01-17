package com.mikepenz.storyblok.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.TopAppBar
import com.mikepenz.common.utils.readableCreatedAt
import com.mikepenz.storyblok.sdk.model.Story
import com.mikepenz.storyblok.util.componentType
import com.mikepenz.storyblok.util.storyType
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import org.koin.androidx.compose.getViewModel
import java.text.DateFormat

@OptIn(ExperimentalCoilApi::class)
@Composable
fun StoryDetailsScreen(
    storyUuid: String,
    open: (Story) -> Unit,
    popBack: () -> Unit,
    storyblokViewModel: StoryblokViewModel = getViewModel()
) {
    val story = storyblokViewModel.getStory(storyUuid)

    if (story == null) {
        popBack()
        return
    }

    val context = LocalContext.current
    val storyAwareUriHandler = object : UriHandler {
        override fun openUri(uri: String) {
            if (uri.startsWith("http")) {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
            } else {
                storyblokViewModel.findStory(uri)?.let { open(it) }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(story.name, modifier = Modifier.padding(end = 8.dp), maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = {
                    IconButton(onClick = { popBack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                contentPadding = rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.statusBars,
                    applyStart = true,
                    applyTop = true,
                    applyEnd = true
                )
            )
        },
    ) {
        val scrollState = rememberScrollState()

        SelectionContainer {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    story.storyType?.let { StoryBadge(it) }
                    Text(story.name, style = MaterialTheme.typography.h3, textAlign = TextAlign.Center)
                    Divider(Modifier.padding(12.dp))
                    Text(story.readableCreatedAt(DateFormat.LONG, DateFormat.MEDIUM), style = MaterialTheme.typography.subtitle1)
                    Spacer(Modifier.padding(20.dp))
                }
                CompositionLocalProvider(LocalUriHandler provides storyAwareUriHandler) {
                    StoryContent(story = story)
                }
                Spacer(
                    Modifier
                        .navigationBarsHeight()
                        .fillMaxWidth()
                )
            }
        }
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