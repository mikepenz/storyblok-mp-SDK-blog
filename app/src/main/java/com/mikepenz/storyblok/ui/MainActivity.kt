package com.mikepenz.storyblok.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.mikepenz.storyblok.sdk.model.Story
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainLayout()
        }
    }
}

sealed class Screen(val title: String) {
    object StoryList : Screen("StoryList")
    object StoryDetails : Screen("StoryDetails")
    object OpenSource : Screen("OpenSource")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainLayout() {
    val navController = rememberAnimatedNavController()
    val viewModel: StoryblokViewModel = getViewModel()

    StoryblokTheme {
        ProvideWindowInsets {
            Scaffold(
                modifier = Modifier.background(MaterialTheme.colors.primarySurface),
                contentPadding = rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.systemBars,
                    applyTop = false,
                    applyBottom = true,
                )
            ) { paddingValues ->
                AnimatedNavHost(navController, startDestination = Screen.StoryList.title) {
                    composable(
                        route = Screen.StoryList.title
                    ) {
                        StoryListScreen(
                            paddingValues = paddingValues,
                            storySelected = {
                                navController.navigate(Screen.StoryDetails.title + "/${it.uuid}")
                            },
                            openSourceClick = {
                                navController.navigate(Screen.OpenSource.title)
                            },
                            viewModel
                        )
                    }
                    composable(
                        route = Screen.StoryDetails.title + "/{story}"
                    ) { backStackEntry ->
                        StoryDetailsScreen(
                            backStackEntry.arguments?.get("story") as String,
                            open = { story -> navController.navigate(Screen.StoryDetails.title + "/${story.uuid}") },
                            popBack = { navController.popBackStack() },
                            viewModel
                        )
                    }
                    composable(
                        route = Screen.OpenSource.title
                    ) {
                        OpenSourceScreen(popBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview(@PreviewParameter(StoryProvider::class) story: Story) {
    MaterialTheme {
        StoryView(story, storySelected = {})
    }
}
