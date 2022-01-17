package com.mikepenz.storyblok.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.mikepenz.markdown.Code
import com.mikepenz.markdown.Markdown
import com.mikepenz.markdown.MarkdownDefaults
import com.mikepenz.storyblok.util.TAG_URL
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Composable
internal fun ArticleMarkdownBlock(elem: JsonObject) {
    elem["markdown"]?.jsonPrimitive?.content?.let { content ->
        Markdown(content)
    }
}

@Composable
internal fun ArticleCodeBlock(elem: JsonObject) {
    elem["code"]?.jsonPrimitive?.content?.let { code ->
        Code(code, colors = MarkdownDefaults.markdownColors())
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
internal fun ArticleImageBlock(elem: JsonObject) {
    Spacer(Modifier.padding(4.dp))
    Image(
        painter = rememberImagePainter(
            elem["image"]?.jsonObject?.get("filename")?.jsonPrimitive?.content ?: "",
            builder = { size(OriginalSize) }
        ),
        contentDescription = "Image",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.padding(4.dp))
}

@Composable
internal fun ArticleQuoteBlock(elem: JsonObject) {
    val quote = elem["quote"]?.jsonPrimitive?.content
    val source = elem["linkname"]?.jsonPrimitive?.content?.takeIf { s -> s.isNotEmpty() }
    val link = elem["linkurl"]?.jsonPrimitive?.content

    // UriHandler parse and opens URI inside AnnotatedString Item in Browse
    val uriHandler = LocalUriHandler.current
    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }

    val color = MaterialTheme.colors.onBackground
    Box(modifier = Modifier
        .padding(top = 16.dp, bottom = 16.dp)
        .drawBehind {
            drawLine(
                color = color,
                strokeWidth = 4f,
                start = Offset(8.dp.value, 0f),
                end = Offset(8.dp.value, size.height)
            )
        }
        .padding(start = 12.dp)) {
        val text = buildAnnotatedString {
            pushStyle(MaterialTheme.typography.body1.toSpanStyle().plus(SpanStyle(fontStyle = FontStyle.Italic)))
            append(quote ?: "")
            pop()
            source?.let {
                link?.let { pushStringAnnotation(TAG_URL, link) }
                pushStyle(ParagraphStyle(textAlign = TextAlign.End))
                pushStyle(MaterialTheme.typography.body2.toSpanStyle().plus(SpanStyle(textDecoration = TextDecoration.Underline, fontWeight = FontWeight.Bold)))
                append(source)
                pop()
            }
        }
        Text(text, Modifier.pointerInput(Unit) {
            detectTapGestures { pos ->
                layoutResult.value?.let { layoutResult ->
                    val position = layoutResult.getOffsetForPosition(pos)
                    text.getStringAnnotations(position, position)
                        .firstOrNull { a -> a.tag == TAG_URL }
                        ?.let { a -> uriHandler.openUri(a.item) }
                }
            }
        }, onTextLayout = { layoutResult.value = it })
    }
}