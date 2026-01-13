package com.example.chatter.Presentation.Components.ChatComponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chatter.DomainLayer.model.Message
import com.example.chatter.R
import com.example.chatter.ui.theme.DarkGray
import com.example.chatter.ui.theme.Purple
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.heightIn
import androidx.compose.ui.draw.clip


@Composable
fun ChatBubble(
    message: Message,
) {
    val isMyMessage = message.senderId == Firebase.auth.currentUser?.uid

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        horizontalArrangement = if (isMyMessage) Arrangement.End else Arrangement.Start
    ) {

        Box(
            modifier = Modifier
                .padding(12.dp)
                .widthIn(max = 280.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically){
                if(!isMyMessage){
                Image(painter = painterResource(R.drawable.man),
                    null,
                    modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                }
                if (!message.imageUrl.isNullOrEmpty()) {

                    AsyncImage(
                        model = message.imageUrl,
                        contentDescription = "chat image",
                        modifier = Modifier
                            .size(220.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                } else {

                    Text(
                        text = message.message.trim(),
                        color = Color.White,
                        modifier = Modifier
                            .background(
                                color = if (isMyMessage) Purple else DarkGray,
                                shape = RoundedCornerShape(
                                    topStart = 16.dp,
                                    topEnd = 16.dp,
                                    bottomStart = if (isMyMessage) 16.dp else 0.dp,
                                    bottomEnd = if (isMyMessage) 0.dp else 16.dp
                                )
                            )
                            .padding(16.dp)
                    )
                }


            }
        }
    }
}

