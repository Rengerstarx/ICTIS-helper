package com.example.ictis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.ictis.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SignIn : AppCompatActivity() {
    lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //-----проверка вошел ли пользователь-------
        /*auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }*/
        //------------------------------------------
        //---------переход между активностями-------
        val registertext: TextView = findViewById(R.id.loginrnowbutton)
        registertext.setOnClickListener {
            val intent =  Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        //------------------------------------------
        //---------кнопка входа по логину и паролю-------
        val loginButton: TextView = findViewById(R.id.LogInButton)
        loginButton.setOnClickListener {
            LogInWithEmailPassword()
        }
        //------------------------------------------
        auth = Firebase.auth
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try{
                val account = task.getResult(ApiException::class.java)
                if(account!=null){
                    firebaseAuthWithGoogle(account.idToken!!)
                }
            }
            catch (e:ApiException){
                Log.d("MyLog", "ApiException!")
            }
        }
        binding.GoogleSignInButton.setOnClickListener{
            SignInGoogle()
        }
        checkautorization()
        findViewById<ImageButton>(R.id.guest).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    private fun LogInWithEmailPassword(){
        //вход по логину и паролю
        val email:EditText = findViewById(R.id.editTextTextPersonNameLogin)
        val password:EditText = findViewById(R.id.editTextTextPassword)
        //проверка введенных данных
        if(email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this, "Заполните оба поля!", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val passwordInput = password.text.toString()
        val loginInput = email.text.toString()
        auth.signInWithEmailAndPassword(loginInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // успешный вход, переход в main activity
                    Toast.makeText(baseContext, "Authentication Success.",
                        Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun getClient():GoogleSignInClient{
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(this, gso)
    }
    private fun SignInGoogle(){
        val signInClient = getClient()
        launcher.launch(signInClient.signInIntent)
    }
    private  fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if (it.isSuccessful){
                Log.d("MyLog", "Success!")
                    startActivity(Intent(this, SecondRegister::class.java))
            }
            else{
                Log.d("MyLog", "Error!")
            }
        }

    }

    private fun checkautorization(){
        if(auth.currentUser!=null){
            val myIntent = Intent(this@SignIn, MainActivity::class.java)
            this@SignIn.startActivity(myIntent)
        }
    }
}
