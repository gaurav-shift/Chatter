package com.example.chatter.Presentation.ChatUi

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import java.io.File
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatter.DomainLayer.model.Message
import com.example.chatter.DomainLayer.util.Results
import com.example.chatter.Presentation.Components.ChatComponent.ChatMessage
import com.example.chatter.Presentation.Components.ChatComponent.ContentSelectionDialog
import com.example.chatter.Presentation.Components.ChatComponent.DeleteMessageDialog
import com.google.firebase.auth.auth
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ChatScreen(navController: NavController , channelId: String){
    Scaffold(
        containerColor = Color.Black,
        modifier = Modifier.fillMaxSize().imePadding(),


    ) {
        val viewModel: ChatViewModel = hiltViewModel()
        val messageState by viewModel.messageState.collectAsState()

        val showDeleteDialog = remember { mutableStateOf(false) }
        val selectedMessageId = remember { mutableStateOf<String?>(null) }


        val chooserDialog = remember { mutableStateOf(false) }
        val cameraImageUri = remember { mutableStateOf<Uri?>(null) }
        val cameraImageLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { success->
            if(success){
                cameraImageUri.value?.let {uri->
                    viewModel.sendImage(channelId, uri)
                }
            }

        }
        val galleryLaucher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri->
            if(uri != null){
               viewModel.sendImage(channelId,uri)
            }

        }
        fun createImageUri(): Uri{
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", java.util.Locale.getDefault()).format(Date())
            val storageDir =
                ContextCompat.getExternalFilesDirs(
                    navController.context, Environment.DIRECTORY_PICTURES
                ).first()
            return FileProvider.getUriForFile(
                navController.context,
                "${navController.context.packageName}.provider",
                File.createTempFile("JPEG_${timeStamp}_",".jpg",storageDir).apply {
                    cameraImageUri.value = Uri.fromFile(this)
                }
            )
        }

        val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()){isGranted->
            if(isGranted) cameraImageLauncher.launch(createImageUri())
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {

            LaunchedEffect(channelId) {
                viewModel.getMessages(channelId)
            }
            when(messageState){
                is Results.Failure ->{}
                Results.Idle -> {}
                Results.Loading -> {}
                is Results.Success<*> ->{
                    val msgs = (messageState as Results.Success<List<Message>>).data
                    ChatMessage(messages = msgs,
                        onSendMessage = {text->
                            val msgg = Message(
                                message=text,
                                senderId = com.google.firebase.Firebase.auth.currentUser?.uid ?: ""
                                )
                            viewModel.sendMessage(channelId, msgg)
                        }, onPickImage = {
                            chooserDialog.value = true
                        },
                        onLongPressMessage = { messageId ->
                            selectedMessageId.value = messageId
                            showDeleteDialog.value = true
                        })
                }
            }
        }
        if(chooserDialog.value){
            ContentSelectionDialog(
                onCameraSelected = {
//                    val  uri = createImageUri(navController.context){
//                        cameraImageUri.value = it
//                    }
//                    cameraImageLauncher.launch(uri)
                    chooserDialog.value = false
                    if(navController.context.checkSelfPermission(Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED){
                        cameraImageLauncher.launch(createImageUri())
                    }else{
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                onGallerySelected = {
                    galleryLaucher.launch("image/*")
                    chooserDialog.value = false
                })
        }
        if (showDeleteDialog.value && selectedMessageId.value != null) {
            DeleteMessageDialog(
                onConfirm = {
                    viewModel.deleteMessage(channelId, selectedMessageId.value!!)
                    showDeleteDialog.value = false
                },
                onDismiss = {
                    showDeleteDialog.value = false
                }
            )
        }




    }

}


