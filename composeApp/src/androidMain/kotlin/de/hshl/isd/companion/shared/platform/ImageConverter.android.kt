package de.hshl.isd.companion.shared.platform

import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

@Composable
actual fun getImageFromBytes(bytes: ByteArray?): ImageBitmap? {
    return if (bytes != null) {
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)?.asImageBitmap()
    } else {
        null
    }
}
