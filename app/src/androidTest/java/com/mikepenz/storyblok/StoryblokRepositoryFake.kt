package com.mikepenz.storyblok

import com.mikepenz.common.repository.StoryblokRepositoryInterface
import com.mikepenz.storyblok.sdk.model.Story

class StoryblokRepositoryFake : StoryblokRepositoryInterface {
    val storyList = listOf(Story(1, "uuid", "test", "slug", "full_slug", createdAt = "2018-04-24T11:57:29.302Z"))

    override suspend fun fetchStories(): List<Story> {
        return storyList
    }
}
