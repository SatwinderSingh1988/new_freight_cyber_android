package com.app.freightCyber.data.repository


import com.app.freightCyber.data.source.remote.ApiDashboardService
import com.app.freightCyber.domain.model.FreightBackandError
import com.app.freightCyber.domain.repository.DashboardRepository
import com.app.freightCyber.utils.NetworkResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(private val apiDashboardService: ApiDashboardService) :
    DashboardRepository {

    private fun <T, R> makeNetworkPostRequest(
        request: T,
        apiCall: suspend (T) -> Response<R>
    ): Flow<NetworkResult<R>> = flow {
        try {
            emit(NetworkResult.Loading())
            val response = apiCall(request)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!))
            } else {
                val errorBody = response.errorBody()?.string()
                val gson = Gson()
                try {
                    val errorResponse = gson.fromJson(errorBody, FreightBackandError::class.java)
                    emit(NetworkResult.Error(Exception(errorResponse.error)))
                } catch (e: Exception) {
                    emit(NetworkResult.Error(Exception("Error: ${response.code()} ${response.message()}")))
                }
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e))
        }
    }.flowOn(Dispatchers.IO)


    private fun <R> makeNetworkGetRequest(
        apiCall: suspend () -> Response<R>
    ): Flow<NetworkResult<R>> = flow {
        try {
            emit(NetworkResult.Loading())
            val response = apiCall()
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!))
            } else {
                emit(NetworkResult.Error(Exception("Error: ${response.code()} ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e))
        }
    }.flowOn(Dispatchers.IO)


}