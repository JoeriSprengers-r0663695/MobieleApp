package com.example.mobieleapp.data.database.user

import androidx.lifecycle.*

import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    val allUsers: MutableLiveData<List<User>> = repository.allUsers.asLiveData() as MutableLiveData<List<User>>
    /*
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)

    }fun delete() = viewModelScope.launch {
        repository.deleteAll()

    }


}

class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}