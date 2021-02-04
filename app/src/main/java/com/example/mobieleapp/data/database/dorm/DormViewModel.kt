package com.example.mobieleapp.data.database.dorm

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class DormViewModel(private val repository: DormRepository) : ViewModel() {
    val allDorms: MutableLiveData<List<Dorm>> = repository.allDorms.asLiveData() as MutableLiveData<List<Dorm>>


    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(dorm: Dorm) = viewModelScope.launch {
        repository.insert(dorm)

    }fun delete() = viewModelScope.launch {
        repository.deleteAll()

    }
}

class DormViewModelFactory(private val repository: DormRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DormViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DormViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}