package cobos.santiago.ui.viewmodels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {

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

    //for button
    private val _isButtonEnable = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnable

    fun onLoginChanged(email: String, password: String){
        _email.value = email
        _password.value = password
        _isButtonEnable.value = enableButton(email,password)
    }

    fun enableButton(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6

}
