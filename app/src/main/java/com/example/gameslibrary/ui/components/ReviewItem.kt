package com.example.gameslibrary.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gameslibrary.R
import com.example.gameslibrary.data.models.ReviewModel
import com.example.gameslibrary.viewmodel.TopBarViewModel

@Composable
fun ReviewItem(review: ReviewModel, navController: NavController, topBarViewModel: TopBarViewModel = hiltViewModel(), isProfile: Boolean = false) {
    val gradientBrush = horizontalGradient()
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.searchBackground)
        ),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable {
                topBarViewModel.changeUid(review.uid)
                navController.navigate("profile1")
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(12.dp),
    ) {
        Column (
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isProfile) review.gameTitle else review.user,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.circle),
                        contentDescription = null,
                        modifier = Modifier
                            .height(24.dp)
                            .drawWithContent {
                                drawContent()
                                drawCircle(
                                    brush = gradientBrush,
                                    radius = size.minDimension / 2,
                                    center = Offset(
                                        size.width / 2,
                                        size.height / 2
                                    ),
                                    style = Stroke(width = size.minDimension / 2 * 0.02f)
                                )
                            }
                    )
                    Text(
                        text = review.rating.toString(),
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Text(
                text = review.comment,
                fontSize = 16.sp,
                color = Color.White,
            )
        }
    }
}