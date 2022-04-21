package com.iliagoncharuk.simpleweatherapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.iliagoncharuk.composebasics.ui.theme.ComposeBasicsTheme

@Composable
fun StartButtonView(onContinueClicked: () -> Unit){
    ComposeBasicsTheme() {
        Surface() {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick =onContinueClicked) {
                    Text(text = "Check weather")
                }
            }


        }
    }
}
