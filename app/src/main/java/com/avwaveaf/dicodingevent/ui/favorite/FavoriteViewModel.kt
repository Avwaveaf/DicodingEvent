package com.avwaveaf.dicodingevent.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.avwaveaf.dicodingevent.data.EventRepository
import com.avwaveaf.dicodingevent.data.local.entity.Event

class FavoriteViewModel(private val repository: EventRepository) : ViewModel() {
    fun getFavoriteEvents(): LiveData<List<Event>> = repository.getFavoriteEvents()
}
