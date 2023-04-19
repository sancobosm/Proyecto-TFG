package cobos.santiago

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cobos.santiago.material3Compatibilities.pagerTabIndicatorOffset
import cobos.santiago.navigation.AppScreens
import cobos.santiago.ui.loginScreen.MyLoginView
import com.airbnb.lottie.compose.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyFirstScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(30.dp))
    ) {
        MyBackroundAnimation()
    }
    MyHorizontalPager(navController)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyHorizontalPager(navController: NavController) {
    val pagerState = rememberPagerState()
    val tabItems = listOf(
        R.string.login,
        R.string.sing_up
    )
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 190.dp, start = 10.dp, end = 10.dp, bottom = 50.dp)
            .clip(RoundedCornerShape(30.dp))
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier
                .padding(10.dp)
                .clip(
                    RoundedCornerShape(30.dp)
                ),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .pagerTabIndicatorOffset(pagerState, tabPositions = tabPositions)
                        .height((2.dp))
                )
            }
        ) {
            tabItems.forEachIndexed { index, tittle ->
                Tab(
                    text = {
                        Text(
                            stringResource(id = tittle),
                            style = if (pagerState.currentPage == index)
                                TextStyle(
                                    fontSize = 17.sp
                                )
                            else TextStyle(
                                fontSize = 13.sp
                            )
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    })
            }
        }

        HorizontalPager(
            count = tabItems.size,
            state = pagerState
        ) { page ->
            when (page) {
                0 -> {
                    MyLoginView(navController)
                }
                1 -> {
                    MyRegisterView()
                }
            }
        }
    }

}




@Composable
fun MyBackroundAnimation() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.compose))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = { progress }
    )
}

@Composable
fun MyForgotPassword() {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            Modifier
                .height(1.dp)
                .weight(1f),
            color = Color(0xFFB5B5B5)
        )
        Text(
            text = "Forgot your password?",
            modifier = Modifier.padding(horizontal = 9.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB5B5B5)
        )
        Divider(
            Modifier
                .height(1.dp)
                .weight(1f),
            color = Color(0xFFB5B5B5)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFieldPassword2(text: String, onValueChanged: (String) -> Unit, s: String) {
    var passwordVisibility by remember { mutableStateOf(false) }
    TextField(
        value = text,
        onValueChange = onValueChanged,
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Lock, contentDescription = "")
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password
        ),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                val image = if (passwordVisibility) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                }
                Icon(imageVector = image, contentDescription = null)
            }
        },
        placeholder = {
            Text(text = s, color = Color(0xFFB5B5B5))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyField2(text: String, onValueChanged: (String) -> Unit, s: String) {
    var passwordVisibility by remember { mutableStateOf(false) }
    TextField(
        value = text,
        onValueChange = onValueChanged,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Email, contentDescription = "")
        },
        singleLine = true,
        placeholder = {
            Text(text = s, color = Color(0xFFB5B5B5))
        }
    )
}







