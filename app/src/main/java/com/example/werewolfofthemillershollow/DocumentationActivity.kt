package com.example.werewolfofthemillershollow

import android.os.Bundle
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
}