package com.jdacodes.feca.feature_product.presentation

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
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jdacodes.feca.R
import com.jdacodes.feca.destinations.SingleProductScreenDestination
import com.jdacodes.feca.feature_product.domain.model.Product
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
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            SearchField(
                viewModel = viewModel,
                leadingIcon = {
                    Icon(
                        Icons.Sharp.Search,
                        contentDescription = null,
                        tint = Color.White
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
                            product = product,
                            navigator = navigator,
                            modifier = modifier,

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
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(product, navigator, index)

    }
}

@Composable
fun CardContent(
    product: Product,
    navigator: DestinationsNavigator,
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
            .clickable { navigator.navigate(SingleProductScreenDestination(product = product)) }
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
@Destination
@Composable
fun SingleProductScreen(
    product: Product,
    modifier: Modifier = Modifier
) {
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
                if (product.id != null) {
                    Text(text = "Item: ${product.id}")
                }
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                AsyncImage(
                    model = product.image,
                    contentDescription = null,
                    modifier = Modifier
                        .heightIn(min = 20.dp, max = 280.dp)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Inside
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
}

