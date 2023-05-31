package cobos.santiago.data.entities

import cobos.santiago.R

data class User(
    val image: Int = R.drawable.profile_image,
    val firstName: String = "Santiago",
    val secondName: String = "Cobos Melgares",
    val email: String = "sancobosm@gmail.com",
    var password: String = ""
)
