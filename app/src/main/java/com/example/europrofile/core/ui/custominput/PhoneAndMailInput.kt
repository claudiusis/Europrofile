package com.example.europrofile.core.ui.custominput

import android.content.Context
import android.util.AttributeSet
import android.util.Patterns
import com.example.europrofile.R

class PhoneAndMailInput @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet,
    defStyleAttr: Int = 0
    ): InputLayout(context, attr, defStyleAttr) {
    override val errorMessageId: Int = R.string.invalid_login

    override fun innerIsValid(): Boolean {
        return if (text().contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(text()).matches()
        } else if (text().isNotBlank()) {
            Patterns.PHONE.matcher(text().trim()).matches()
        } else false
    }
}