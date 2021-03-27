package com.example.werewolfofthemillershollow.fragments

import android.os.Bundle
import android.text.TextUtils
import android.text.TextUtils.replace
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.werewolfofthemillershollow.R

abstract class BaseFragment(layout : Int): Fragment(layout) {

    abstract fun onBackPressed(): Boolean

    abstract fun onCreated(view: View, savedInstanceState: Bundle?)

    abstract fun tag(): String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreated(view, savedInstanceState)
    }

    fun pushFragment(
        fragment : BaseFragment,
    ){
        parentFragmentManager.commit {

            setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.fade_out
            )

            replace(R.id.activity_fragment_container, fragment)
            addToBackStack(fragment.tag())
        }
    }

}