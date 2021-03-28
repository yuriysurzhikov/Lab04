package com.yuriysurzhikov.lab4.utils

import android.view.View

object ViewsUtil {

    @JvmStatic
    fun setGone(view: View?) {
        if (view?.visibility != View.GONE) {
            view?.visibility = View.GONE
        }
    }

    @JvmStatic
    fun setVisible(view: View?) {
        if (view?.visibility != View.VISIBLE) {
            view?.visibility = View.VISIBLE
        }
    }

    @JvmStatic
    fun setInvisible(view: View?) {
        if (view?.visibility != View.INVISIBLE) {
            view?.visibility = View.INVISIBLE
        }
    }
}