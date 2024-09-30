package org.hshl.isd.companion.features.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

@Composable
expect fun getImageFromBytes(bytes: ByteArray?): ImageBitmap?
