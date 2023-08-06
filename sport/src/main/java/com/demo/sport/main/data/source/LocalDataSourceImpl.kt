package com.demo.sport.main.data.source

import com.demo.sport.common.qualifiers.IO
import com.demo.sport.main.data.models.Sport
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LocalDataSourceImpl @Inject constructor() : LocalDataSource {

    override suspend fun getSports(): List<Sport> {
        return Sport.createMockedSports()
    }

}