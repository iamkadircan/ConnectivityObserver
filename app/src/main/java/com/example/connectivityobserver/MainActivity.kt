package com.example.connectivityobserver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.connectivityobserver.ui.theme.ConnectivityObserverTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConnectivityObserverTheme {

                val viewModel: ConnectivityViewModel = hiltViewModel()
                val isOnline by viewModel.isOnline.collectAsState()
                var showBottomBar by remember { mutableStateOf(false) }
                var bgColor by remember { mutableStateOf(Color.Red) }

                LaunchedEffect(isOnline) {
                    if (isOnline) {
                        showBottomBar = true
                        bgColor = Color.Green
                        delay(2000)
                        showBottomBar = false
                    } else {

                        showBottomBar = true
                        bgColor  = Color.Red
                    }
                }



                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            val message = if (isOnline) "Connected" else "No Connection"

                            Text(
                                text = message,
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth()
                                    .background(bgColor)
                                , textAlign = TextAlign.Center,

                            )
                        }

                    }


                ) { innerPadding ->

                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            modifier = Modifier.size(60.dp),
                            painter = painterResource(R.drawable.ic_network),
                            tint = bgColor,
                            contentDescription = ""
                        )

                    }

                }
            }
        }
    }
}
