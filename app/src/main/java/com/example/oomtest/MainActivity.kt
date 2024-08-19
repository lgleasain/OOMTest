package com.example.oomtest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oomtest.ui.theme.OOMTestTheme
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OOMTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting(viewModel: TestViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val animate by viewModel.uiState.map(coroutineScope) { it.animate }.collectAsStateWithLifecycle()
    val number by viewModel.uiState.map(coroutineScope) { it.number }.collectAsStateWithLifecycle()

    var scale by remember {
        mutableFloatStateOf(1f)
    }

    LaunchedEffect(key1 = animate) {
        if (animate) {
            launch {
                animate(
                    initialValue = 1f,
                    targetValue = 2f,
                    animationSpec = tween(500)
                ) { value, _ ->
                    scale = value
                }
                viewModel.resetAnimation()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Hello, $number!", fontSize = 30.sp, modifier = Modifier.align(Alignment.Center).scale(scale))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OOMTestTheme {
        Greeting()
    }
}