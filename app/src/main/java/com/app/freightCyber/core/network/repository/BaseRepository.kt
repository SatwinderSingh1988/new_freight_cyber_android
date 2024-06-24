package com.app.freightCyber.core.network.repository

import com.app.freightCyber.domain.model.FreightBackandError
import com.app.freightCyber.utils.NetworkResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

abstract class BaseRepository {

    protected suspend  fun <T, R> makeNetworkRequest(
        request: T,
        apiCall: suspend (T) -> Response<R>
    ): Flow<NetworkResult<R>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = apiCall(request)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!))
            } else {
                emit(NetworkResult.Error(extractError(response)))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    private fun <R> extractError(response: Response<R>): Exception {
        val errorBody = response.errorBody()?.string()
        return try {
            val errorResponse = gson.fromJson(errorBody, FreightBackandError::class.java)
            Exception(errorResponse.error)
        } catch (e: Exception) {
            Exception("Error: ${response.code()} ${response.message()}")
        }
    }

    companion object {
        private val gson: Gson by lazy {
            Gson()
        }
    }
}