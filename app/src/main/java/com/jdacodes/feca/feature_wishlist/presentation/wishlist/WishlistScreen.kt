package com.jdacodes.feca.feature_wishlist.presentation.wishlist

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.jdacodes.feca.R
import com.jdacodes.feca.core.util.UiEvents
import com.jdacodes.feca.destinations.SingleProductScreenDestination
import com.jdacodes.feca.feature_wishlist.data.local.WishlistEntity
import com.jdacodes.feca.feature_wishlist.data.mapper.toDomain
import com.jdacodes.feca.feature_wishlist.data.mapper.toProduct
import com.jdacodes.feca.feature_wishlist.domain.model.Wishlist
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun WishlistScreen(
    navigator: DestinationsNavigator,
    viewModel: WishlistViewModel = hiltViewModel(),
) {
    val wishlistItems = viewModel.wishlistItems.observeAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackBarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }

                else -> {}
            }
        }
    }

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                elevation = 1.dp,
                backgroundColor = MaterialTheme.colorScheme.primary,
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 24.dp),
                        text = "Wishlist",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.deleteAllWishlist()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {
        WishListScreenContent(
            wishlistItems = wishlistItems,
            onClickOneWishItem = { wishlist ->
                navigator.navigate(SingleProductScreenDestination(wishlist.toProduct()))
            },
            onClickWishIcon = { wishlist ->
                viewModel.deleteFromWishlist(wishlist)
            }
        )
    }


}

@Composable
private fun WishListScreenContent(
    wishlistItems: State<List<WishlistEntity>>,
    onClickOneWishItem: (Wishlist) -> Unit,
    onClickWishIcon: (Wishlist) -> Unit,
) {

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(wishlistItems.value) { wishlist ->
                WishlistItem(
                    wishlist = wishlist.toDomain(), modifier = Modifier
                        .fillMaxWidth()
                        .height(135.dp)
                        .padding(8.dp),
                    onClickOneWishItem = onClickOneWishItem,
                    onClickWishIcon = onClickWishIcon
                )
            }
        }

        if ((wishlistItems.value.isEmpty() || wishlistItems.value.isEmpty())) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(220.dp),
                    painter = painterResource(id = R.drawable.ic_artwork),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WishlistItem(
    wishlist: Wishlist,
    modifier: Modifier,
    onClickOneWishItem: (Wishlist) -> Unit,
    onClickWishIcon: (Wishlist) -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colorScheme.surface,
        onClick = {
            onClickOneWishItem(wishlist)
        }
    ) {
        Row {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = wishlist.image)
                        .apply(block = fun ImageRequest.Builder.() {
                            placeholder(R.drawable.ic_placeholder)
                            crossfade(true)
                        }).build()
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
                    .fillMaxHeight(),
                contentScale = ContentScale.Inside
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(5.dp)
            ) {
                Text(
                    text = wishlist.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "$${wishlist.price}",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 22.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Light
                )
                IconButton(
                    onClick = {
                        onClickWishIcon(wishlist)
                    },
                    modifier = Modifier.align(Alignment.End),
                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_heart_fill),
//                        tint = YellowMain,
//                        contentDescription = null,
//                    )
                    Icon(
                        Icons.Filled.Favorite,
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            }
        }
    }
}
