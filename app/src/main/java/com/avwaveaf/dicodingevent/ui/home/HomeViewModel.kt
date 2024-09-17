package com.avwaveaf.dicodingevent.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avwaveaf.dicodingevent.data.response.EventItem
import com.avwaveaf.dicodingevent.data.response.EventResponse
import com.avwaveaf.dicodingevent.data.retrofit.ApiConfig
import com.avwaveaf.dicodingevent.util.EventWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _activeEvents = MutableLiveData<List<EventItem>>()
    val activeEvents: LiveData<List<EventItem>> = _activeEvents

    private val _finishedEvents = MutableLiveData<List<EventItem>>()
    val finishedEvents: LiveData<List<EventItem>> = _finishedEvents

    private val _isLoadingActiveEvent = MutableLiveData<Boolean>()
    val isLoadingActiveEvent: LiveData<Boolean> = _isLoadingActiveEvent

    private val _isLoadingFinishedEvent = MutableLiveData<Boolean>()
    val isLoadingFinishedEvent: LiveData<Boolean> = _isLoadingFinishedEvent

    private val _snackbarText = MutableLiveData<EventWrapper<String>>()
    val snackbarText: LiveData<EventWrapper<String>> = _snackbarText

    init {
        fetchEvents(true)
        fetchEvents(false)
    }

    private fun fetchEvents(isActive: Boolean) {
        val loadingLiveData = if (isActive) _isLoadingActiveEvent else _isLoadingFinishedEvent
        val eventsLiveData = if (isActive) _activeEvents else _finishedEvents

        loadingLiveData.value = true
        val client = if (isActive) {
            ApiConfig.getApiService().getActiveEvents(active = 1)
        } else {
            ApiConfig.getApiService().getFinishedEvents(active = 0)
        }

        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                loadingLiveData.value = false
                if (response.isSuccessful && response.body() != null) {
                    eventsLiveData.value = response.body()?.listEvents
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    if (response.body()?.message?.trim() == "Unable to resolve host \"event-api.dicoding.dev\": No address associated with hostname") {
                        _snackbarText.value = EventWrapper("No Internet Connection")
                    } else {
                        _snackbarText.value = EventWrapper(response.body()?.message.toString())
                    }
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                loadingLiveData.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                if (t.message?.trim() == "Unable to resolve host \"event-api.dicoding.dev\": No address associated with hostname") {
                    _snackbarText.value = EventWrapper("No Internet Connection")
                } else {
                    _snackbarText.value = EventWrapper(t.message.toString())
                }
            }
        })
    }


    companion object {
        private const val TAG = "HomeViewModel"
    }
}