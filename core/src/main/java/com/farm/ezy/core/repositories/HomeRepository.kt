package com.farm.ezy.core.repositories

import com.farm.ezy.core.models.items.ItemGet
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


class HomeRepository @Inject constructor(
    private val db: FirebaseFirestore
) {

    fun getAll(type: String): Flow<DataState<List<ItemGet>>> = channelFlow {
        try {
            val d = db.collection(type).orderBy("id", Query.Direction.ASCENDING)
            d.addSnapshotListener { value, error ->
                launch(Dispatchers.Main) {
                    if (error != null) {
                        send(DataState.Error(error))
                    } else {
                        if (value!!.isEmpty) {
                            send(DataState.Error(NoItemFoundException("No Data Found")))
                        } else {
                            val item = value.toObjects(ItemGet::class.java)
                            send(DataState.Success(item))
                        }
                    }
                }
            }
            awaitClose()
        } catch (e: java.lang.Exception) {
            send(DataState.Error(e))
        }
    }

    fun getInsecticides(): Flow<DataState<List<ItemGet>>> = channelFlow {
        try {
            val d = db.collection("INSECTICIDES").orderBy("id", Query.Direction.ASCENDING)
            d.addSnapshotListener { value, error ->
                launch(Dispatchers.Main) {
                    if (error != null) {
                        send(DataState.Error(error))
                    } else {
                        if (value!!.isEmpty) {
                            send(DataState.Error(NoItemFoundException("No Data Found")))
                        } else {
                            val item = value.toObjects(ItemGet::class.java)
                            send(DataState.Success(item))
                        }
                    }
                }
            }
            awaitClose()
        } catch (e: java.lang.Exception) {
            send(DataState.Error(e))
        }
    }

    fun getHerbicides(): Flow<DataState<List<ItemGet>>> = channelFlow {
        try {
            val d = db.collection("HERBICIDES").orderBy("id", Query.Direction.ASCENDING)
            d.addSnapshotListener { value, error ->
                launch(Dispatchers.Main) {
                    if (error != null) {
                        send(DataState.Error(error))
                    } else {
                        if (value!!.isEmpty) {
                            send(DataState.Error(NoItemFoundException("No Data Found")))
                        } else {
                            val item = value.toObjects(ItemGet::class.java)
                            send(DataState.Success(item))
                        }
                    }
                }
            }
            awaitClose()
        } catch (e: java.lang.Exception) {
            send(DataState.Error(e))
        }
    }

    fun getGrowthPromoter(): Flow<DataState<List<ItemGet>>> = channelFlow {
        try {
            val d = db.collection("GROWTH PROMOTERS").orderBy("id", Query.Direction.ASCENDING)
            d.addSnapshotListener { value, error ->
                launch(Dispatchers.Main) {
                    if (error != null) {
                        send(DataState.Error(error))
                    } else {
                        if (value!!.isEmpty) {
                            send(DataState.Error(NoItemFoundException("No Data Found")))
                        } else {
                            val item = value.toObjects(ItemGet::class.java)
                            send(DataState.Success(item))
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