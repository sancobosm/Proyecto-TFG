package cobos.santiago.ui.viewmodels

import androidx.compose.ui.graphics.painter.Painter
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

    private val _currentImage: MutableLiveData<Painter> = MutableLiveData()
    var currentImage: LiveData<Painter> = _currentImage

    private val _selectedImageUri: MutableLiveData<String> = MutableLiveData("")
    var selectedImageUri: LiveData<String> = _selectedImageUri
    fun saveProfileImage(imageUri: String) {
        _selectedImageUri.value = imageUri
    }

    fun setImageUri(uri: String) {
        _selectedImageUri.value = uri
    }

    fun setImage(uri: Painter) {
        _currentImage.value = uri
    }

    init {
        _currentUser.value = User()
    }

    fun setCurrentUser(user: User) {
        _currentUser.value = user
    }

    fun getCurrentUser() = _currentUser
}
