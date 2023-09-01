package com.jdacodes.feca.feature_auth.presentation.forgot_password

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdacodes.feca.core.presentation.theme.workSans
import com.ramcosta.composedestinations.annotation.Destination

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination
@Composable
fun ForgotPasswordScreen() {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = "Forgot Password",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = workSans,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Please enter an email address that you had registered with, so that we can send you a password reset link",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = workSans,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

    ) {
        ForgotPasswordContent(
            onClickForgotPassword = {
                Toast.makeText(
                    context,
                    "This API does not provide an endpoint for sending password reset link, just login with the credentials provided in the README file",
                    Toast.LENGTH_LONG
                ).show()
            }
        )

    }
}

@Composable
fun ForgotPasswordContent(onClickForgotPassword: () -> Unit) {
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        item {
            Spacer(modifier = Modifier.height(64.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {

                },
                label = {
                    Text(text = "Email",color = MaterialTheme.colorScheme.onSurface)
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = true,
                    keyboardType = KeyboardType.Email,
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.tertiary,


                    )
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onClickForgotPassword,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.tertiary,
                )
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp), text = "Continue", textAlign = TextAlign.Center
                )
            }
        }
    }

}
