package com.example.tdmobile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tdmobile.databinding.ActivitySignInBinding
import com.example.tdmobile.retrofit.RetrofitService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime


class SignInFragment :  Fragment() {
    private var mBtnSignIn: TextView? = null
    private var mEtEmail: EditText? = null
    private var mEtPassword: EditText? = null
    private var root: ConstraintLayout? = null
    private var mRlSignUp: Button? = null
    private var mRlFadingLayout: RelativeLayout? = null
    private var mProgressBar: ProgressBar? = null
    private var _binding: ActivitySignInBinding? = null
    private var signInButton: SignInButton? = null
    private var googleSignInClient: GoogleSignInClient? = null
    private val TAG = "mainTag"
    private val RESULT_CODE_SINGIN = 999

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

        signInButton = binding.signInButton

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


            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitService.getInstance().signIn(map)
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


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("757938849657-2tkdop73k4ql3l30anajlmr4gcfrd64t.apps.googleusercontent.com")
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient((activity  as MainActivity), gso)

        //Attach a onClickListener

        //Attach a onClickListener
        signInButton!!.setOnClickListener { signInM() }




        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private fun signInM() {
        val singInIntent = googleSignInClient!!.signInIntent
        startActivityForResult(singInIntent, RESULT_CODE_SINGIN)
    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_CODE_SINGIN) {        //just to verify the code
            //create a Task object and use GoogleSignInAccount from Intent and write a separate method to handle singIn Result.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }


    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {

        //we use try catch block because of Exception.
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            if(account.id != null) {Log.d("Account id", account.id!!)}
            Toast.makeText((activity  as MainActivity), "Signed In successfully", Toast.LENGTH_LONG).show()
            //SignIn successful now show authentication
        } catch (e: ApiException) {
            e.printStackTrace()
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            Toast.makeText((activity  as MainActivity), "SignIn Failed!!!", Toast.LENGTH_LONG).show()
        }
    }



}

