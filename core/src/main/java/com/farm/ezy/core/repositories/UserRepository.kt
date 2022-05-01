package com.farm.ezy.core.repositories

import com.farm.ezy.core.models.order.Orders
import com.farm.ezy.core.models.user.UserGet
import com.farm.ezy.core.utils.DataState
import com.farm.ezy.core.utils.NoItemFoundException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val db: FirebaseFirestore
) {

    fun getLoginUser(uid: String): Flow<DataState<UserGet>> = channelFlow {
        try {
            db.collection("Users").document(uid)
                .addSnapshotListener { documentSnapShot, error ->
                    launch(Dispatchers.Main) {
                        send(DataState.Loading)
                        val user = documentSnapShot?.toObject(UserGet::class.java)
                        send(DataState.Success(user!!))
                    }
                }
            awaitClose()
        } catch (e: Exception) {
            send(DataState.Error(e))
            awaitClose()
        }
    }

    fun getOrder(uid: String): Flow<DataState<List<Orders>>> = channelFlow {
        try {
            val d = db.collection("Users").document(uid)
                .collection("orders").orderBy("buyOn", Query.Direction.ASCENDING)
            d.addSnapshotListener { value, error ->
                launch(Dispatchers.Main) {
                    if (error != null) {
                        send(DataState.Error(error))
                    } else {
                        if (value!!.isEmpty) {
                            send(DataState.Error(NoItemFoundException("No Data Found")))
                        } else {
                            val orders = value.toObjects(Orders::class.java)
                            send(DataState.Success(orders))
                        }
                    }
                }
            }
            awaitClose()
        } catch (e: java.lang.Exception) {
            send(DataState.Error(e))

        }
    }
}