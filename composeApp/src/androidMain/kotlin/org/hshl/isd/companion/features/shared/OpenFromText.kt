package org.hshl.isd.companion.features.shared

import android.content.Context
import android.content.Intent
import android.net.Uri

actual fun openEmail(email: String, context: Any) {
    val ctx = context as Context
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$email")
    }
    ctx.startActivity(intent)
}

actual fun openPhone(phoneNumber: String, context: Any) {
    val ctx = context as Context
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    ctx.startActivity(intent)
}