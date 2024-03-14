package com.example.europrofile.ui.authentication.custominput

import android.content.Context
import android.util.AttributeSet
import android.util.Patterns
import com.example.europrofile.R

class MailInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int=0
) : InputLayout(context, attrs, defStyleAttr) {
    override val errorMessageId: Int = R.string.invalid_email

    override fun innerIsValid(): Boolean {
        return if (text().contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(text()).matches()
        } else {
            text().isNotBlank()
        }
    }
}