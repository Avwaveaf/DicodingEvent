package com.avwaveaf.dicodingevent.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avwaveaf.dicodingevent.data.EventRepository
import com.avwaveaf.dicodingevent.ui.detailevent.DetailEventViewModel
import com.avwaveaf.dicodingevent.ui.favorite.FavoriteViewModel
import com.avwaveaf.dicodingevent.ui.finished.FinishedEventViewModel
import com.avwaveaf.dicodingevent.ui.home.HomeViewModel

class ViewModelFactory(private val repository: EventRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FinishedEventViewModel::class.java)) {
            return FinishedEventViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(DetailEventViewModel::class.java)) {
            return DetailEventViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
