package com.jdacodes.feca.core.presentation


import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.jdacodes.feca.core.presentation.graphs.RootNavigationGraph
import com.jdacodes.feca.core.presentation.theme.FakeECommerceAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FakeECommerceAppTheme(
                dynamicColor = false
            ) {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}


@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    showBackground = true,
    name = "DefaultPreview",
    widthDp = 320
)
@Preview(showBackground = true)
@Composable
fun ProductsPreview() {
    FakeECommerceAppTheme {
//        Product("Android")
//        ProductScreen()

    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProductsPreviewDark() {
    FakeECommerceAppTheme {
//        Product("Android")
//        ProductScreen()
    }
}

