package com.example.werewolfofthemillershollow.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.adapters.DocCharactersAdapter
import com.example.werewolfofthemillershollow.roles.Role

class DocsCharactersFragment: BaseFragment(R.layout.fragment_docs_characters) {

    override fun onBackPressed(): Boolean = true

    override fun onCreated(view: View, savedInstanceState: Bundle?) {

        val rv : RecyclerView = view.findViewById(R.id.fragment_recycler_view)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = DocCharactersAdapter(
            Role.getRoles(requireContext()),
            object: DocCharactersAdapter.OnCharacterClick{
            override fun onClick(position: Int) {
                TODO("Not yet implemented")
            }
        })

    }

    override fun tag(): String = "DOCS_CHARACTERS"
}