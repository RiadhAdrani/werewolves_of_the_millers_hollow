package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Captain
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons
import com.example.werewolfofthemillershollow.utility.*

class CaptainTurn(role : Role, var activity: GameActivity) : Turn<Role>(activity) {

    private var chooseNewCaptain : Ability

    private var chooseTalker : Ability

    init {
        setRole(role)

        val ability = object :Ability.Specification{

            override fun use(self: Role, role: Role): Boolean {
                self.isCaptain = false
                role.isCaptain = true
                return true
            }

            override fun isUsable(): Boolean {
                return true
            }

            override fun isTarget(self: Role, targetRole: Role): Boolean {
                return targetRole != self
            }

        }
        chooseNewCaptain = Ability(ability, App.ABILITY_INFINITE, App.TARGET_SINGLE, Icons.captainChoose)

        val talker = object :Ability.Specification{
            override fun use(self: Role, role: Role): Boolean {
                role.isTalking = true
                return true
            }

            override fun isUsable(): Boolean {
                return true
            }

            override fun isTarget(self: Role, targetRole: Role): Boolean {
                return true
            }

        }
        chooseTalker = Ability(talker, App.ABILITY_INFINITE, App.TARGET_SINGLE, Icons.talkFirst)

    }

    override fun getPrimaryAbility(): Ability {
        return chooseTalker
    }

    override fun getSecondaryAbility(): Ability? {
        return null
    }

    override fun getTertiaryAbility(): Ability? {
        return null
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {
        if (getRole().isKilled)
            return context.getString(R.string.captain_instruction_inherit)

        return context.getString(R.string.captain_instruction)
    }

    override fun canPlay(round: Int, list: ArrayList<Role>?): Boolean {
        return true
    }

    override fun addTurn(output: ArrayList<Turn<*>>, list: ArrayList<Role>, context: Context): Boolean {

        val index = Role.roleInList(role = getRole(), list = list)

        if (index != -1) {
            output.add(CaptainTurn(list[index] as Captain, activity))
            return true
        }

        else
            Log.d("AddTurn","Captain role not found")
        return false
    }

    override fun getIcon(): Int {
        return Icons.captainChoose
    }

    override fun onStart(activity: GameActivity): Boolean {

        if (getRole().isKilled){

            if (getRole().isServed){

                val index = servant(activity)
                if (index == -1)
                    return true

                setRole(activity.playerList[index])
                activity.captainRef = activity.playerList[index]

                val onClick = object : AlertDialog.OnClick{
                    override fun onClick(alertDialog: AlertDialog) {
                        activity.displayNext()
                        alertDialog.dismiss()
                    }
                }

                val dialog = AlertDialog(
                    icon = Icons.servantTake,
                    text = R.string.captain_instruction_inherit_servant,
                    rightButton = onClick,
                    cancelable = false)

                dialog.show(activity.supportFragmentManager, App.TAG_ALERT)

                return false
            }
            return true
        }

        return false
    }

    override fun servant(activity: GameActivity): Int {

        if (activity.servantRef == null)
            return -1

        val index : Int = activity.playerList.indexOf(activity.servantRef)

        if (index == -1)
            return -1

        val player = activity.servantRef!!.player ?: return -1

        val sub = getRole().new(activity, player, activity.servantRef)!!
        sub.isCaptain = true

        activity.playerList.removeAt(index)
        activity.playerList.add(index, sub)
        activity.events.add(Event.servant(activity,sub.name))

        activity.servantRef = null

        return index
    }

    override fun getRoleToDisplay(context: Context?, list: ArrayList<Role>?): String {
        return context!!.getString(R.string.captain_name)
    }

    override fun getOnStartOnClickHandler(): UsePowerDialog.OnClickListener {

            return object : UsePowerDialog.OnClickListener{
                override fun done(
                    ability: Ability,
                    aliveList: ArrayList<Role>,
                    deadList: ArrayList<Role>,
                    adapter: TargetAdapter,
                    activity: GameActivity,
                    dialog: UsePowerDialog?
                ) {

                    if (adapter.getTargets().isEmpty()){
                        val alert = AlertDialog(text = R.string.should_use_power)
                        alert.show(activity.supportFragmentManager,App.TAG_ALERT)
                        return
                    }

                    for(index : Int in adapter.getTargets()){

                        val target : Role = adapter.getList()[index]

                        val i = activity.playerList.indexOf(target)

                        if (i != -1){
                            ability.use(getRole(),activity.playerList[i])
                            activity.playerList[i].debug()
                            setRole(activity.playerList[i])
                        }

                        activity.displayNext()
                        dialog!!.dismiss()

                    }

                }

                override fun reset(
                    aliveList: ArrayList<Role>,
                    deadList: ArrayList<Role>,
                    adapter: TargetAdapter
                ) {
                    adapter.emptyTargets()
                }
            }
    }

    override fun shouldUsePower(gameActivity: GameActivity): Boolean {
        return true
    }

    override fun getOnStartAbility(): Ability {
        return chooseNewCaptain
    }
}