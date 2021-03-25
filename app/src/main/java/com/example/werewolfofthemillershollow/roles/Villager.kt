package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.util.App

/**
 * A simple villager :
 * has no special ability expect his intelligence.
 * @see Role
 * @param context context in which the class object is created
 */
class Villager(context: Context) : Role() {

    init {
        name = context.getString(App.VILLAGER_NAME)
        description = context.getString(App.VILLAGER_DESCRIPTION)
        team = App.VILLAGER_TEAM
        icon = App.VILLAGER_ICON
    }

    override fun canPlay(round: Int): Boolean {
        return false
    }

    override fun new(context: Context, name: String, role: Role?): Role {
        val output = Villager(context)
        output.player = name

        if (role != null){
            output.copyStatusEffects(role)
        }

        return output
    }
}