package com.example.studyapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.studyapp.database.AppDatabase
import com.example.studyapp.database.entity.UserEntity
import com.example.studyapp.databinding.LoginMainBinding
import com.example.studyapp.fragment.DangKiFragment
import com.example.studyapp.fragment.ForgotPassword
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import java.util.concurrent.TimeUnit

/*
* get sha-1
* cmd: keytool -list -v -keystore C:\Users\DELL\.android\debug.keystore -alias androiddebugkey -storepass android -keypass android
* */

class LoginActivity : AppCompatActivity() {
    lateinit var binding: LoginMainBinding

    var storedVerificationId :String = ""


    val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initListener()
        checkRememberLogin()

    }

    private fun initListener() {
        binding.tvDangNhap.setOnClickListener {
            login(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
//            val phone = binding.etPhone.text.toString()
//            val phoneNational = phone.replaceFirst("0","+84")
//
//            loginWithPhoneNumber(phoneNational)
        }
        binding.tvDangKy.setOnClickListener {
            val dangKiFragment = DangKiFragment()
            dangKiFragment.show(supportFragmentManager, "DangKiFragment")
        }

        binding.tvRememberPassword.setOnClickListener {
            val dialog = ForgotPassword()
            dialog.show(supportFragmentManager, "ForgotPassword")
        }

        binding.tvEmail.setOnClickListener {
            switchUILogin()
            switchUIOTP()
        }
        binding.tvPhoneNumber.setOnClickListener {
            switchUILogin()
            switchUIOTP()
        }

        binding.tvGuiOTP.setOnClickListener {
            val phone = binding.etPhone.text.toString()
            val phoneNational = phone.replaceFirst("0","+84")

            loginWithPhoneNumber(phoneNational)
        }
        binding.tvDangNhapBangOTP.setOnClickListener {
            val credential = PhoneAuthProvider.getCredential(storedVerificationId, binding.etOTP.text.toString())
            signInWithPhoneAuthCredential(credential)



        }
    }

    private fun login(email: String, password: String) {
        when {
            email.isEmpty() -> {
                Toast.makeText(this, "nhap email", Toast.LENGTH_SHORT).show()
            }
            password.isEmpty() -> {
                Toast.makeText(this, "nhap password", Toast.LENGTH_SHORT).show()
            }
            password.length < 6 -> {
                Toast.makeText(this, "password khong du ki tu", Toast.LENGTH_SHORT).show()

            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                Toast.makeText(this, "email khong hop le", Toast.LENGTH_SHORT).show()
            }

            else -> {
                handleTextUser()

            }
        }

    }

    private fun handleTextUser() {
        var listUser: List<UserEntity>
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val dao = db.userDao()
        val email = binding.etEmail.text.toString()
        val bundle = Bundle()

        val password = binding.etPassword.text.toString()

        listUser = dao.findUserWithName(email, password)

        if (listUser.isEmpty()) {
            Toast.makeText(this, "user khong ton tai", Toast.LENGTH_SHORT).show()
        } else {
            rememberSaveLogin()
//            saveEmailUser(listUser[0])
            saveIdUser(listUser[0])
            var bundle = Bundle()
            var gson = Gson()
            var data = gson.toJson(listUser[0])
            bundle.putString("Main", data)
            supportFragmentManager.setFragmentResult("Main", bundle)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


//            supportFragmentManager.beginTransaction().apply {
//                val homeFragment = HomeFragment()
//                add(R.id.flLogin, homeFragment)
//                commit()
//            }
        }


    }

    fun rememberSaveLogin() {
        val sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("Login", true)
        editor.apply()
    }

    fun checkRememberLogin() {
        val sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val value = sharedPreferences.getBoolean("Login", false)

        if (value == true) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


//            supportFragmentManager.beginTransaction().apply {
//                val homeFragment = HomeFragment()
//                add(R.id.flLogin, homeFragment)
//                commit()
//
//            }
        }
    }

    fun saveIdUser(idUser: UserEntity) {
        val sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val save = sharedPreferences.edit()
        save.putInt("idUser", idUser.id)
        save.apply()


    }
//    fun saveEmailUser(emailUserEntity: UserEntity) {
//        val sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
//        val save = sharedPreferences.edit()
//        save.putString("email",emailUserEntity.email)
//        save.apply()
//
//
//    }


    fun loginWithPhoneNumber(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
            }
            Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.

            // Save verification ID and resending token so we can use them later
            storedVerificationId = verificationId
//            resendToken = token

//            val credential = PhoneAuthProvider.getCredential(verificationId, "221111")
//            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "signInWithCredential:success")

                    val user = task.result?.user
                    Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show()

                   var userEntity = findUId(user?.uid)
                    if (userEntity == null){
                        saveData(user?.uid)
                        userEntity = findUId(user?.uid)
                    }

                    rememberSaveLogin()
                    userEntity?.let {

                        saveIdUser(it)
//                        var bundle = Bundle()
//                        var gson = Gson()
//                        var data = gson.toJson(listUser[0])
//                        bundle.putString("Main", data)
//                        supportFragmentManager.setFragmentResult("Main", bundle)

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }


//

                } else {
                    // Sign in failed, display a message and update the UI
//                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid

                        Toast.makeText(
                            this@LoginActivity,
                            task.exception.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    // Update UI
                }
            }
    }

    fun switchUILogin() {
        binding.llLoginEmail.visibility =
            if (binding.llLoginEmail.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        binding.llLoginPhoneNumber.visibility =
            if (binding.llLoginPhoneNumber.visibility == View.VISIBLE) View.GONE else View.VISIBLE

    }
    fun switchUIOTP(){
        binding.llBtEamil.visibility =
            if(binding.llBtEamil.visibility == View.VISIBLE) View.GONE else View.VISIBLE

        binding.llBtPhoneNumber.visibility =
            if (binding.llBtPhoneNumber.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

    private fun saveData(uid: String?=null) {
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val dao = db.userDao()
        val listUser = UserEntity(
           phone = binding.etPhone.text.toString(),
            uid = uid

        )
        dao.insertAll(listUser)


    }

    fun findUId(uid : String?):UserEntity?{
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val dao = db.userDao()

       return dao.findUserByUId(uid = uid)

    }
}