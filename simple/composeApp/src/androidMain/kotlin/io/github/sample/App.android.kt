package io.github.sample

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.deep_liking.KDeepLinking

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { App() }

        val deepLinkUri: Uri? = intent?.data
        KDeepLinking.notifyUrl(deepLinkUri)

    }
}

@Preview
@Composable
fun AppPreview() {
    App()
}
