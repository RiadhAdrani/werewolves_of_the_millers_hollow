package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Barber
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.utility.*

class BarberTurn(role : Barber, private var activity: GameActivity) : Turn<Barber>(activity) {

    init {
        setRole(role)
    }

    override fun getOnCallAbility(): Ability? {
        return getPrimaryAbility()
    }

    override fun getOnStartAbility(): Ability? {
        return getPrimaryAbility()
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {
        if (getRole().isKilled)
            return context.getString(R.string.barber_instruction_killed)

        return context.getString(R.string.barber_instruction)
    }

    override fun canPlay(round: Int, list: ArrayList<Role>?): Boolean {
        return getRole().canPlay(round)
    }

    override fun addTurn(output: ArrayList<Turn<*>>, list: ArrayList<Role>, context: Context): Boolean {

        val index = Role.roleInList(role = getRole(), list = list)

        if (index != -1){
            output.add(BarberTurn(list[index] as Barber, activity = activity))
            return true
        }

        Log.d("AddTurn","Barber role not found")
        return false

    }

    override fun onStart(activity: GameActivity): Boolean {
        return getRole().isKilled && getOnStartAbility()!!.times != App.ABILITY_NONE
    }

    override fun shouldUsePower(gameActivity: GameActivity): Boolean {
        if (getRole().isKilled)
            return true

        return false
    }

    override fun servant(activity: GameActivity, events: ArrayList<Event>): Int {

        if (activity.servantRef == null)
            return -1

        val index : Int = activity.playerList.indexOf(activity.servantRef)
        Log.d("servant","servant index = $index")

        if (index == -1)
            return -1

        val player = activity.servantRef!!.player ?: return -1

        val sub = getRole().new(activity, player, activity.servantRef)
        sub.debug(tag = "servant")

        setRole(sub)
        getRole().debug(tag = "servant")

        activity.playerList.removeAt(index)
        activity.playerList.add(index, sub)
        events.add(Event.servant(activity,sub))

        activity.barberRef = getRole()

        return index
    }

    override fun getOnActionOnClickHandler(onAction: OnAction): UsePowerDialog.OnClickListener {
        return object : UsePowerDialog.OnClickListener{
            override fun done(
                ability: Ability,
                aliveList: ArrayList<Role>,
                deadList: ArrayList<Role>,
                adapter: TargetAdapter,
                activity: GameActivity,
                dialog: UsePowerDialog?
            ): Boolean {
                if (adapter.getTargets().isEmpty()){
                    val alert = AlertDialog(text = R.string.should_use_power)
                    alert.show(activity.supportFragmentManager, App.TAG_ALERT)
                    return false
                }

                for(index : Int in adapter.getTargets()){

                    val target : Role = adapter.getList()[index]

                    val i = activity.playerList.indexOf(target)

                    if (i == -1)
                        return false

                    ability.use(self = getRole(), role = activity.playerList[i], activity.playerList)

                }

                val onClick = object : AlertDialog.OnClick{
                    override fun onClick(alertDialog: AlertDialog) {
                        onAction.index ++
                        onAction.onStart()
                        dialog!!.dismiss()
                        alertDialog.dismiss()
                    }
                }

                val goodNightDialog = AlertDialog(
                    text = R.string.good_night,
                    rightButton = onClick,
                    cancelable = false)
                goodNightDialog.show(activity.supportFragmentManager, App.TAG_ALERT)
                return false
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
}