package com.example.werewolfofthemillershollow.settings

/**
 * contains settings for the app.
 * Could be accessed globally
 */
abstract class App {

    companion object {

        /*** can use ability once*/
        const val ABILITY_ONCE : Int = 1

        /*** cannot use ability*/
        const val ABILITY_NONE : Int = 0

        /*** can use ability an infinite amount of time*/
        const val ABILITY_INFINITE : Int = -1

        /*** identifier for the village team*/
        const val TEAM_VILLAGE : Int = 0

        /*** identifier for the wolves team*/
        const val TEAM_WOLVES : Int = 1

        /*** identifier for the solo team*/
        const val TEAM_SOLO : Int = 2

    }

}