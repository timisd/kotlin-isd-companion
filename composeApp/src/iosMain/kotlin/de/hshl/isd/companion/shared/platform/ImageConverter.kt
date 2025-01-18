package de.hshl.isd.companion.shared.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image

@Composable
actual fun getImageFromBytes(bytes: ByteArray?): ImageBitmap? {
    return if (bytes != null) {
        Bitmap.makeFromImage(Image.makeFromEncoded(bytes)).asComposeImageBitmap()
    } else {
        null
    }
}


