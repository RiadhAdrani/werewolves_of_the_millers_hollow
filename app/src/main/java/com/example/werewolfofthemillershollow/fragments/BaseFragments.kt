package com.example.werewolfofthemillershollow.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragments(layout : Int): Fragment(layout) {

    abstract fun  onCreated(view: View, savedInstanceState: Bundle?)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreated(view, savedInstanceState)
    }
}