package cobos.santiago.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    //for email text
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    fun onTextChanged(email: String) {
        _email.value = email
    }

    //for password text
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password
    fun onPasswordChanged(password: String) {
        _password.value = password
    }
}
