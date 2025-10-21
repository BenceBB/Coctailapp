package com.example.cocktails.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.cocktails.CocktailUiState
import com.example.cocktails.data.Cocktail
import kotlinx.coroutines.delay

@Composable
fun CocktailScreen(
    state: CocktailUiState,
    onCocktailSelected: (Cocktail) -> Unit,
    onDismissDetails: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (state.cocktails.isEmpty()) {
            Text(
                text = "Nem találhatók koktélok",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            val configuration = LocalConfiguration.current
            val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(
                    minSize = if (isLandscape) 180.dp else 160.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
            ) {
                items(state.cocktails, key = { it.id }) { cocktail ->
                    CocktailTile(cocktail = cocktail, onClick = { onCocktailSelected(cocktail) })
                }
            }
        }

        AnimatedVisibility(
            visible = state.selectedCocktail != null,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            state.selectedCocktail?.let { cocktail ->
                CocktailDetailsDialog(cocktail = cocktail, onDismiss = onDismissDetails)
            }
        }
    }
}

@Composable
private fun CocktailTile(cocktail: Cocktail, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(3f / 4f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            val imagePainter = painterResource(
                id = getCocktailDrawableId(cocktail.imageName)
            )
            Image(
                painter = imagePainter,
                contentDescription = cocktail.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = cocktail.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = cocktail.tagline,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun CocktailDetailsDialog(cocktail: Cocktail, onDismiss: () -> Unit) {
    LaunchedEffect(cocktail) {
        delay(20_000)
        onDismiss()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .padding(24.dp)
                .clip(RoundedCornerShape(24.dp))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { },
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = cocktail.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = cocktail.tagline,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "Hozzávalók",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    cocktail.ingredients.forEach { ingredient ->
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "•",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "${ingredient.amount} – ${ingredient.item}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                Text(
                    text = "Elkészítés",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = cocktail.preparation,
                    style = MaterialTheme.typography.bodyMedium
                )
                cocktail.garnish?.takeIf { it.isNotBlank() }?.let { garnish ->
                    Text(
                        text = "Díszítés",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = garnish,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

private fun getCocktailDrawableId(imageName: String): Int {
    return when (imageName) {
        "bison-on-the-beach" -> com.example.cocktails.R.drawable.bison_on_the_beach
        "lime-bison" -> com.example.cocktails.R.drawable.lime_bison
        "bison-mojito" -> com.example.cocktails.R.drawable.bison_mojito
        "bison-sour" -> com.example.cocktails.R.drawable.bison_sour
        "bison-negroni" -> com.example.cocktails.R.drawable.bison_negroni
        "aloe-bison" -> com.example.cocktails.R.drawable.aloe_bison
        "apple-bison" -> com.example.cocktails.R.drawable.apple_bison
        "beauty-and-the-beast" -> com.example.cocktails.R.drawable.beauty_and_the_beast
        "bison-from-the-orchard" -> com.example.cocktails.R.drawable.bison_from_the_orchard
        "milky-bison" -> com.example.cocktails.R.drawable.milky_bison
        "cafe-bison" -> com.example.cocktails.R.drawable.cafe_bison
        "cherry-bison" -> com.example.cocktails.R.drawable.cherry_bison
        "smoky-bison" -> com.example.cocktails.R.drawable.smoky_bison
        else -> com.example.cocktails.R.drawable.placeholder_cocktail
    }
}
