package com.example.tdmobile

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tdmobile.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Matcher
import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity() {
    private var mEtEmail: EditText? = null
    private var mEtPassword: EditText? = null
    private var mEtRepeatPassword: EditText? = null
    private var mTvSignUp: TextView? = null
    private var mAuth: FirebaseAuth? = null
    private var mLlSignIn: Button? = null
    private var mTvWhat: TextView? = null
    private var mRlFadingLayout: RelativeLayout? = null
    private var mProgressBar: ProgressBar? = null
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        mEtEmail = binding.etEmailSignup
        mEtPassword = binding.etPassSignup
        mEtRepeatPassword = binding.etPassRepeatSignup
        mTvSignUp = binding.tvSignupSignup

        mRlFadingLayout = binding.progressSingup.fading
        mProgressBar = binding.progressSingup.progress

        mProgressBar!!.setVisibility(View.INVISIBLE)
        mRlFadingLayout!!.setVisibility(View.INVISIBLE)


        mLlSignIn!!.setText("Sign in")
        mLlSignIn!!.setOnClickListener {

                startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
                Log.d("tag", "go from sign up to sign in")
                finish()

        }
        mTvSignUp!!.setOnClickListener{
                mProgressBar!!.setVisibility(View.VISIBLE)
                mRlFadingLayout!!.setVisibility(View.VISIBLE)
                val email = mEtEmail!!.getText().toString()
                val password = mEtPassword!!.getText().toString()
                val repeatPassword = mEtRepeatPassword!!.getText().toString()
                if (!isValidEmail(email)) {
                    //Email is invalid
                    Toast.makeText(this@SignUpActivity, "Wrong Email format!", Toast.LENGTH_SHORT)
                        .show()
                } else if (password.length < 6) {
                    //Password length short
                    Toast.makeText(
                        this@SignUpActivity,
                        "Password must be 6 character long",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (password != repeatPassword) {
                    //Password not matched
                    Toast.makeText(this@SignUpActivity, "Password not matched", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    //Perform signup
                    mAuth!!.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            mProgressBar!!.setVisibility(View.INVISIBLE)
                            mRlFadingLayout!!.setVisibility(View.INVISIBLE)
                            //Sign up completed
                            if (task.isSuccessful) {
                                //Signup successful
                                startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                                finish()
                            } else {
                                //Sign not successful
                                //Something went wrong
                                AlertDialog.Builder(this@SignUpActivity)
                                    .setTitle("Sorry")
                                    .setMessage(
                                        "Something went wrong\n\nError Message: " + task.exception!!
                                            .getLocalizedMessage()
                                    )
                                    .setPositiveButton("Okay",
                                        DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
                                    .create()
                                    .show()
                            }
                        }
                }
            }

    }

    private fun isValidEmail(email: String): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }
}
