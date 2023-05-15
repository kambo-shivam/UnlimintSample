package com.app.unlimint.ui.main.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.unlimint.data.model.JokesModel
import com.app.unlimint.data.repository.UnlimintRepository
import com.app.unlimint.utils.NetworkHelper
import com.app.unlimint.utils.Resource
import kotlinx.coroutines.*

class UnlimintViewModel(
    private val mainRepository: UnlimintRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private lateinit var countDownTimer: CountDownTimer
    private val _users = MutableLiveData<Resource<JokesModel>>()
    val users: LiveData<Resource<JokesModel>>
        get() = _users

    private val jokesLive: MutableLiveData<List<JokesModel>> by lazy { MutableLiveData() }
    val jokesLiveData: LiveData<List<JokesModel>> by lazy { jokesLive }

    init {
        fetchJokes()
        runCountDownTimer()
    }

    private fun fetchJokes() {
        viewModelScope.launch(Dispatchers.IO) {
            getDbData(this)
            fetchUsers()
        }
    }

    private suspend fun getDbData(coroutineScope: CoroutineScope) {
        val data = coroutineScope.async { mainRepository.getAllJokes() }.await()
        jokesLive.postValue(data)
    }

    private fun runCountDownTimer() {
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(p0: Long) {
                Log.d("tiktok:", (p0 / 1000).toString())
            }

            override fun onFinish() {
                viewModelScope.launch(Dispatchers.IO) {
                    fetchUsers()
                }
                countDownTimer.cancel()
                countDownTimer.start()
            }

        }
        countDownTimer.start()
    }

    private suspend fun fetchUsers() {
        if (networkHelper.isNetworkConnected()) {
            mainRepository.getUsers().let {
                if (it.isSuccessful) {
                    _users.postValue(Resource.success(it.body()))
                    viewModelScope.launch(Dispatchers.IO) {
                        if (jokesLive.value?.size == 10) {
                            mainRepository.deleteFirstJoke(jokesLive.value?.get(0))
                            mainRepository.insertJoke(it.body()!!)
                        } else {
                            mainRepository.insertJoke(it.body()!!)
                            getDbData(this)
                        }
                    }
                } else _users.postValue(Resource.error(it.errorBody().toString(), null))
            }
        } else _users.postValue(Resource.error("No internet connection", null))
    }
}