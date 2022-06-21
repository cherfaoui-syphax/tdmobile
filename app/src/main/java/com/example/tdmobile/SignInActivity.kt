package com.example.tdmobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.example.tdmobile.databinding.ActivitySignInBinding
import com.example.tdmobile.retrofit.Endpoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SignInActivity : AppCompatActivity() {
    private var mBtnSignIn: TextView? = null
    private var mEtEmail: EditText? = null
    private var mEtPassword: EditText? = null
    private var root: RelativeLayout? = null
    private var mRlSignUp: Button? = null
    private var mRlFadingLayout: RelativeLayout? = null
    private var mProgressBar: ProgressBar? = null
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)

        setContentView(binding.root)





        mBtnSignIn = binding.tvLoginLogin
        mEtPassword = binding.etPassLogin
        mEtEmail = binding.etEmailLogin
        root = binding.root
        mRlFadingLayout = binding.progressLogin.fading
        mProgressBar = binding.progressLogin.progress

        mProgressBar!!.setVisibility(View.INVISIBLE)
        mRlFadingLayout!!.setVisibility(View.INVISIBLE)

        mRlSignUp!!.setText("Sign up")
        mRlSignUp!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
            Log.d("tag", "go from sign in to sign up")
            finish()
        })
        mBtnSignIn!!.setOnClickListener(View.OnClickListener {
            mProgressBar!!.setVisibility(View.VISIBLE)
            mRlFadingLayout!!.setVisibility(View.VISIBLE)
            val email = mEtEmail!!.getText().toString()
            val password = mEtPassword!!.getText().toString()
            val map = mapOf<String , String>( "email" to email , "password" to password);

            /*
            val firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                    finish()
                } else {
                    Snackbar
                        .make(root!!, task.exception!!.localizedMessage, Snackbar.LENGTH_LONG)
                        .show()
                }
            }*/
            CoroutineScope(Dispatchers.IO).launch {
                val response = Endpoint.createInstance().signIn(map)
                withContext(Dispatchers.Main) {
                    mProgressBar!!.setVisibility(View.INVISIBLE)
                    mRlFadingLayout!!.setVisibility(View.INVISIBLE)
                    if (response.isSuccessful && response.body() != null) {
                        val data = response.body()!!
                        Log.d("TAG",data.message);
                        Log.d("TAG",data.userId);
                        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Snackbar
                            .make(root!!, response.message(), Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }
        })
    }
}