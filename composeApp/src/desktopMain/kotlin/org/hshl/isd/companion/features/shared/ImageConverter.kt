package org.hshl.isd.companion.features.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.ImageInfo
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

@Composable
actual fun getImageFromBytes(bytes: ByteArray?): ImageBitmap? {
    return if (bytes != null) {
        val inputStream = ByteArrayInputStream(bytes)
        val bufferedImage: BufferedImage? = ImageIO.read(inputStream)
        bufferedImage?.let {
            val width = it.width
            val height = it.height
            val pixels = IntArray(width * height)
            it.getRGB(0, 0, width, height, pixels, 0, width)
            val bitmap = Bitmap().apply {
                allocPixels(ImageInfo.makeS32(width, height, ColorAlphaType.PREMUL))
                installPixels(bytes)
            }
            bitmap.asComposeImageBitmap()
        }
    } else {
        null
    }
}
