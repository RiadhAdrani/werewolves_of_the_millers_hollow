package com.example.werewolfofthemillershollow.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginTop
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.util.Ability

class DocsRoleFragment(val role: Role) : BaseFragment(R.layout.fragment_docs_role) {

    override fun onBackPressed(): Boolean = true

    override fun onCreated(view: View, savedInstanceState: Bundle?) {
        val icon : ImageView = view.findViewById(R.id.fragment_icon)
        val name : TextView = view.findViewById(R.id.fragment_title)
        val description : TextView = view.findViewById(R.id.description)
        val scrollContainer : LinearLayout = view.findViewById(R.id.scroll_container)

        icon.setImageResource(role.icon)
        name.text = role.name
        description.text = role.description

        if (role.primaryAbility != null){
            scrollContainer.addView(createAbilityView(role.primaryAbility!!))
        }

        if (role.secondaryAbility != null){
            scrollContainer.addView(createAbilityView(role.secondaryAbility!!))
        }

        if (role.tertiaryAbility != null){
            scrollContainer.addView(createAbilityView(role.tertiaryAbility!!))
        }
    }

    @SuppressLint("InflateParams")
    private fun createAbilityView(ability: Ability): LinearLayout{
        val layout : LinearLayout = layoutInflater.inflate(R.layout.item_ability_docs, null) as LinearLayout
        layout.findViewById<TextView>(R.id.ability_name).text = getString(ability.name)
        layout.findViewById<ImageView>(R.id.ability_icon).setImageResource(ability.icon)
        layout.findViewById<TextView>(R.id.ability_description).text = "Ability Description lol"
        return layout
    }

    override fun tag(): String = "DOCS_ROLE"
}