package com.example.werewolfofthemillershollow.util

import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.turn.Turn
import java.io.Serializable

data class SavedGame(
    val id : Long,
    val roleList : ArrayList<Role>,
    val turnList : ArrayList<Turn<*>>,
    val night : Int) : Serializable {



}