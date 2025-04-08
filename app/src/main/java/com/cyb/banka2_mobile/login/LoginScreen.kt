package com.cyb.banka2_mobile.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cyb.banka2_mobile.R
import com.cyb.banka2_mobile.ui.theme.EnableEdgeToEdge

fun NavGraphBuilder.login(
    route: String,
    onUserClick: () -> Unit
) = composable(
    route = route
) {
    val loginViewModel = hiltViewModel<LoginViewModel>()
    val state = loginViewModel.state.collectAsState()
    EnableEdgeToEdge()
    LoginScreen(
        state = state.value,
        eventPublisher = {
            loginViewModel.setEvent(it)
        },
        onUserClick = onUserClick
    )
}

@Composable
fun LoginScreen(
    state: LoginContract.LoginState,
    eventPublisher: (uiEvent: LoginContract.LoginEvent) -> Unit,
    onUserClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F1120))
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_splash),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Log in to BankToo",
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                    .background(Color(0xFF0F1120), RoundedCornerShape(12.dp))
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = state.emailValue,
                        onValueChange = { eventPublisher(LoginContract.LoginEvent.EmailFieldChange(it)) },
                        label = { Text("Email") },
                        placeholder = { Text("example@example.com") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.Gray,
                            cursorColor = Color.White
                        ),
                        textStyle = LocalTextStyle.current.copy(color = Color.White)
                    )

                    OutlinedTextField(
                        value = state.passwordValue,
                        onValueChange = { eventPublisher(LoginContract.LoginEvent.PasswordFieldChange(it)) },
                        label = { Text("Password") },
                        placeholder = { Text("********") },
                        singleLine = true,
                        visualTransformation = if (state.passwordFieldVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {
                                eventPublisher(LoginContract.LoginEvent.SwitchPasswordVisibility)
                            }) {
                                Image(
                                    painter = painterResource(
                                        id = R.drawable.password_not
                                    ),
                                    contentDescription = "Toggle password visibility",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Gray,
                            cursorColor = Color.White
                        ),
                        textStyle = LocalTextStyle.current.copy(color = Color.White)
                    )

                    if (state.raiseError) {
                        Text(
                            text = "Incorrect email or password. Please try again.",
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )

                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF4C5BD4),
                                        Color(0xFF9F50FF)
                                    )
                                )
                            )
                            .clickable { eventPublisher(LoginContract.LoginEvent.DoLogin) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Log in",
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
