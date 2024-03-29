package com.university.gami_android.ui.login

import android.content.Context
import com.university.gami_android.model.LoginDao
import com.university.gami_android.ui.base.BaseContract

interface LoginContract {

    interface View : BaseContract.View {
        fun navigateToMainActivity(context: Context)
        fun navigateToSignUpActivity(context: Context)
        fun navigateToForgotPasswordActivity(context: Context)
        fun changeProgressaBarVisibility(value: Boolean)
    }

    interface Presenter {
        fun validateInput(loginDao: LoginDao): Boolean
        fun doLogin(loginDao: LoginDao, context: Context)
    }
}