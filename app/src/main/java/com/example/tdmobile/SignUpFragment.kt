package com.example.tdmobile

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tdmobile.MainActivity
import com.example.tdmobile.SignInActivity
import com.example.tdmobile.databinding.ActivitySignUpBinding
import com.example.tdmobile.retrofit.Endpoint
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Integer.parseInt
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignUpFragment : Fragment() {
    private var mEtEmail: EditText? = null
    private var mEtPassword: EditText? = null
    private var mEtRepeatPassword: EditText? = null
    private var mTvSignUp: TextView? = null
    private var mAuth: FirebaseAuth? = null
    private var mLlSignIn: Button? = null
    private var mTvWhat: TextView? = null
    private var mRlFadingLayout: RelativeLayout? = null
    private var mProgressBar: ProgressBar? = null
    private var _binding:   ActivitySignUpBinding? = null
    private val binding get() = _binding!!


    public override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

            super.onCreate(savedInstanceState)
            _binding = ActivitySignUpBinding.inflate(layoutInflater)

            mAuth = FirebaseAuth.getInstance()
            mEtEmail = binding.etEmailSignup
            mEtPassword = binding.etPassSignup
            mEtRepeatPassword = binding.etPassRepeatSignup
            mTvSignUp = binding.tvSignupSignup

            mRlFadingLayout = binding.progressSingup.fading
            mProgressBar = binding.progressSingup.progress

            mProgressBar!!.setVisibility(View.INVISIBLE)
            mRlFadingLayout!!.setVisibility(View.INVISIBLE)


            binding.buttonGoToSignIn.setOnClickListener {
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment());
            }
            mTvSignUp!!.setOnClickListener {
                mProgressBar!!.setVisibility(View.VISIBLE)
                mRlFadingLayout!!.setVisibility(View.VISIBLE)
                val email = mEtEmail!!.getText().toString()
                val password = mEtPassword!!.getText().toString()
                val repeatPassword = mEtRepeatPassword!!.getText().toString()
                val firstname = binding.firstName!!.getText().toString()
                val lastname = binding.lastName!!.getText().toString()
                val num = parseInt(binding.numero!!.getText().toString())

                if (!isValidEmail(email)) {
                    //Email is invalid
                    Toast.makeText(context, "Wrong Email format!", Toast.LENGTH_SHORT)
                        .show()
                } else if (password.length < 6) {
                    //Password length short
                    Toast.makeText(
                        context,
                        "Password must be 6 character long",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (password != repeatPassword) {
                    //Password not matched
                    Toast.makeText(context, "Password not matched", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    //Perform signup
                        /*
                    mAuth!!.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            mProgressBar!!.setVisibility(View.INVISIBLE)
                            mRlFadingLayout!!.setVisibility(View.INVISIBLE)
                            //Sign up completed
                            if (task.isSuccessful) {
                                //Signup successful
                                //startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                                //finish()
                            } else {
                                //Sign not successful
                                //Something went wrong
                                AlertDialog.Builder(context)
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

                         */
                    CoroutineScope(Dispatchers.IO).launch {
                        val response = Endpoint.createInstance().signUp(firstname,lastname,password,email,num)
                        withContext(Dispatchers.Main) {
                            mProgressBar!!.setVisibility(View.INVISIBLE)
                            mRlFadingLayout!!.setVisibility(View.INVISIBLE)
                            if (response.isSuccessful && response.body() != null) {
                                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment());
                                //startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                                //finish()
                            } else {
                                Snackbar
                                    .make(binding.root!!, response.message(), Snackbar.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                }
            }
        return binding.root
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private fun isValidEmail(email: String): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

}
