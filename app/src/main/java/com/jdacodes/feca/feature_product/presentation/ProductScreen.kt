package com.jdacodes.feca.feature_product.presentation

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material.icons.sharp.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import com.jdacodes.feca.R
import com.jdacodes.feca.destinations.SingleProductScreenDestination
import com.jdacodes.feca.feature_product.domain.model.Product
import com.jdacodes.feca.feature_wishlist.data.mapper.toWishlistRating
import com.jdacodes.feca.feature_wishlist.domain.model.Wishlist
import com.jdacodes.feca.feature_wishlist.presentation.wishlist.WishlistViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductListElement(
    productItems: List<Product>,
    navigator: DestinationsNavigator,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel,

    ) {
    Box(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            SearchField(
                viewModel = viewModel,
                leadingIcon = {
                    Icon(
                        Icons.Sharp.Search, contentDescription = null, tint = Color.White
                    )
                },
                trailingIcon = null,
                paddingLeadingIconEnd = 10.dp,
                paddingTrailingIconStart = 10.dp,

                )
            Spacer(modifier = Modifier.height(8.dp))
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
                    val index by rememberSaveable {
                        mutableStateOf(i)
                    }
                    key(product.toString()) {
                        ProductItem(
                            product = product, navigator = navigator, modifier = modifier,

                            index = index
                        )
                    }

                }

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
    navigator: DestinationsNavigator,
    index: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ), modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(product, navigator, index)

    }
}

@Composable
fun CardContent(
    product: Product, navigator: DestinationsNavigator, index: Int, modifier: Modifier = Modifier
) {
    Row(modifier = Modifier
        .padding(12.dp)
        .animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
            )
        )
        .clickable { navigator.navigate(SingleProductScreenDestination(product = product)) }) {
        Column(
            modifier = Modifier.padding(12.dp)
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun SingleProductScreen(
    product: Product,
    navigator: DestinationsNavigator,
    viewModel: WishlistViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val inWishlist = viewModel.inWishlist(product.id).observeAsState().value != null

    Scaffold(backgroundColor = MaterialTheme.colorScheme.background, topBar = {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navigator.popBackStack()
                },
            ) {
//                    androidx.compose.material.Icon(
//                        painter = painterResource(id = R.drawable.ic_chevron_left),
//                        contentDescription = null,
//                        modifier = Modifier.size(32.dp)
//                    )
                Icon(
                    imageVector = Icons.Filled.ChevronLeft,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
            IconButton(
                onClick = {
                    if (inWishlist) {
                        viewModel.deleteFromWishlist(
                            Wishlist(
                                image = product.image,
                                title = product.title,
                                id = product.id,
                                liked = true,
                                price = product.price,
                                description = product.description,
                                category = product.category,
                                rating = product.rating.toWishlistRating()
                            )
                        )
                    } else {
                        viewModel.insertFavorite(
                            Wishlist(
                                image = product.image,
                                title = product.title,
                                id = product.id,
                                liked = true,
                                price = product.price,
                                description = product.description,
                                category = product.category,
                                rating = product.rating.toWishlistRating()
                            )
                        )
                    }
                },
            ) {
//                    androidx.compose.material.Icon(
//                        painter = if (inWishlist) {
//                            painterResource(id = R.drawable.ic_heart_fill)
//                        } else {
//                            painterResource(id = R.drawable.ic_heart)
//                        },
//                        tint = if (inWishlist) {
//                            YellowMain
//                        } else {
//                            GrayColor
//                        },
//                        contentDescription = null,
//                        modifier = Modifier.size(32.dp)
//                    )

                Icon(
                    imageVector = if (inWishlist) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Filled.FavoriteBorder
                    },
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }) {
        SingleProductScreenContent(
            product = product,
            modifier = Modifier.fillMaxSize()
        )
    }
//    Box(
//        modifier = Modifier
//            .background(MaterialTheme.colorScheme.background)
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//    ) {
//        Card(
//            colors = CardDefaults.cardColors(
//                containerColor = MaterialTheme.colorScheme.primaryContainer,
//            ), modifier = Modifier
//                .padding(vertical = 4.dp, horizontal = 8.dp)
//                .fillMaxWidth()
//        ) {
//
//            Column(
//                modifier = Modifier
//                    .padding(24.dp)
//                    .animateContentSize(
//                        animationSpec = spring(
//                            dampingRatio = Spring.DampingRatioMediumBouncy,
//                            stiffness = Spring.StiffnessLow
//                        )
//                    )
//                    .fillMaxWidth()
//
//            ) {
//                if (product.id != null) {
//                    Text(text = "Item: ${product.id}")
//                }
//                Spacer(modifier = Modifier.padding(vertical = 8.dp))
//                AsyncImage(
//                    model = product.image,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .heightIn(min = 20.dp, max = 280.dp)
//                        .align(Alignment.CenterHorizontally),
//                    contentScale = ContentScale.Inside
//                )
//                Spacer(modifier = Modifier.padding(vertical = 8.dp))
//                product.title?.let {
//                    Text(
//                        text = it,
//                        style = MaterialTheme.typography.bodyMedium.copy(
//                            fontWeight = FontWeight.ExtraBold
//                        ),
//                        maxLines = 2,
//                        textAlign = TextAlign.Start,
//                        overflow = TextOverflow.Ellipsis,
//                        modifier = Modifier.align(Alignment.Start)
//                    )
//                }
//                Spacer(modifier = Modifier.padding(vertical = 8.dp))
//                Text(
//                    text = "$ ${product.price}",
//                    style = MaterialTheme.typography.bodyMedium.copy(
//                        fontWeight = FontWeight.SemiBold
//                    ),
//                    maxLines = 1,
//                    textAlign = TextAlign.Start,
//                    modifier = Modifier.align(Alignment.Start)
//                )
//                Spacer(modifier = Modifier.padding(vertical = 8.dp))
//                Row(
//                    horizontalArrangement = Arrangement.Start,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//
//                    Icon(
//                        Icons.Sharp.Star,
//                        contentDescription = stringResource(id = R.string.star_rating_icon),
//                        modifier = Modifier.size(18.dp)
//                    )
//                    Text(
//                        text = " ${product.rating?.rate}",
//                        style = MaterialTheme.typography.bodyMedium.copy(
//                            fontWeight = FontWeight.SemiBold
//                        ),
//                        maxLines = 1,
//                        textAlign = TextAlign.Start,
//
//                        )
//                }
//            }
//        }
//    }
}

@Composable
fun SingleProductScreenContent(
    product: Product,
    modifier: Modifier = Modifier,
) {

    Column {
        Box(
            modifier = modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
//            Image(
//                painter = rememberAsyncImagePainter(
//                    ImageRequest.Builder(LocalContext.current)
//                        .data(data = product.image)
//                        .apply(block = fun ImageRequest.Builder.() {
//                            crossfade(true)
//                            placeholder(R.drawable.ic_placeholder)
//                        }).build()
//                ),
//                contentDescription = null,
//                modifier = modifier
//                    .fillMaxWidth()
//                    .height(250.dp)
//                    .align(Alignment.Center),
//                contentScale = ContentScale.Inside
//            )

            AsyncImage(
//                model = product.image,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.image)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_placeholder)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .heightIn(max = 250.dp)
                    .align(Alignment.Center),
                contentScale = ContentScale.Inside
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = modifier
                .fillMaxWidth()
                .weight(2f),
//            elevation = 0.dp,
            shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp),
//            backgroundColor = MainWhiteColor
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {

            Box(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = product.title,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    val rating: Float by remember { mutableStateOf(product.rating.rate.toFloat()) }

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
//                        RatingBar(
//                            value = rating,
//                            config = RatingBarConfig()
//                                .activeColor(YellowMain)
//                                .inactiveColor(GrayColor)
//                                .stepSize(StepSize.HALF)
//                                .numStars(5)
//                                .isIndicator(true)
//                                .size(16.dp)
//                                .padding(3.dp)
//                                .style(RatingBarStyle.HighLighted),
//                            onValueChange = {},
//                            onRatingChanged = {}
//                        )
                        RatingBar(
                            value = rating,
                            style = RatingBarStyle.Fill(
                                activeColor = Color.Yellow,
                                inActiveColor = Color.Gray

                            ),
                            onValueChange = {},
                            onRatingChanged = {},

                            numOfStars = 5,
                            size = 16.dp,
                            spaceBetween = 3.dp,
                            isIndicator = true,
                            stepSize = StepSize.HALF,
                            hideInactiveStars = false
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "(${product.rating.count})",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = product.description,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )

                }
                Row(
                    modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "$${product.price}",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = {
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                            backgroundColor = MaterialTheme.colorScheme.tertiary,
                        ),
                        shape = CircleShape
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            text = stringResource(R.string.add_to_cart)
                        )
                    }
                }
            }
        }
    }

}

