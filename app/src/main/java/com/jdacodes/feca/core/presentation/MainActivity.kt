package com.jdacodes.feca.core.presentation


import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jdacodes.feca.NavGraphs
import com.jdacodes.feca.core.presentation.theme.FakeECommerceAppTheme
import com.jdacodes.feca.destinations.AccountScreenDestination
import com.jdacodes.feca.destinations.CartScreenDestination
import com.jdacodes.feca.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.rememberNavHostEngine
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
                val navController = rememberNavController()
                val navHostEngine = rememberNavHostEngine()
                val newBackStackEntry by navController.currentBackStackEntryAsState()
                val route = newBackStackEntry?.destination?.route

                CustomScaffold(
                    navController = navController,
                    showBottomBar = route in listOf(
                        HomeScreenDestination.route,
                        CartScreenDestination.route,
                        AccountScreenDestination.route
                    )
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            navController = navController,
                            engine = navHostEngine
                        )
                    }

                }

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

