package com.example.werewolfofthemillershollow.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role

class DocsRoleFragment(val role: Role) : BaseFragment(R.layout.fragment_docs_role) {

    override fun onBackPressed(): Boolean = true

    override fun onCreated(view: View, savedInstanceState: Bundle?) {
        val icon : ImageView = view.findViewById(R.id.fragment_icon)
        val name : TextView = view.findViewById(R.id.fragment_title)
        val description : TextView = view.findViewById(R.id.fragment_description)

        icon.setImageResource(role.icon)
        name.text = role.name
        description.text = role.description
    }

    override fun tag(): String = "DOCS_ROLE"
}