package com.example.tdmobile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tdmobile.databinding.ActivitySignInBinding
import com.example.tdmobile.retrofit.Endpoint
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime


class SignInFragment :  Fragment() {
    private var mBtnSignIn: TextView? = null
    private var mEtEmail: EditText? = null
    private var mEtPassword: EditText? = null
    private var root: RelativeLayout? = null
    private var mRlSignUp: Button? = null
    private var mRlFadingLayout: RelativeLayout? = null
    private var mProgressBar: ProgressBar? = null
    private var _binding: ActivitySignInBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    @OptIn(ExperimentalTime::class)
    public override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivitySignInBinding.inflate(layoutInflater)
        var preferences: SharedPreferences? =
            this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)




        val con = preferences!!.getBoolean("connected", false)
        if(con)   findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToAccountDetail())







        mBtnSignIn = binding.tvLoginLogin
        mEtPassword = binding.etPassLogin
        mEtEmail = binding.etEmailLogin
        root = binding.root
        mRlFadingLayout = binding.progressLogin.fading
        mProgressBar = binding.progressLogin.progress

        mProgressBar!!.setVisibility(View.INVISIBLE)
        mRlFadingLayout!!.setVisibility(View.INVISIBLE)

        binding.tvSignupSignup2!!.setOnClickListener(View.OnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment());
            Log.d("tag", "go from sign in to sign up")
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
                        preferences!!.edit {
                            putBoolean("connected",true)
                            putString("email",email)
                            putString("usersId" , response.body()!!.userId)
                        }
                        findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToAccountDetail())

                        //startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                        //finish()
                    } else {
                        Snackbar
                            .make(root!!, response.message(), Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }
        })




        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}

