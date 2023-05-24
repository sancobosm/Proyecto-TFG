package cobos.santiago.data.entities

import cobos.santiago.R

data class User(
    val image: Int = R.drawable.profile_image,
    val firstName: String = "firstName",
    val secondName: String = "secondName",
    val email: String = "email",
    var password: String = ""
)
