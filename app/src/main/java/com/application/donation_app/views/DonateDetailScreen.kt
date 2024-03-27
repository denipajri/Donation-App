package com.application.donation_app.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.application.donation_app.R
import com.application.donation_app.data.database.Donate
import com.application.donation_app.data.viewmodel.DonatesViewModel
import com.application.donation_app.navigation.Screen
import com.application.donation_app.views.utils.CustomUpdateButton
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonateDetailScreen(id: Int, donatesViewModel: DonatesViewModel, navController: NavController) {
    val context = LocalContext.current
    val donates = remember { mutableStateOf<Donate?>(null) }
    var donaturNameState: String? by remember { mutableStateOf(null) }
    var tglDonasiState: String? by remember { mutableStateOf(null) }
    var categoryState: String? by remember { mutableStateOf(null) }
    var amountState: String? by remember { mutableStateOf(null) }
    var isTglDonasiDropDownExpanded by remember { mutableStateOf(false) }
    var isCategoryDropDownExpanded by remember { mutableStateOf(false) }
    var isAmountDropDownExpanded by remember { mutableStateOf(false) }
    val tglDonasiList = listOf("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9","B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9","C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9")
    val categoryList = listOf("Education ", "Society", "Environmental", "Natural Disaster")
    val amountList = listOf(
        "Rp. 50,000 ",
        "Rp. 100,000",
        "Rp. 150,000",
        "Rp. 200,000",
        "Rp. 250,000",
        "Rp.300,000",
        "Rp. 350,000",
        "Rp. 400.000"
    )

    LaunchedEffect(Unit) {
        donatesViewModel.getDonate(id)
    }
    donatesViewModel.getDonate(id)

    val donate = donatesViewModel.getDonate.observeAsState().value
    donate ?: return
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = "Details Donation",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 60.dp),
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                OutlinedTextField(
                    value = donaturNameState
                        ?: donate.donaturName,  // display database text if no modified text available
                    onValueChange = { donaturNameState = it },
                    label = { Text(stringResource(id = R.string.donatur_name)) },
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = tglDonasiState
                        ?: donate.tglDonasi,
                    onValueChange = { tglDonasiState = it },
                    label = { Text(stringResource(id = R.string.donation_date)) },
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Box {
                    OutlinedTextField(
                        value = categoryState ?: donate.category,
                        onValueChange = { categoryState = it },
                        placeholder = { androidx.compose.material.Text(text = donate.category) },
                        enabled = false,
                        modifier = Modifier
                            .clickable {
                                isCategoryDropDownExpanded = true
                            }
                            .fillMaxWidth(0.8f),
                        textStyle = TextStyle(color = Color.Black),
                        trailingIcon = { Icon(imageVector = Icons.Default.ArrowCircleDown, "") }
                    )

                    DropdownMenu(
                        expanded = isCategoryDropDownExpanded,
                        onDismissRequest = { isCategoryDropDownExpanded = false },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                    ) {
                        categoryList.forEachIndexed { index, selectedItem ->
                            DropdownMenuItem(onClick = {
                                categoryState = selectedItem
                                isCategoryDropDownExpanded = false
                            }) {
                                androidx.compose.material.Text(selectedItem)
                            }
                            if (index != categoryList.lastIndex)
                                Divider(Modifier.background(Color.Black))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box {
                    OutlinedTextField(
                        value = amountState ?: donate.amount,
                        onValueChange = { amountState = it },
                        placeholder = { androidx.compose.material.Text(text = donate.amount) },
                        enabled = false,
                        modifier = Modifier
                            .clickable {
                                isAmountDropDownExpanded = true
                            }
                            .fillMaxWidth(0.8f),
                        textStyle = TextStyle(color = Color.Black),
                        trailingIcon = { Icon(imageVector = Icons.Default.ArrowCircleDown, "") }
                    )

                    DropdownMenu(
                        expanded = isAmountDropDownExpanded,
                        onDismissRequest = { isAmountDropDownExpanded = false },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                    ) {
                        amountList.forEachIndexed { index, selectedItem ->
                            DropdownMenuItem(onClick = {
                                amountState = selectedItem
                                isAmountDropDownExpanded = false
                            }) {
                                androidx.compose.material.Text(selectedItem)
                            }
                            if (index != amountList.lastIndex)
                                Divider(Modifier.background(Color.Black))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                CustomUpdateButton(stringResource(id = R.string.update_donation))
                {
                    // Create the donate object
                    val donate = Donate(
                        donaturName = donaturNameState ?: donate.donaturName,
                        tglDonasi = tglDonasiState ?: donate.tglDonasi,
                        category = categoryState ?: donate.category,
                        amount = amountState ?: donate.amount
                    )

                    // Update the donate in the database
                    donatesViewModel.updateDonate(
                        id,
                        donate.donaturName,
                        donate.tglDonasi,
                        donate.category,
                        donate.amount
                    )
                    Toast.makeText(context, "Donation updated successfully", Toast.LENGTH_SHORT)
                        .show()

                }
                Column(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    val openDialog = remember { mutableStateOf(false) }

                    Button(onClick = { openDialog.value = true }) {
                        Text(text = "Delete Donation")
                    }

                    if (openDialog.value) {
                        AlertDialog(
                            onDismissRequest = { openDialog.value = false },
                            title = {
                                Text(text = "Deleting Donation")
                            },
                            text = {
                                Text(text = "Do you really want to Delete this Donation ?")
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        donate?.let { id ->
                                            donatesViewModel.deleteDonate(id)
                                        }
                                        openDialog.value = false
                                        Toast.makeText(context, "Donation Deleted successfully", Toast.LENGTH_SHORT)
                                            .show()
                                        navController.navigate(Screen.AllDonatesScreen.route)
                                    },
                                ) {
                                    Text(text = "CONFIRM")

                                }
                            },
                            dismissButton = {
                                Button(onClick = { openDialog.value = false }
                                ) {
                                    Text(text = "CANCEL")
                                }
                            }
                        )
                    }
                }

            }

        }

    }
}



