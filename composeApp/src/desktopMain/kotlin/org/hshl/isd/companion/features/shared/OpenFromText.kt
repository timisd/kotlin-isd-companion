package org.hshl.isd.companion.features.shared

import java.awt.Desktop
import java.net.URI

actual fun openEmail(email: String, context: Any) {
    val uri = URI("mailto:$email")
    if (Desktop.isDesktopSupported()) {
        Desktop.getDesktop().mail(uri)
    }
}

actual fun openPhone(phoneNumber: String, context: Any) {
    val uri = URI("tel:$phoneNumber")
    if (Desktop.isDesktopSupported()) {
        Desktop.getDesktop().browse(uri)
    }
}