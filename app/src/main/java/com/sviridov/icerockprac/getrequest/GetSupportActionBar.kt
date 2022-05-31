package com.sviridov.icerockprac.getrequest

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

val Fragment.supportActionBar : ActionBar?
    get() {
        return try {
            (requireActivity() as AppCompatActivity).supportActionBar
        } catch (ex: Exception) {
            null
        }
    }