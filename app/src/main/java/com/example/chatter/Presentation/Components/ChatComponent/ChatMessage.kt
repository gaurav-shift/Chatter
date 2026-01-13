package com.example.chatter.Presentation.Components.ChatComponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.example.chatter.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chatter.DomainLayer.model.Message
import com.example.chatter.ui.theme.DarkGray

@Composable
fun ChatMessage(
    messages: List<Message>,
    onSendMessage: (String) -> Unit,
    onPickImage:() -> Unit,
    onLongPressMessage:(String)-> Unit
) {
    var msg by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true   // newest at bottom
        ) {
            items(messages.reversed()) { message ->
                ChatBubble(message = message, onLongPress = {
                    onLongPressMessage(message.id)
                })
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkGray)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {onPickImage()
                msg = ""}
            ) {
                Image(painter = painterResource(R.drawable.baseline_attach_file_24), contentDescription = "Attach")
            }

            TextField(
                value = msg,
                onValueChange = { msg = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message") },
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = DarkGray,
                    unfocusedContainerColor = DarkGray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedPlaceholderColor = Color.White,
                    unfocusedPlaceholderColor = Color.White
                )
            )

            IconButton(
                onClick = {
                    if (msg.isNotBlank()) {
                        onSendMessage(msg)
                        msg = ""
                    }
                }
            ) {
                Image(painter = painterResource(R.drawable.baseline_send_24), contentDescription = "Send")
            }
        }
    }
}
