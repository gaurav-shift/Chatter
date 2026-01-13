package com.example.chatter.Presentation.Components.ChatComponent

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentSelectionDialog(
    onCameraSelected:()-> Unit,
    onGallerySelected:()-> Unit
){
    AlertDialog(onDismissRequest = {},
        confirmButton = { TextButton(onClick = onCameraSelected) {Text(text = "Camera", color = Color.Black) } },
        dismissButton = {TextButton(onClick = onGallerySelected) {Text(text = "Gallery", color = Color.Black) }},
        title = {Text(text = "Select Your Source?")},
        text = {Text(text = "Would you like to pick an image from the gallery or use the camera")})
}