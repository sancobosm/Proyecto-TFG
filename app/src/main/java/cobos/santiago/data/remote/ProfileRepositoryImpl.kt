package cobos.santiago.data.remote

import cobos.santiago.core.Constants.USERS
import cobos.santiago.domain.model.Response
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import cobos.santiago.domain.model.Response.Success
import cobos.santiago.domain.repository.ProfileRepository
import cobos.santiago.domain.repository.RevokeAccessResponse
import cobos.santiago.domain.repository.SignOutResponse

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    private var signInClient: GoogleSignInClient,
    private val db: FirebaseFirestore
) : ProfileRepository {
    override val displayName = auth.currentUser?.displayName.toString()
    override val photoUrl = auth.currentUser?.photoUrl.toString()

    override suspend fun signOut(): SignOutResponse {
        return try {
            oneTapClient.signOut().await()
            auth.signOut()
            Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun revokeAccess(): RevokeAccessResponse {
        return try {
            auth.currentUser?.apply {
                db.collection(USERS).document(uid).delete().await()
                signInClient.revokeAccess().await()
                oneTapClient.signOut().await()
                delete().await()
            }
            Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}