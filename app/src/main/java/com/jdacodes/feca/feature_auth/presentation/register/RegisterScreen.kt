package com.jdacodes.feca.feature_auth.presentation.register

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdacodes.feca.core.presentation.theme.workSans
import com.jdacodes.feca.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination
@Composable
fun RegisterScreen(
    navigator: DestinationsNavigator
) {
    Scaffold(
        topBar = {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.Top) {
                Text(
                    text = "Getting Started",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = workSans,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Create an account to continue with your shopping",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = workSans,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    ) {
        RegisterScreenContent(
            onClickSignUp = {
                navigator.popBackStack()
                navigator.navigate(LoginScreenDestination)
            }
        )

    }

}

@Composable
fun RegisterScreenContent(
    onClickSignUp: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(64.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {

                },
                label = {
                    Text(text = "Name", color = MaterialTheme.colorScheme.onSurface)
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Email,
                ),
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.tertiary,
                )
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {

                },
                label = {
                    Text(text = "Email", color = MaterialTheme.colorScheme.onSurface)
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = true,
                    keyboardType = KeyboardType.Email,
                ),
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.tertiary,
                )
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {

                },
                label = {
                    Text(text = "Password", color = MaterialTheme.colorScheme.onSurface)
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = true,
                    keyboardType = KeyboardType.Password,
                ),
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.tertiary,
                )
            )
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))

            val context = LocalContext.current

            Button(
                onClick = {

                    Toast.makeText(
                        context,
                        "This API does not provide an endpoint for registering, just login with the credentials provided in the README file",
                        Toast.LENGTH_LONG
                    ).show()
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.tertiary,
                )
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp), text = "Sign Up", textAlign = TextAlign.Center
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))

            TextButton(
                onClick = onClickSignUp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            append("Already have an account?")
                        }
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.tertiary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Sign In")
                        }
                    },
                    fontFamily = workSans,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
