package cobos.santiago.data.remote

import androidx.navigation.NavController
import cobos.santiago.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth

class Auth {
    private val db = FirebaseAuth.getInstance()

    fun createUser(user:String, password:String){
        db.createUserWithEmailAndPassword(user,password).addOnCompleteListener{
            if(it.isSuccessful) {
                println("USUARIO CREADO*************")
            }else{
                println("ERROR NO CREADO**********")
            }
        }
    }

    fun makeLogin(user: String, password: String, navController: NavController, onError: () -> Unit){
        db.signInWithEmailAndPassword(user,password).addOnCompleteListener{
            if(it.isSuccessful) {
                navController.navigate(AppScreens.HomeScreen.ruta)
            }else{
                onError()
            }
        }
    }
}