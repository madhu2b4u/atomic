package com.demo.sport.main.data.repository

import androidx.lifecycle.liveData
import com.demo.sport.common.Result
import com.demo.sport.main.data.models.repository.MainRepository
import com.demo.sport.main.data.source.LocalDataSource
import javax.inject.Inject


class MainRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource) :
    MainRepository {
    override suspend fun getSports() = liveData {
        emit(Result.loading())
        try {
            val result = localDataSource.getSports()
            emit(Result.success(result))
        } catch (exception: Exception) {
            emit(Result.error(exception.message ?: "", null))
        }
    }
}




