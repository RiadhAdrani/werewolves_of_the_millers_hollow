package com.example.werewolfofthemillershollow

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.werewolfofthemillershollow.fragments.DocsMainFragment
import com.example.werewolfofthemillershollow.settings.App

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