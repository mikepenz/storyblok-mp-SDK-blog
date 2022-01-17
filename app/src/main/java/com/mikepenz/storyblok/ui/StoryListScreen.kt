package com.mikepenz.storyblok.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.TopAppBar
import com.mikepenz.common.utils.readableCreatedAt
import com.mikepenz.storyblok.sdk.model.Story
import com.mikepenz.storyblok.util.storyType
import org.koin.androidx.compose.getViewModel
import java.text.DateFormat.MEDIUM

const val StoryListTag = "StoryList"

@Composable
fun StoryListScreen(
    paddingValues: PaddingValues = PaddingValues(),
    storySelected: (story: Story) -> Unit,
    openSourceClick: () -> Unit,
    storyblokViewModel: StoryblokViewModel = getViewModel()
) {
    val storyState = storyblokViewModel.stories

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mike Penz Blog | Storyblok mp SDK") },
                contentPadding = rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.statusBars,
                    applyStart = true,
                    applyTop = true,
                    applyEnd = true,
                ),
                actions = {
                    IconButton(onClick = openSourceClick) { Icon(Icons.Filled.Info, "Licenses") }
                }
            )
        }
    ) {
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .testTag(StoryListTag),
        ) {
            items(storyState.value) { story ->
                StoryView(story, storySelected)
            }
        }
    }
}

@Composable
fun StoryView(story: Story, storySelected: (story: Story) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { storySelected(story) })
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            Modifier.weight(1f)
        ) {
            Text(text = story.name, maxLines = 2, style = MaterialTheme.typography.h6)
            Text(text = story.readableCreatedAt(MEDIUM, MEDIUM), style = MaterialTheme.typography.caption)
        }
        Spacer(modifier = Modifier.size(4.dp))
        story.storyType?.let { StoryBadge(it) }
    }
}