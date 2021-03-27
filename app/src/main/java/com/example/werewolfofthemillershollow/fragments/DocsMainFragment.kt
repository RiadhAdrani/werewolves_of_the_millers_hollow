package com.example.werewolfofthemillershollow.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.adapters.DocSectionsAdapter
import com.example.werewolfofthemillershollow.util.DocSection

class DocsMainFragment : BaseFragment(R.layout.fragment_docs_main) {

    override fun onBackPressed(): Boolean = false

    override fun onCreated(view: View, savedInstanceState: Bundle?) {

        val section = ArrayList<DocSection>()
        section.add(
            DocSection(
                R.string.section_about_the_game,
                R.string.section_about_the_game_desc,
                R.drawable.ic_ww_wolf,
                DocsAboutFragment()
            )
        )
        section.add(
            DocSection(
                R.string.section_how_to_play,
                R.string.section_how_to_play_desc,
                R.drawable.ic_ww_villager,
                DocsMainFragment()
            )
        )
        section.add(
            DocSection(
                R.string.section_characters,
                R.string.section_characters_desc,
                R.drawable.ic_ww_seer,
                DocsCharactersFragment()
            )
        )
        section.add(
            DocSection(
                R.string.section_advices_narrator,
                R.string.section_advices_narrator_desc,
                R.drawable.ic_ww_captain_discuss,
                DocsMainFragment()
            )
        )
        section.add(
            DocSection(
                R.string.section_advices_players,
                R.string.section_advices_players_desc,
                R.drawable.ic_ww_discussion,
                DocsMainFragment()
            )
        )

        val rv: RecyclerView = view.findViewById(R.id.fragment_docs_main_recycler_view)
        rv.setHasFixedSize(true)
        rv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv.adapter = DocSectionsAdapter(
            section,
            requireContext(),
            object : DocSectionsAdapter.OnSectionClick {
                override fun onClick(position: Int) {
                    pushFragment(section[position].fragment)
                }
            })

    }

    override fun tag(): String = "DOCS_MAIN"

}