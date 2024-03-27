package com.application.donation_app.views

import HandleBackPress
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.application.donation_app.data.database.Donate
import com.application.donation_app.data.viewmodel.DonatesViewModel
import com.application.donation_app.navigation.Screen
import com.application.donation_app.views.utils.TitleText
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import com.application.donation_app.R
import com.application.donation_app.ui.theme.Pink40


@Composable
fun AllDonatesScreen(navController: NavController, donatesViewModel: DonatesViewModel) {
    val donates: List<Donate> by donatesViewModel.allDonates.observeAsState(initial = listOf())
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    LaunchedEffect(Unit) {
        donatesViewModel.getDonates()
    }

    LaunchedEffect(Unit) {
        donatesViewModel.getDonates()
    }

    Box(
        modifier = Modifier
            .background(color = Pink40)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .background(color = Pink40)
        ) {
            if (donates.isEmpty()) {
                TitleText(text = stringResource(id = R.string.all_donations_title), modifier = Modifier,)
                Column(
                    modifier = Modifier.offset(y = -(35).dp)
                ) {
                    EmptyContent()
                }
            } else {
                TitleText(text = stringResource(id = R.string.all_donations_title), modifier = Modifier,)
                LazyColumn(modifier = Modifier
                    .weight(1f)
                    .background(color = Pink40), verticalArrangement = Arrangement.spacedBy(15.dp)) {
                    items(donates) { donate ->
                        OutlinedCard(
                            Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clickable {
                                    navController.navigate(route = Screen.DonateDetailsScreen.route + "/" + donate.id)
                                },
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(10.dp)
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = donate.donaturName,
                                        modifier = Modifier
                                            .padding(20.dp, 8.dp, 0.dp, 0.dp),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 30.sp
                                    )
                                    Text(
                                        text = "Category - ${donate.category}",
                                        modifier = Modifier
                                            .padding(20.dp, 8.dp, 0.dp, 5.dp),
                                        fontStyle = FontStyle.Italic,
                                        fontSize = 14.sp
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 16.dp),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Text(
                                        text = "${donate.tglDonasi}",
                                        modifier = Modifier
                                            .padding(0.dp, 8.dp, 10.dp, 0.dp),
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.End
                                    )
                                    Text(
                                        text = "${donate.amount}",
                                        modifier = Modifier.padding(0.dp, 16.dp, 10.dp, 0.dp),
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }



    if (backDispatcher != null) {
        HandleBackPress(backDispatcher) {
            navController.navigate(Screen.HomeScreen.route)
        }
    }
}

@Composable
fun EmptyContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Pink40),
    ) {
        Icon(
            modifier = Modifier.size(120.dp),
            painter = painterResource(
                R.drawable.money
            ), contentDescription = stringResource(
                R.string.no_donation
            ),
            tint = Color.LightGray
        )
        androidx.compose.material.Text(
            text = stringResource(
                R.string.text_empty_content
            ),
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize,
            color = Color.LightGray
        )
    }
}
