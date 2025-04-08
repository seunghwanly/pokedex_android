package io.seunghwanly.ui

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult


@Composable
fun PokemonCard(
    onClick: () -> Unit,
    id: Int,
    imageUrl: String,
    name: String,
) {
    val context = LocalContext.current
    val backgroundColor1 = remember { mutableStateOf(Color.White) }
    val backgroundColor2 = remember { mutableStateOf(Color.White) }


    LaunchedEffect(imageUrl) {
        val request = ImageRequest.Builder(context).data(imageUrl).allowHardware(false).build()

        val result = (context.imageLoader.execute(request) as SuccessResult).drawable
        val bitmap = (result as BitmapDrawable).bitmap

        Palette.from(bitmap).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { color ->
                backgroundColor1.value = Color(color)
            }
            palette?.vibrantSwatch?.rgb?.let { color ->
                backgroundColor2.value = Color(color)
            }
        }
    }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            backgroundColor1.value,
            Color.White,
            backgroundColor2.value,
        )
    )

    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(200.dp)
            .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
            .drawWithContent {
                drawContent()
                drawRect(brush = gradientBrush, blendMode = BlendMode.DstIn)
            }
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradientBrush)
                .padding(16.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                modifier = Modifier
                    .weight(4f)
                    .fillMaxWidth(),
            )
            Text(
                text = name,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
            )
        }
    }
}