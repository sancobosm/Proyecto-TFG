package cobos.santiago.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cobos.santiago.data.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
) : ViewModel() {

    private val _currentUser: MutableLiveData<User> = MutableLiveData()
    var currentUser: LiveData<User> = _currentUser

    init {
        _currentUser.value = User()
    }

    fun setCurrentUser(user: User) {
        _currentUser.value = user
    }

    fun getCurrentUser() = _currentUser
}
