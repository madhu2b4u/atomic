package com.demo.sport.main.presentation.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.sport.common.AppCoroutineDispatcherProvider
import com.demo.sport.common.AppCoroutineDispatchers
import com.demo.sport.common.Event
import com.demo.sport.common.SingleLiveEvent
import com.demo.sport.main.data.models.Sport
import com.demo.sport.main.domain.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.demo.sport.common.Result
import com.demo.sport.common.Status
import com.demo.sport.main.domain.MainUseCaseImpl
import kotlinx.coroutines.delay


@HiltViewModel
class SportViewModel @Inject constructor(
    private val useCase: MainUseCase,
) : ViewModel(), LifecycleObserver {

    val data: LiveData<Sport> get() = _data
    private val _data = MediatorLiveData<Sport>()

    val result = MediatorLiveData<Event<Result<List<Sport>>>>()

    val showLoader: LiveData<Unit> get() = _showLoader
    private val _showLoader = SingleLiveEvent<Unit>()

    private val dispatcher: AppCoroutineDispatchers = AppCoroutineDispatcherProvider.dispatcher()


    fun getSports() {

        viewModelScope.launch {
            withContext(dispatcher.main()) {
                delay(2500)
                result.addSource(useCase.getSports()) {
                    result.value = Event(it)
                    handleResponse(it)
                }
            }
        }
    }

    private fun handleResponse(it: Result<List<Sport>>) {
        when (it.status) {
            Status.LOADING -> {
                _showLoader.call()
            }

            Status.ERROR -> {
                //handle if there is an error
            }

            Status.SUCCESS -> it.data?.let { it1 -> handleSportsData(it.data) }
        }
    }

    private fun handleSportsData(data: List<Sport>) {
        _data.value = data.random()
    }
}