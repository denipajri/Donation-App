package com.application.donation_app.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.donation_app.R
import com.application.donation_app.data.database.Donate
import com.application.donation_app.data.viewmodel.DonatesViewModel
import com.application.donation_app.ui.theme.Pink40
import com.application.donation_app.views.utils.CustomButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDonateScreen(donatesViewModel: DonatesViewModel) {
    val context = LocalContext.current
    var donaturName by rememberSaveable { mutableStateOf("") }
    var tglDonasi by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }
    var isCategoryDropDownExpanded by remember { mutableStateOf(false) }
    var isAmountDropDownExpanded by remember { mutableStateOf(false) }
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


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Add Your Donation",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Pink40,
                modifier = Modifier
                    .padding(top = 60.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = donaturName,
                label = { Text(stringResource(id = R.string.donatur_name)) },
                onValueChange = {
                    donaturName = it
                },
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = tglDonasi,
                label = { Text(stringResource(id = R.string.donation_date)) },
                onValueChange = {
                    tglDonasi = it
                },
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        placeholder = { androidx.compose.material.Text(text = "Choose The Category") },
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
                            .fillMaxWidth(0.8f),
                    ) {
                        categoryList.forEachIndexed { index, selectedItem ->
                            DropdownMenuItem(onClick = {
                                category = selectedItem
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
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box {
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        placeholder = { androidx.compose.material.Text(text = "Choose The Amount") },
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
                            .fillMaxWidth(0.8f),
                    ) {
                        amountList.forEachIndexed { index, selectedItem ->
                            DropdownMenuItem(onClick = {
                                amount = selectedItem
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
            Spacer(modifier = Modifier.height(30.dp))
            CustomButton(stringResource(id = R.string.add_donation)) {
                // Create the donation object
                if (donaturName == "" || tglDonasi == "" || category == "" || amount == "") {
                    Toast.makeText(context, "Donation Added Failed", Toast.LENGTH_SHORT).show()
                    Log.d("data db", "Data Gagal")
                } else {
                    val donate = Donate(donaturName, tglDonasi, category, amount)
                    Log.d("data db", "Data Berhasil $donate")

                    // Update the donation to the database
                    donatesViewModel.addDonate(donate)
                    // Clear text fields
                    donaturName = ""
                    tglDonasi = ""
                    category = ""
                    amount = ""
                    Toast.makeText(context, "Donation added successfully", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }
}
