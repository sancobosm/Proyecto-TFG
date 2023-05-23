package cobos.santiago.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val _isUpdateSuccess = MutableLiveData<Boolean>()
    val isUpdateSuccess: LiveData<Boolean> = _isUpdateSuccess

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _newName = MutableLiveData<String>()
    val newName: LiveData<String> = _newName

    private val _newPassword = MutableLiveData<String>()
    val newPassword: LiveData<String> = _newPassword

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: LiveData<String> = _confirmPassword

    init {
        _newName.value = ""
        _newPassword.value = ""
        _confirmPassword.value = ""
        _isUpdateSuccess.value = false
        _errorMessage.value = ""
    }

    fun setName(name: String) {
        _newName.value = name
    }

    fun setPassword(password: String) {
        _newPassword.value = password
    }

    fun setConfirmPassword(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
    }

    fun updateProfile() {
        val newNameValue = _newName.value.orEmpty().trim()
        val newPasswordValue = _newPassword.value.orEmpty()
        val confirmPasswordValue = _confirmPassword.value.orEmpty()

        if (newNameValue.isNotEmpty()) {
            // Update profile name logic
            // Replace the code below with your actual implementation
            //currentUser?.updateProfile(buildProfileUpdates(newNameValue))
        }

        if (newPasswordValue.isNotEmpty() && newPasswordValue == confirmPasswordValue) {
            // Update password logic
            // Replace the code below with your actual implementation
            //currentUser?.updatePassword(newPasswordValue)
        } else if (newPasswordValue.isNotEmpty() && newPasswordValue != confirmPasswordValue) {
            _errorMessage.value = "Password do not match"
        }

        // Reset values after update
        _newName.value = ""
        _newPassword.value = ""
        _confirmPassword.value = ""
        _isUpdateSuccess.value = true
        _errorMessage.value = ""
    }

    private fun buildProfileUpdates(newName: String): UserProfileChangeRequest {
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(newName)
            .build()
        return profileUpdates
    }

    fun logout() {
        // Implement your logout logic here
    }
}
