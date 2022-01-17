package com.mikepenz.storyblok.util

import com.mikepenz.storyblok.sdk.model.Story
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

internal const val TAG_URL = "url"
internal const val TAG_IMAGE_URL = "imageUrl"

val JsonObject?.componentType: String?
    get() {
        this ?: return null
        return this["component"]?.jsonPrimitive?.content
    }

val Story.storyType: String?
    get() = content?.getValue("type")?.jsonPrimitive?.content
