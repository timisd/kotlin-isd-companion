package de.hshl.isd.companion.core.textinteraction

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun openEmail(email: String, context: Any) {
    val url = NSURL.URLWithString("mailto:$email") ?: return
    UIApplication.sharedApplication.openURL(url, emptyMap<Any?, Any>(), null)
}

actual fun openPhone(phoneNumber: String, context: Any) {
    val url = NSURL.URLWithString("tel:$phoneNumber") ?: return
    UIApplication.sharedApplication.openURL(url, emptyMap<Any?, Any>(), null)
}
