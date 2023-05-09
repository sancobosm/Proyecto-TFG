package cobos.santiago.data.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MusicDatabase {
    val db = Firebase.firestore
    val docRef = db.collection("songs")
    fun getAllSongs() {
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
}