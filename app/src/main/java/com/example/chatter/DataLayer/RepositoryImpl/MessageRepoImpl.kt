package com.example.chatter.DataLayer.RepositoryImpl

import android.net.Uri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.chatter.DomainLayer.model.Message
import com.example.chatter.DomainLayer.repository.MessageRepository
import com.example.chatter.DomainLayer.util.Results
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resumeWithException
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MessageRepoImpl @Inject constructor(
    private val database: FirebaseDatabase
): MessageRepository {
    override fun getMessages(channelId: String): Flow<Results<List<Message>>> = callbackFlow{
        trySend(Results.Loading)
        val ref = database
            .getReference("message")
            .child(channelId)
        val listener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               val message = snapshot.children.mapNotNull { data->
                   data.getValue(Message::class.java)
               }
                trySend(Results.Success(message))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Results.Failure(error.message))
            }

        }
        ref.addValueEventListener(listener)

        awaitClose {
            ref.removeEventListener(listener)
        }
    }

    override suspend fun sendMessage(
        channelId: String,
        message: Message
    ): Results<Unit> {
        return try {
            val ref = database
                .getReference("message")
                .child(channelId)
            val messageId = ref.push().key
                ?: return Results.Failure("Invalid message id")
            ref.child(messageId).setValue(
                message.copy(messageId)
            )
            Results.Success(Unit)
        } catch (e: Exception){
            Results.Failure(e.message?:"Failed to send Message")
        }
    }

    override suspend fun sendImageMessage(
        channelId: String,
        imageUri: Uri
    ): Results<Unit> {
        return try {
            val userId = Firebase.auth.currentUser?.uid
                ?: return Results.Failure("User not logged in")

            // 1️⃣ Upload to Cloudinary
            val result = suspendCancellableCoroutine<Map<*, *>> { cont ->
                MediaManager.get().upload(imageUri)
                    .unsigned("Chatter_app")
                    .callback(object : UploadCallback {
                        override fun onSuccess(requestId: String?, resultData: Map<*, *>) {
                            cont.resume(resultData, null)
                        }

                        override fun onError(requestId: String?, error: ErrorInfo?) {
                            cont.resumeWithException(Exception(error?.description))
                        }

                        override fun onStart(requestId: String?) {}
                        override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {}
                        override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
                    })
                    .dispatch()
            }

            val imageUrl = result["secure_url"] as String

            // 2️⃣ Create message object
            val messageId = database.getReference("message")
                .child(channelId)
                .push()
                .key ?: return Results.Failure("Failed to create message id")

            val message = Message(
                id = messageId,
                senderId = userId,
                message = "",
                imageUrl = imageUrl,
                createdAt = System.currentTimeMillis()
            )

            // 3️⃣ Save to Firebase DB
            database.getReference("message")
                .child(channelId)
                .child(messageId)
                .setValue(message)
                .await()

            Results.Success(Unit)

        } catch (e: Exception) {
            Results.Failure(e.message ?: "Image upload failed")
        }
    }



}