package com.example.werewolfofthemillershollow.fragments

import android.os.Bundle
import android.view.View
import com.example.werewolfofthemillershollow.R

class DocsAboutFragment : BaseFragment(R.layout.fragment_docs_about) {

    override fun onBackPressed(): Boolean = true

    override fun onCreated(view: View, savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun tag(): String = "DOCS_ABOUT"

}