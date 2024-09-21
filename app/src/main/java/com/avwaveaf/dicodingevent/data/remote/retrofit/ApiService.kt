package com.avwaveaf.dicodingevent.data.remote.retrofit

import com.avwaveaf.dicodingevent.data.remote.response.EventDetailResponse
import com.avwaveaf.dicodingevent.data.remote.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getActiveEvents(
        @Query("active") active: Int? = 1
    ): Call<EventResponse>

    @GET("events")
    fun getFinishedEvents(
        @Query("active") active: Int? = 0
    ): Call<EventResponse>

    @GET("events")
    fun searchEvents(
        @Query("active") active: Int? = -1,
        @Query("q") searchQuery: String
    ): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: String,
    ): Call<EventDetailResponse>

    @GET("events?active=-1&limit=1")
    fun getEventNotification(): Call<EventResponse>
}