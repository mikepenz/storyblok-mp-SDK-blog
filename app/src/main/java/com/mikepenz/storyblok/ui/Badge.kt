package com.mikepenz.storyblok.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Badge
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.*

/**
 * Defines a custom badge style to highlight a stories type
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun StoryBadge(type: String) {
    Badge(
        backgroundColor = MaterialTheme.colors.primaryVariant
    ) {
        Text(
            type.uppercase(Locale.ENGLISH),
            modifier = Modifier.padding(1.dp),
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.subtitle1
        )
    }
}