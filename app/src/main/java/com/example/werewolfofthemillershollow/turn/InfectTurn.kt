package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.FatherOfWolves
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons
import com.example.werewolfofthemillershollow.utility.*

class InfectTurn(role : FatherOfWolves, var activity: GameActivity) : Turn<FatherOfWolves>(activity) {

    init {
        setRole(role)
    }

    override fun onSkip(activity: GameActivity): Boolean {

        val onClick = object : AlertDialog.OnClick{
            override fun onClick(alertDialog: AlertDialog) {

                alertDialog.dismiss()

                val skip = object : AlertDialog.OnClick{
                    override fun onClick(alertDialog: AlertDialog) {
                        alertDialog.dismiss()
                        activity.next()
                    }
                }

                val dialog = AlertDialog(
                    text = R.string.good_night,
                    rightButton = skip,
                    cancelable = false)
                dialog.show(activity.supportFragmentManager,App.TAG_ALERT)
            }
        }

        val dialog = AlertDialog(
            text = R.string.infect_wake_pack,
            cancelable = false,
            rightButton = onClick)
        dialog.show(activity.supportFragmentManager,App.TAG_ALERT)

        return true
    }

    override fun getOnClickHandler(): UsePowerDialog.OnClickListener {

        return object : UsePowerDialog.OnClickListener{
            override fun done(
                ability: Ability,
                aliveList: ArrayList<Role>,
                deadList: ArrayList<Role>,
                adapter: TargetAdapter,
                activity: GameActivity,
                dialog: UsePowerDialog?
            ) {

                var done = false

                val onClick = object : AlertDialog.OnClick{
                    override fun onClick(alertDialog: AlertDialog) {

                        alertDialog.dismiss()

                        val gn = object : AlertDialog.OnClick{
                            override fun onClick(alertDialog: AlertDialog) {
                                alertDialog.dismiss()
                                dialog!!.dismiss()
                                activity.next()
                            }
                        }

                        val goodNightDialog = AlertDialog(
                            text = R.string.good_night,
                            rightButton = gn,
                            cancelable = false)
                        goodNightDialog.show(activity.supportFragmentManager,App.TAG_ALERT)
                        return
                    }
                }

                if (adapter.getTargets().isEmpty()){
                    val alert = AlertDialog(
                        icon = Icons.fatherInfect,
                        text = R.string.infect_action_fail,
                        rightButton = onClick,
                        cancelable = false
                    )

                    alert.show(activity.supportFragmentManager, App.TAG_ALERT)
                    return
                }


                Log.d("Role","Turn Class : using secondary ability")

                for(index : Int in adapter.getTargets()){

                    val target : Role = adapter.getList()[index]

                    val i = activity.playerList.indexOf(target)

                    if (i == -1)
                        return

                    if (getPrimaryAbility()!!.use(getRole(),activity.playerList[i], activity.playerList))
                        done = true

                }

                val text = if (done) R.string.infect_action_success
                           else      R.string.infect_action_fail

                val alert = AlertDialog(
                    icon = Icons.fatherInfect,
                    text = text,
                    rightButton = onClick,
                    cancelable = false
                )
                alert.show(activity.supportFragmentManager, App.TAG_ALERT)
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

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {

        for (role : Role in list!!){
            if (role.isKilled)
                return context.getString(R.string.infect_instruction)
        }

        return context.getString(R.string.infect_instruction_nobody)
    }

    override fun canPlay(round: Int, list: ArrayList<Role>?): Boolean {
        return getRole().canPlay(round)
    }

    override fun addTurn(output: ArrayList<Turn<*>>, list: ArrayList<Role>, context: Context): Boolean {

        val index = Role.roleInList(role = getRole(), list = list)

        if (index != -1) {
            output.add(InfectTurn(list[index] as FatherOfWolves,activity))
            return true
        }

        else
            Log.d("AddTurn","role not found")
        return false
    }

    override fun shouldUsePower(gameActivity: GameActivity): Boolean {
        return false
    }

    override fun servant(activity: GameActivity, events: ArrayList<Event>): Int {
        if (activity.servantRef == null)
            return -1

        val index : Int = activity.playerList.indexOf(activity.servantRef)
        if (index == -1)
            return -1

        val player = activity.servantRef!!.player ?: return -1
        val sub = getRole().new(activity, player, activity.servantRef)
        sub.primaryAbility!!.times = getRole().primaryAbility!!.times
        setRole(sub as FatherOfWolves)
        

        activity.playerList.removeAt(index)
        activity.playerList.add(index, sub)
        events.add(Event.servant(activity,sub.name))
        return index
    }
}