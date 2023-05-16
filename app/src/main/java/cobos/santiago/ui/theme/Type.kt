package cobos.santiago.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Definir estilos personalizados
val songNameStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp
)

val SpotifyTextStyleDark = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    fontSize = 15.sp,
    color = md_theme_dark_onPrimaryContainer
)
val SpotifyTextStyleLight = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    fontSize = 15.sp,
    color = md_theme_dark_tertiaryContainer
)

val SpotifyTextStyleDarkArtist = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    color = md_theme_dark_onPrimaryContainer
)
val SpotifyTextStyleLigtArtist = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    color = md_theme_dark_tertiaryContainer
)

val durationStringStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    color = Color.Gray
)

// Agregar estilos personalizados a Typography
