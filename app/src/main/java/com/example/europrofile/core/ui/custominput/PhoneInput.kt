package com.example.europrofile.core.ui.custominput

import android.content.Context
import android.util.AttributeSet
import android.util.Patterns
import com.example.europrofile.R

class PhoneInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    attrsStyleType: Int = 0
) : InputLayout(context, attrs, attrsStyleType) {
    override val errorMessageId: Int = R.string.invalid_phone

    override fun innerIsValid(): Boolean {
        return if(text().isNotBlank()){
            Patterns.PHONE.matcher(text().trim()).matches()
        } else false
    }
}