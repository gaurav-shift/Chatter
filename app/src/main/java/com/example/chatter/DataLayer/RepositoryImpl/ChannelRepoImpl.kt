package com.example.chatter.DataLayer.RepositoryImpl

import android.R.attr.data
import com.example.chatter.DomainLayer.model.Channel
import com.example.chatter.DomainLayer.repository.ChannelRepository
import com.example.chatter.DomainLayer.util.Results
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ChannelRepoImpl @Inject constructor(
    private val database: FirebaseDatabase
) : ChannelRepository{
    override fun getChannels(): Flow<Results<List<Channel>>> = callbackFlow {

        trySend(Results.Loading)

        val ref = database.getReference("channel")

        val listener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val channel = snapshot.children.mapNotNull { data->
                    val id = data.key ?: return@mapNotNull null
                    val name = data.child("name").getValue(String::class.java)
                        ?: return@mapNotNull null
                    val createdAt =
                        data.child("createdAt").getValue(Long::class.java) ?: 0L

                    Channel(
                        id = id,
                        name = name,
                        createdAt = createdAt
                    )

                }
                trySend(Results.Success(channel))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Results.Failure(error.message))
            }

        }


        ref.addValueEventListener(listener)
        awaitClose{
            ref.removeEventListener(listener)
        }

    }

    override suspend fun addChannel(name: String): Results<Unit> {
        return try {
            val ref = database.getReference("channel")
            val id = ref.push().key ?: return Results.Failure("Invalid ID")

            val channel = Channel(
                id = id,
                name = name,
                createdAt = System.currentTimeMillis()
            )

            ref.child(id).setValue(channel)
            Results.Success(Unit)

        } catch (e: Exception) {
            Results.Failure(e.message ?: "Failed to add channel")
        }
    }


}