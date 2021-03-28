package com.yuriysurzhikov.lab4.ui.text

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

class LengthWatcher(val editText: TextInputLayout?) : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        if (start == 0 && !editText?.error.isNullOrEmpty()) {
            editText?.error = null
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}