package cobos.santiago.core

import android.util.Log
import cobos.santiago.core.Constants.TAG

class Utils {
    companion object {
        fun print(e: Exception) = Log.e(TAG, e.stackTraceToString())
    }
}