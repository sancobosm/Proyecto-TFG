package cobos.santiago.ui.viewmodels

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import cobos.santiago.data.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _currentUser: MutableLiveData<User> = MutableLiveData()
    var currentUser: LiveData<User> = _currentUser

    private val _selectedImageUri = mutableStateOf<Uri?>(null)
    val selectedImageUri: State<Uri?> = _selectedImageUri

    fun setSelectedImageUri(uri: Uri) {
        _selectedImageUri.value = uri
    }

    init {
        _currentUser.value = User()
    }

    fun setCurrentUser(user: User) {
        _currentUser.value = user
    }

    fun getCurrentUser() = _currentUser
}
