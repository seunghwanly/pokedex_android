package io.seunghwanly.ui

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

@Composable
fun PokemonDialog(
    onDismiss: () -> Unit,
    onSave: (Int) -> Unit,
    id: Int,
    imageUrl: String,
    name: String,
    height: Int,
    weight: Int,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
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


    Dialog(
        onDismissRequest = {
            onDismiss()
        },
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .aspectRatio(0.7f)
                .background(
                    brush = gradientBrush,
                    shape = RoundedCornerShape(24.dp),
                )
                .clip(RoundedCornerShape(24.dp))
                .border(2.dp, Color.White, RoundedCornerShape(24.dp)),
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 8.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .drawWithContent {
                                drawContent()
                                drawRect(
                                    brush = gradientBrush,
                                    blendMode = BlendMode.Multiply,
                                )
                            }
                            .border(2.dp, Color.White, RoundedCornerShape(12.dp))
                    ) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "$name image",
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    ) {
                        Text(
                            text = "${height}cm", modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "${weight}kg", modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            onSave(id)
                        },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("❤️")
                    }
                }
            }
//            Card(
//                shape = RoundedCornerShape(20.dp),
//                modifier = Modifier
//                    .padding(8.dp)
//                    .fillMaxSize(),
//                colors = CardDefaults.cardColors(containerColor = Color.White),
//            ) {
//
//            }
        }

    }
}