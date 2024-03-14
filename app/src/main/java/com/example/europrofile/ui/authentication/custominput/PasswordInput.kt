package com.example.europrofile.ui.authentication.custominput

import android.content.Context
import android.util.AttributeSet
import com.example.europrofile.R

class PasswordInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : InputLayout(context, attrs, defStyleAttr) {
    override val errorMessageId: Int = R.string.invalid_password

    override fun innerIsValid(): Boolean {
        return text().matches(Regex(PASSWORD_PATTERN))
    }

    companion object {
        private const val PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$"
    }
}