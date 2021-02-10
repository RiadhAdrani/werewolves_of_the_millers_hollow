package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Captain
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons
import com.example.werewolfofthemillershollow.utility.AlertDialog
import com.example.werewolfofthemillershollow.utility.TargetAdapter
import com.example.werewolfofthemillershollow.utility.UsePowerDialog

class CaptainTurn(role : Role, var activity: GameActivity) : Turn<Role>(activity) {

    init {
        setRole(role)
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {
        if (getRole().getIsKilled()!!)
            return context.getString(R.string.captain_instruction_inherit)

        return context.getString(R.string.captain_instruction)
    }

    override fun canPlay(round: Int, list: ArrayList<Role>?): Boolean {
        return getRole().canPlay(round)
    }

    override fun usePrimary(target: Role): Boolean {
        return getRole().usePrimaryAbility(role = target)
    }

    override fun useSecondary(target: Role): Boolean {
        return false
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

    override fun getPrimaryTargets(): Int {
        return Captain.getCaptainTargets()
    }

    override fun getHasPrimary(): Boolean {
        return true
    }

    override fun onStart(activity: GameActivity): Boolean {

        if (getRole().getIsKilled()!!){

            if (getRole().getIsServed()!!){

                val index = servant(activity)
                if (index == -1)
                    return true

                setRole(activity.playerList[index])

                val onClick = object : AlertDialog.OnClick{
                    override fun onClick(dialog: AlertDialog) {
                        activity.displayNext()
                        dialog.dismiss()
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

        val player = activity.servantRef!!.getPlayer() ?: return -1
        val sub = getRole().new(activity, player, activity.servantRef)!!
        sub.setIsCaptain(true)

        activity.playerList.removeAt(index)
        activity.playerList.add(index, sub)
        return index
    }

    override fun getTargetsPrimary(list: ArrayList<Role>): ArrayList<Role> {
        return Captain.targets(list)
    }

    override fun getPrimaryIcon(): Int {
        return Icons.captainChoose
    }

    override fun getRoleToDisplay(context: Context?, list: ArrayList<Role>?): String {
        return context!!.getString(R.string.captain_name)
    }

    override fun getOnStartTargets(list: ArrayList<Role>): ArrayList<Role> {
        return Captain.newCaptainTargets(list)
    }

    override fun getOnStartOnClickHandler(): UsePowerDialog.OnClickListener {

            return object : UsePowerDialog.OnClickListener{
                override fun done(
                    aliveList: ArrayList<Role>,
                    deadList: ArrayList<Role>,
                    adapter: TargetAdapter,
                    activity: GameActivity,
                    dialog: UsePowerDialog?
                ) {

                    if (adapter.getTargets().isEmpty())
                        return

                    Log.d("Role","Turn Class : using secondary ability")

                    for(index : Int in adapter.getTargets()){

                        val target : Role = adapter.getList()[index]

                        val i = activity.playerList.indexOf(target)

                        if (i != -1){
                            Captain.newCaptain(activity.playerList[i])
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

    override fun getOnStartOnTargetHandler(): TargetAdapter.OnClickListener {

        return object : TargetAdapter.OnClickListener{

            override fun onClick(position: Int, dialog: UsePowerDialog, adapter: TargetAdapter) {

                if (position in adapter.getTargets()){
                    Log.d("TargetAdapter","item $position removed from target list.")
                    adapter.removeTarget(position)
                    if (adapter.getTargets().size == 0){
                        dialog.setCancelState()
                    }
                    Log.d("TargetAdapter","targets = ${adapter.getTargets()}")
                    return
                }

                if (adapter.getTargets().size > 0){
                    adapter.emptyTargets()
                }

                adapter.addTarget(position)
                dialog.setResetState()
                Log.d("TargetAdapter","item $position added to target list")
                Log.d("TargetAdapter","targets = ${adapter.getTargets()}")

            }

        }
    }
}