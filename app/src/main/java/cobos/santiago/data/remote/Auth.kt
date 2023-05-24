package cobos.santiago.data.remote

import androidx.navigation.NavController
import cobos.santiago.data.entities.User
import cobos.santiago.navigation.AppScreens
import cobos.santiago.ui.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class Auth @Inject constructor(
    private val userViewModel: UserViewModel
) {
    private val db = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()


    /*private val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("828425020051-jjbmtgdofjc2aar66vb0n439g6cdoiij.apps.googleusercontent.com")
        .requestEmail()
        .build()*/

    fun createUser(
        user: String,
        password: String,
        firstName: String = "",
        secondName: String = ""
    ) {
        db.createUserWithEmailAndPassword(user, password).addOnCompleteListener { authResult ->
            if (authResult.isSuccessful) {
                val newUser = User(
                    email = user,
                    firstName = firstName,
                    secondName = secondName
                    // Agrega aquí cualquier otro dato que desees guardar en el usuario
                )

                firestore.collection("users")
                    .add(newUser)
                    .addOnSuccessListener { documentReference ->
                        newUser.password = "" // No guardes la contraseña en el ViewModel
                        userViewModel.setCurrentUser(newUser)
                        println("Usuario creado en Firestore con ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { exception ->
                        println("Error al crear el usuario en Firestore: ${exception.message}")
                    }
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
                navController.navigate(AppScreens.MyScaffold.route)
            } else {
                onError()
            }
        }
    }

    fun isUserLoggedIn(): Boolean {
        val firebaseUser = db.currentUser
        return firebaseUser != null
    }

    fun getLoggedInUserEmail(): String? {
        val user = db.currentUser
        return user?.email
    }

}