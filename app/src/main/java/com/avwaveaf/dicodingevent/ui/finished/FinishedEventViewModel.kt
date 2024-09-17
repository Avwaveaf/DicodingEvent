package com.avwaveaf.dicodingevent.ui.finished

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

class FinishedEventViewModel : ViewModel() {
    private val _searchedEvents = MutableLiveData<List<EventItem>>()
    val searchedEvents: LiveData<List<EventItem>> = _searchedEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<EventWrapper<String>>()
    val snackbarText: LiveData<EventWrapper<String>> = _snackbarText


    fun searchEvents(searchQuery: String) {
        _isLoading.value = true
        val client = if (searchQuery.isEmpty()) {
            ApiConfig.getApiService().getFinishedEvents(active = 0)
        }else{
            ApiConfig.getApiService().searchEvents(active = -1, searchQuery)
        }

        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _searchedEvents.value = response.body()?.listEvents
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                    if (response.body()?.message?.trim() == "Unable to resolve host \"event-api.dicoding.dev\": No address associated with hostname") {
                        _snackbarText.value = EventWrapper("No Internet Connection")
                    } else {
                        _snackbarText.value = EventWrapper(response.body()?.message.toString())
                    }
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
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
        private const val TAG = "FinishedEventViewModel"
    }
}