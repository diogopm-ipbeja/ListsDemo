package pt.ipbeja.listsdemo.ui.utils

import android.widget.EditText

val Any.TAG : String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

val EditText.content : String
    get() = this.text?.toString() ?: ""