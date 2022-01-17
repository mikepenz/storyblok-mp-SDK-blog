package com.mikepenz.common.utils

import com.mikepenz.storyblok.sdk.model.Story
import kotlinx.datetime.Instant

/**
 * Creation date as instant
 */
val Story.createdAtInstant: Instant
    get() = Instant.parse(createdAt)
