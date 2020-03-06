package com.university.gami_android.ui.signUp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.university.gami_android.R
import com.university.gami_android.model.User
import com.university.gami_android.ui.hobby_selection.HobbySelectionActivity
import com.university.gami_android.util.EditTextWatcher
import java.text.SimpleDateFormat
import java.util.*


class SignUpActivity : AppCompatActivity(), SignUpContract.View, View.OnClickListener {
    private lateinit var birthday: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var userName: EditText

    private lateinit var signUp: Button
    private lateinit var back: TextView

    private lateinit var passwordLayout: TextInputLayout
    private lateinit var confirmPasswordLayout: TextInputLayout
    private lateinit var emailLayout: TextInputLayout

    private lateinit var signUpData: SignUpDao

    private lateinit var presenter: SignUpPresenter

    private val textWatcher = object : EditTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            validateInputs()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        presenter = SignUpPresenter()
        presenter.bindView(this)

        signUp = findViewById(R.id.signUp_btn)

        userName = findViewById(R.id.username_text)
        userName.addTextChangedListener(textWatcher)

        firstName = findViewById(R.id.firstName_text)
        firstName.addTextChangedListener(textWatcher)

        lastName = findViewById(R.id.lastName_text)
        lastName.addTextChangedListener(textWatcher)

        birthday = findViewById(R.id.birthday_text)
        birthday.addTextChangedListener(textWatcher)

        email = findViewById(R.id.email_text)
        email.addTextChangedListener(textWatcher)

        password = findViewById(R.id.password_text)
        password.addTextChangedListener(textWatcher)

        confirmPassword = findViewById(R.id.confirm_password_text)
        confirmPassword.addTextChangedListener(textWatcher)

        confirmPasswordLayout = findViewById(R.id.confirm_password)
        passwordLayout = findViewById(R.id.password)
        emailLayout = findViewById(R.id.email)

        back = findViewById(R.id.back_btn)

        validateInputs()

        back.setOnClickListener(this)
        birthday.setOnClickListener(this)
        signUp.setOnClickListener(this)
    }

    @SuppressLint("SimpleDateFormat", "PrivateResource")
    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.back_btn -> {
                finish()
                overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_slide_out_bottom)
            }

            R.id.birthday_text -> {
                val calendar = Calendar.getInstance()
                val formatter = SimpleDateFormat(resources.getString(R.string.date_format))

                val datePickerDialog =
                    DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        val date: Calendar = Calendar.getInstance()
                        date.apply {
                            set(Calendar.YEAR, year)
                            set(Calendar.MONTH, monthOfYear)
                            set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        }

                        birthday.setText(formatter.format(date.time))

                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

                datePickerDialog.show()
            }

            R.id.signUp_btn -> {
                if (hasErrorForEmail() &&
                    hasErrorForPassword() &&
                    hasErrorForConfirmPassword()) {

                    presenter.doSignUp(
                        User(
                        signUpData.userName,
                        signUpData.password,
                        signUpData.firstName,
                        signUpData.lastName,
                        " ",
                        signUpData.email,
                        signUpData.birthday), appContext())
                }
            }
        }
    }

    override fun navigateToHobbySelectionActivity(context: Context) {
        finish()
        startActivity(Intent(context, HobbySelectionActivity::class.java))
    }

    fun validateInputs() {
        signUpData = SignUpDao(
            firstName.text.toString(),
            lastName.text.toString(),
            email.text.toString(),
            birthday.text.toString(),
            userName.text.toString(),
            password.text.toString(),
            confirmPassword.text.toString()
        )

        signUp.isEnabled = !presenter.fieldsValidation(signUpData)
    }

    override fun hasErrorForPassword() : Boolean {
        return if (!presenter.validatePassword(signUpData.password)) {
            passwordLayout.error = resources.getString(R.string.password_error)
            false
        } else {
            passwordLayout.error = null
            true
        }
    }

    override fun hasErrorForConfirmPassword() : Boolean {
        return if (!presenter.validateConfirmPassword(signUpData.password, signUpData.confirmPassword)) {
            confirmPasswordLayout.error = resources.getString(R.string.confirm_password_error)
            false
        } else {
            confirmPasswordLayout.error = null
            true
        }
    }

    override fun hasErrorForEmail() : Boolean {
        return if (!presenter.validateEmail(signUpData.email)) {
            emailLayout.error = resources.getString(R.string.email_error)
            false
        } else {
            emailLayout.error = null
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    override fun appContext(): Context = applicationContext
}
