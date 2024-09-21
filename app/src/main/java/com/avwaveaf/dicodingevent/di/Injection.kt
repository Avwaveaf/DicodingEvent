package com.avwaveaf.dicodingevent.di

import android.content.Context
import com.avwaveaf.dicodingevent.data.EventRepository
import com.avwaveaf.dicodingevent.data.local.room.EventDatabase
import com.avwaveaf.dicodingevent.data.remote.retrofit.ApiConfig
import com.avwaveaf.dicodingevent.util.PreferenceManager

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventDatabase.getDatabase(context)
        val dao = database.eventDao()
        val preferenceManager = PreferenceManager.getInstance(context)
        return EventRepository.getInstance(dao, apiService, preferenceManager)
    }
}