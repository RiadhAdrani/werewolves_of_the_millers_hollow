package com.example.werewolfofthemillershollow

import android.os.Bundle
import com.example.werewolfofthemillershollow.fragments.BaseFragment
import com.example.werewolfofthemillershollow.fragments.DocsMainFragment
import com.example.werewolfofthemillershollow.util.App

class DocumentationActivity : App()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_documentation)



        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_fragment_container,DocsMainFragment())
            .addToBackStack(null)
            .commit()

    }

    override fun onBackPressed() {

        val fragment = supportFragmentManager.findFragmentById(R.id.activity_fragment_container) as BaseFragment

        if (fragment.onBackPressed())
            super.onBackPressed()
        else{
            finish()
        }

    }
}