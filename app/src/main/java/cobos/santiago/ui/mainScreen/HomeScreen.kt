package cobos.santiago.ui.mainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun HomeScreen(){
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {

        HomeBottomBar()
    }
}










