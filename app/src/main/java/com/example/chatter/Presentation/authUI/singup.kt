package com.example.chatter.Presentation.authUI

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatter.DomainLayer.util.Results
import com.example.chatter.Navigation.Routes
import com.example.chatter.Presentation.Components.CustomTextField
import com.example.chatter.R


@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        when(val currentState = authState){
            is Results.Failure ->{
                isLoading = false
                Toast.makeText(context,currentState.message, Toast.LENGTH_LONG).show()
            }
            Results.Idle -> isLoading = false
            Results.Loading -> isLoading = true
            is Results.Success ->{
                isLoading=false
                Toast.makeText(context,"Account created successfully",Toast.LENGTH_SHORT).show()
                navController.navigate(Routes.Login){
                    popUpTo(Routes.SignUp){inclusive =true}
                }
                viewModel.resetAuthState()
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()){
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "logo",
                modifier = Modifier
                    .size(150.dp)
            )
            Spacer(Modifier.padding(12.dp))
            OutlinedTextField(
                value =email,
                onValueChange ={email = it},
                label = {Text("Please Enter your Email")},
                placeholder = {Text("Email")},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = "Email Icon"
                    )},
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)
            )
            Spacer(Modifier.padding(12.dp))
            CustomTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                icon = Icons.Default.Lock,
                placeholder = "Password",
                isPassword = true,
                passwordVisible = passwordVisible,
                onVisibilityChange = { passwordVisible = !passwordVisible }
            )
            Spacer(Modifier.padding(12.dp))
            CustomTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "ConfirmPassword",
                icon = Icons.Default.Lock,
                placeholder = "ConfirmPassword",
                isPassword = true,
                passwordVisible = passwordVisible,
                onVisibilityChange = { passwordVisible = !passwordVisible }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(10.dp))
                    .height(45.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(Color(0xFFA4C639))
                    .clickable{
                        if(password != confirmPassword){
                            Toast.makeText(context,"Passwords do not match",Toast.LENGTH_SHORT).show()
                        }
                        else if(email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()){
                            viewModel.signUp(email,password)
                        }
                        else Toast.makeText(context,"All fields required",Toast.LENGTH_SHORT).show()
                    }
            ){
                if(isLoading){
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                } else{
                    Text(
                        text = "Create an Account",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.align(Alignment.Center),
                        letterSpacing = 0.5.sp
                    )
                }
            }
//            Button(
//                onClick = {},
//                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA4C639)),
//                shape = RoundedCornerShape(10.dp)
//            ) {
//                Text("SignUp",
//                    fontSize = 16.sp)
//            }
            Spacer(Modifier.padding(6.dp))
            TextButton(
                onClick = {
                    navController.navigate(Routes.Login){
                        popUpTo(Routes.SignUp){inclusive = true}
                    }
                }
            ) {
                Text(text = "Already have an account? Login ",
                    color = Color(0xFFA4C639))
            }

        }



    }
}


//@Preview(showBackground = true)
//@Composable
//fun PrevSignUp(){
//    SignUpScreen()
//}