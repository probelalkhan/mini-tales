package dev.belalkhan.minitales.writestory

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.belalkhan.minitales.theme.components.AppBar
import dev.belalkhan.minitales.theme.utils.toast

@Composable
internal fun WriteStoryScreen(viewModel: WriteStoryViewModel, navController: NavController) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var editorWebView by remember { mutableStateOf<WebView?>(null) }

    if (uiState.value.draftSaved) {
        context.toast(R.string.draft_saved_successfully) { viewModel.resetErrorSuccess() }
    }
    if (uiState.value.error != null) {
        context.toast(uiState.value.error!!) { viewModel.resetErrorSuccess() }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(
                title = stringResource(id = R.string.mini_tales),
                navIcon = Icons.Filled.ArrowBack,
                onNav = { navController.navigateUp() },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editorWebView?.evaluateJavascript(
                        "(function(){return window.document.getElementsByClassName('note-editable')[0].innerHTML})();",
                    ) { body ->
                        viewModel.onEvent(WriteStoryUiEvent.SaveDraft(uiState.value.title, body))
                    }
                },
            ) {
                Icon(Icons.Filled.Done, "Save")
            }
        },
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp),
        ) {
            val (title, editor) = createRefs()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(title) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    },
            ) {
                Text(
                    modifier = Modifier.align(CenterHorizontally),
                    text = stringResource(id = R.string.title),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.value.title,
                    onValueChange = { viewModel.onEvent(WriteStoryUiEvent.TitleChanged(it)) },
                )
            }

            Editor(
                modifier = Modifier.constrainAs(editor) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(title.bottom, 8.dp)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
                onWebView = { editorWebView = it },
            )
        }
    }
}

@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
@Composable
private fun Editor(modifier: Modifier = Modifier, onWebView: (WebView) -> Unit) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val editor = View.inflate(context, R.layout.editor, null)
            val webView = editor.findViewById<WebView>(R.id.web_view)
            webView.settings.javaScriptEnabled = true
            webView.loadUrl("file:///android_asset/editor.html")
            onWebView(webView)
            editor
        },
    )
}
