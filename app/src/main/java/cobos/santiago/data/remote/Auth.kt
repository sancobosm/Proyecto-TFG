package cobos.santiago.data.remote

import androidx.navigation.NavController
import cobos.santiago.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth

class Auth () {
    private val db = FirebaseAuth.getInstance()

    /*private val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("828425020051-jjbmtgdofjc2aar66vb0n439g6cdoiij.apps.googleusercontent.com")
        .requestEmail()
        .build()*/

    fun createUser(user: String, password: String) {
        db.createUserWithEmailAndPassword(user, password).addOnCompleteListener {
            if (it.isSuccessful) {
                println("USUARIO CREADO*************")
            } else {
                println("ERROR NO CREADO**********")
            }
        }
    }

    fun makeLogin(
        user: String,
        password: String,
        navController: NavController,
        onError: () -> Unit
    ) {
        db.signInWithEmailAndPassword(user, password).addOnCompleteListener {
            if (it.isSuccessful) {
                navController.navigate(AppScreens.HomeScreen.ruta)
            } else {
                onError()
            }
        }
    }


}