package com.jdacodes.feca


import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Star
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import coil.compose.AsyncImage
import com.jdacodes.feca.feature_product.domain.model.Product
import com.jdacodes.feca.feature_product.presentation.ProductState
import com.jdacodes.feca.feature_product.presentation.ProductViewModel
import com.jdacodes.feca.ui.theme.FakeECommerceAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FakeECommerceAppTheme(
                dynamicColor = false
            ) {
                MyApp(
                    modifier = Modifier.fillMaxSize()
                )

            }


        }
    }
}


@Composable
fun MyApp(
    modifier: Modifier = Modifier
) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            ProductScreen()
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Fake E-Commerce App!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }

}

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = hiltViewModel()

) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    // TODO: Not yet implemented: for Product details
    val navController = rememberNavController()


    LaunchedEffect(key1 = true) {
        viewModel.getProductsList()
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ProductViewModel.UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,

    ) { paddingValues ->
        FecaNavHost(
            state = state,
            modifier = modifier.padding(paddingValues),
            navController = navController

        )

    }
}

@Composable
fun FecaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    state: ProductState
) {
    NavHost(
        navController = navController,
        startDestination = Products.route,
        modifier = modifier
    ) {

            composable(route = Products.route) {
                ProductListElement(
                    productItems = state.productItems,
                    isLoading = state.isLoading,
                    onProductClick = { id ->
                        navController.navigateToSingleProduct(id)
                    }
                )
            }
            composable(
                route = SingleProduct.routeWithArgs,
                arguments = SingleProduct.arguments
            ) { navBackStackEntry ->
                val productId = navBackStackEntry.arguments?.getInt(SingleProduct.productIdArg)
                SingleProductScreen(
                    productItems = state.productItems,
                    productId = productId,
                    modifier = modifier
                )
            }

        productsGraph(
            navController = navController,
            state = state,
            modifier = modifier
        )
    }


}

fun NavGraphBuilder.productsGraph(
    navController: NavController,
    state: ProductState,
    modifier: Modifier = Modifier
) {
    navigation(startDestination = Products.route, route = "products") {
        composable(route = Products.route) {
            ProductListElement(
                productItems = state.productItems,
                isLoading = state.isLoading,
                onProductClick = { id ->
                    navController.navigateToSingleProduct(id)
                }
            )
        }
        composable(
            route = SingleProduct.routeWithArgs,
            arguments = SingleProduct.arguments
        ) { navBackStackEntry ->
            val productId = navBackStackEntry.arguments?.getInt(SingleProduct.productIdArg)
            SingleProductScreen(
                productItems = state.productItems,
                productId = productId,
                modifier = modifier
            )
        }
    }

}

@Composable
fun SingleProductScreen(
    productItems: List<Product>,
    productId: Int? = productItems.first().id,
    modifier: Modifier
) {
    val productId by rememberSaveable {
        mutableStateOf(productId)
    }
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())

    ) {


        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .fillMaxWidth()
        ) {

            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                    .fillMaxWidth()

            ) {
                if (productId != null) {
                    Text(text = "Item: ${productItems[productId!!].id}")
                }
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                AsyncImage(
                    model = productItems[productId!!].image,
                    contentDescription = null,
                    modifier = Modifier
                        .heightIn(min = 20.dp, max = 280.dp)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Inside
                )
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                productItems[productId!!].title?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.ExtraBold
                        ),
                        maxLines = 2,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = "$ ${productItems[productId!!].price}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    maxLines = 1,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        Icons.Sharp.Star,
                        contentDescription = stringResource(id = R.string.star_rating_icon),
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = " ${productItems[productId!!].rating?.rate}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        maxLines = 1,
                        textAlign = TextAlign.Start,

                        )
                }
            }


//            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductListElement(
    productItems: List<Product>,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    onProductClick: (Int) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalItemSpacing = 8.dp,
                modifier = modifier.fillMaxSize()
            ) {
                itemsIndexed(
                    productItems
                ) { i, product ->
                    var index by rememberSaveable {
                        mutableStateOf(i)
                    }
                    key(product.toString()) {
                        ProductItem(
                            product = product,
                            modifier = modifier,
                            onProductClick = onProductClick,
                            index = index
                        )
                    }

                }

//                items(
//                    items = productItems,
//                    key = { productItem -> productItem.id!! }
//                ) { product ->
//                    ProductItem(product = product, modifier, onProductClick)
//                }
            }
        }
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    index: Int,
    modifier: Modifier = Modifier,
    onProductClick: (Int) -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(product, onProductClick, index)

    }
}

@Composable
fun CardContent(
    product: Product,
    onProductClick: (Int) -> Unit = {},
    index: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .clickable { onProductClick(index) }
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Text(text = "Item: ${product.id}")
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .heightIn(min = 40.dp, max = 100.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Inside,
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            product.title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    maxLines = 2,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = "$ ${product.price}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                maxLines = 1,
                textAlign = TextAlign.Start,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    Icons.Sharp.Star,
                    contentDescription = stringResource(id = R.string.star_rating_icon),
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = " ${product.rating?.rate}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    maxLines = 1,
                    textAlign = TextAlign.Start,

                    )
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

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    FakeECommerceAppTheme() {
        //do nothing
        OnboardingScreen(onContinueClicked = {})
    }
}


@Preview(
    showBackground = true,
    widthDp = 320,
    heightDp = 320
)
@Composable
fun MyAppPreview() {
    FakeECommerceAppTheme() {
        MyApp(Modifier.fillMaxSize())
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    heightDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MyAppPreviewDark() {
    FakeECommerceAppTheme() {
        MyApp(Modifier.fillMaxSize())
    }
}

fun NavHostController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        //When pressing back will pop the backstack to the start destination which is Overview
        // this is to avoid a large stack in the backstack
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // To make sure only one instance of the destination when tapping on the navigation
        launchSingleTop = true
        //when tapping the same tab in a row will keep prev data and user state without reloading
        // if there is no saved state with destination id, this has no effect
        restoreState = true

    }
}

private fun NavHostController.navigateToSingleProduct(id: Int) {
    this.navigateSingleTopTo("${SingleProduct.route}/$id")
}

fun NavController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        //When pressing back will pop the backstack to the start destination which is Overview
        // this is to avoid a large stack in the backstack
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // To make sure only one instance of the destination when tapping on the navigation
        launchSingleTop = true
        //when tapping the same tab in a row will keep prev data and user state without reloading
        // if there is no saved state with destination id, this has no effect
        restoreState = true

    }
}

private fun NavController.navigateToSingleProduct(id: Int) {
    this.navigateSingleTopTo("${SingleProduct.route}/$id")
}