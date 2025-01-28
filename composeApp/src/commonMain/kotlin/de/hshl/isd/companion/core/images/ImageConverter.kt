package de.hshl.isd.companion.core.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

@Composable
expect fun getImageFromBytes(bytes: ByteArray?): ImageBitmap?
