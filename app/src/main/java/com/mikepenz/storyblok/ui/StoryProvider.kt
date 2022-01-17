package com.mikepenz.storyblok.ui

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import com.mikepenz.storyblok.sdk.model.Story

class StoryProvider : CollectionPreviewParameterProvider<Story>(
    listOf(
        Story(1, "uuid1", "Test", "slug", "slug", createdAt = "2021-10-18T12:38:03.257Z"),
        Story(2, "uuid2", "Test 2", "slug2", "slug2", createdAt = "2021-10-19T12:38:03.257Z")
    )
)