package io.github.deep_liking

import android.net.Uri

actual object KDeepLinking {
    private var callback: ((UrlDetails) -> Unit)? = null
    private var lastDeepLink: UrlDetails? = null

    fun notifyUrl(uri: Uri?) {
        if (uri != null) {
            val parsedUrl = parseUri(uri)
            lastDeepLink = parsedUrl
            if (parsedUrl != null)
                callback?.invoke(parsedUrl)
        }
    }

    actual fun setListener(callback: (UrlDetails) -> Unit) {
        this.callback = callback
        lastDeepLink?.let { callback(it) }
        lastDeepLink = null

    }


    private fun parseUri(uri: Uri): UrlDetails? {
        return try {
            UrlDetails(
                scheme = uri.scheme,
                host = uri.host,
                port = uri.port.takeIf { it != -1 },
                path = uri.path,
                queryParams = uri.queryParameterNames.associateWith {
                    uri.getQueryParameter(it) ?: ""
                },
                fragment = uri.fragment
            )
        } catch (e: Exception) {
            // Log the error or handle it appropriately
            null
        }
    }

}
