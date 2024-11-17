package io.github.deep_liking


data class UrlDetails(
    val scheme: String?,
    val host: String?,
    val port: Int?,
    val path: String?,
    val queryParams: Map<String, String>,
    val fragment: String?
)

expect object KDeepLinking {
    fun setListener(callback: (UrlDetails) -> Unit)
}