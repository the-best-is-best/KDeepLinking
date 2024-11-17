package io.github.deep_liking

import platform.Foundation.NSURL

actual object KDeepLinking {
    private var callback: ((UrlDetails) -> Unit)? = null
    private var lastDeepLink: UrlDetails? = null

    // This method will be called from the iOS UIApplicationDelegate when the app is opened by a deep link
    fun notifyUrl(url: NSURL?) {
        url?.let {
            val parsedUrl = parseUrl(it)
            lastDeepLink = parsedUrl
            if (parsedUrl != null) {
                callback?.invoke(parsedUrl)
            }
        }
    }

    // Set the listener that will be triggered when a deep link is received
    actual fun setListener(callback: (UrlDetails) -> Unit) {
        this.callback = callback
        lastDeepLink?.let { callback(it) }
        lastDeepLink = null
    }

    // Helper method to parse NSURL into UrlDetails
    private fun parseUrl(url: NSURL): UrlDetails? {
        return try {
            UrlDetails(
                scheme = url.scheme,
                host = url.host,
                port = url.port?.takeIf { it.intValue != -1 }?.intValue(),
                path = url.path,
                queryParams = url.query?.let {
                    url.query?.split("&")
                        ?.associate {
                            val (key, value) = it.split("=")
                            key to value
                        } ?: emptyMap()
                } ?: emptyMap(),
                fragment = url.fragment
            )
        } catch (e: Exception) {
            // Handle or log the error appropriately
            null
        }
    }
}

