package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons
import com.example.werewolfofthemillershollow.utility.AlertDialog
import com.example.werewolfofthemillershollow.utility.TargetAdapter
import com.example.werewolfofthemillershollow.utility.UsePowerDialog
import kotlin.collections.ArrayList

abstract class Turn<R : Role>(private var gameActivity: GameActivity) {

    /**
     * Return the instructions said by the game master when the current turn begins.
     * @param context calling context
     * @param list list of current alive players.
     * @return instruction as string
     */
    abstract fun getInstructions(context: Context, list : ArrayList<Role>? = null):String

    abstract fun addTurn(output : ArrayList<Turn<*>>, list: ArrayList<Role>, context: Context): Boolean

    private var role : R? = null

    /**
     * getter for Turn.role
     */
    fun getRole() : R{
        return role!!
    }

    /**
     * setter for Turn.Role
     * @param role new role
     */
    fun setRole(role : R){
        this.role = role
    }

    /**
     * Returns the name to be displayed.
     */
    open fun getPlayer(list : ArrayList<Role>? = null): String{
        return role?.getPlayer()!!
    }

    /**
     * Returns the displayed icon of the current role turn.
     */
    open fun getIcon(): Int{
        return role?.getIcon()!!
    }

    /**
     * Returns the icon representing the primary ability.
     */
    open fun getPrimaryIcon(): Int{
        if (role?.getHasPrimary()!!)
            return role?.getPrimaryIcon()!!

        return Icons.noAbility
    }

    /**
     * Returns the icon representing the secondary ability.
     */
    open fun getSecondaryIcon(): Int{
        if (role?.getHasSecondary()!!)
            return role?.getSecondaryIcon()!!

        return Icons.noAbility
    }

    /**
     * Return the name of the role to be displayed.
     */
    open fun getRoleToDisplay(context: Context? = null, list : ArrayList<Role>? = null): String{
        return role?.getName()!!
    }

    /**
     * Return whether this role can use its primary ability or no.
     */
    open fun getHasPrimary(): Boolean{
        return role?.getHasPrimary()!!
    }

    /**
     * Returns whether the role can use its primary ability in the current round.
     */
    open fun canPrimary(): Boolean{
        return true
    }

    /**
     * Return whether this role can use its secondary ability or no.
     */
    open fun getHasSecondary(): Boolean{
        return role?.getHasSecondary()!!
    }

    /**
     * Returns whether the role can use its secondary ability in the current round.
     */
    open fun canSecondary(): Boolean{
        return false
    }

    /**
     * Return whether this role can use its tertiary ability or no.
     */
    open fun getHasTertiary(): Boolean{
        return role?.getHasTertiary()!!
    }

    /**
     * Returns whether the role can use its tertiary ability in the current round.
     */
    open fun canTertiary(): Boolean{
        return false
    }

    /**
     * Return whether this role can use its tertiary ability or no.
     */
    open fun getCanUseTertiary(): Boolean{
        return role?.getHasTertiary()!!
    }

    /**
     * returns the number of players that can be targeted with the primary ability
     */
    open fun getPrimaryTargets() : Int{
        return role!!.getPrimaryTargets()
    }

    /**
     * returns the number of players that can be targeted with the secondary ability
     */
    open fun getSecondaryTargets(): Int{
        return role!!.getSecondaryTargets()
    }

    /**
     * returns the number of players that can be targeted with the tertiary ability
     */
    open fun getTertiaryTargets(): Int{
        return role!!.getTertiaryTargets()
    }

    /**
     * Make the needed changes to the servantRef.
     */
    open fun servant(activity: GameActivity): Int{

        if (activity.servantRef == null)
            return -1

        val index : Int = activity.playerList.indexOf(activity.servantRef)
        if (index == -1)
            return -1

        val player = activity.servantRef!!.getPlayer() ?: return -1
        val sub = getRole().new(activity, player, activity.servantRef)!!

        activity.playerList.removeAt(index)
        activity.playerList.add(index, sub)
        return index

    }

    /**
     * Interface used to override the functionality of the fragment UsePowerDialog.
     * @see UsePowerDialog
     */
    open fun getPrimaryOnClickHandler() : UsePowerDialog.OnClickListener{

        return object : UsePowerDialog.OnClickListener{
            override fun done(
                aliveList: ArrayList<Role>,
                deadList: ArrayList<Role>,
                adapter: TargetAdapter,
                activity: GameActivity,
                dialog: UsePowerDialog?
            ) {
                if (adapter.getTargets().isEmpty()){
                    val alert = AlertDialog(text = com.example.werewolfofthemillershollow.R.string.should_use_power)
                    alert.show(activity.supportFragmentManager,App.TAG_ALERT)
                    return
                }


                Log.d("Role","Turn Class : using secondary ability")

                for(index : Int in adapter.getTargets()){

                    val target : Role = adapter.getList()[index]

                    val i = gameActivity.playerList.indexOf(target)

                    if (i == -1)
                        return

                    usePrimary(gameActivity.playerList[i])
                    gameActivity.playerList[i].debug()

                }

                dialog?.dismiss()
                activity.next()
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

    /**
     * Interface used to override the functionality of the fragment UsePowerDialog.
     * @see UsePowerDialog
     */
    open fun getSecondaryOnClickHandler() : UsePowerDialog.OnClickListener{

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

                    val i = gameActivity.playerList.indexOf(target)

                    if (i == -1)
                        return

                    useSecondary(gameActivity.playerList[i])
                    gameActivity.playerList[i].debug()

                }

                dialog?.dismiss()
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

    /**
     * Interface used to handle clicking on targets in the fragment UsePowerDialog.
     * @see UsePowerDialog
     * @see TargetAdapter
     */
    open fun getPrimaryOnTargetHandler() : TargetAdapter.OnClickListener{

        return object : TargetAdapter.OnClickListener{

            override fun onClick(position: Int, dialog: UsePowerDialog, adapter: TargetAdapter) {

                Log.d("TargetAdapter","Clicked on pos $position")

                if (getPrimaryTargets() == App.TARGET_NONE)
                    return

                if (position in adapter.getTargets()){
                    Log.d("TargetAdapter","item $position removed from target list.")
                    adapter.removeTarget(position)
                    if (adapter.getTargets().size == 0){
                        dialog.setCancelState()
                    }
                    Log.d("TargetAdapter","targets = ${adapter.getTargets()}")
                    return
                }

                if (getPrimaryTargets() == App.TARGET_SINGLE){

                    if (adapter.getTargets().size > 0){
                        adapter.emptyTargets()
                    }

                    adapter.addTarget(position)
                    dialog.setResetState()
                    Log.d("TargetAdapter","item $position added to target list")
                    Log.d("TargetAdapter","targets = ${adapter.getTargets()}")
                    return
                }

                if (getPrimaryTargets() == App.TARGET_TWO){

                    if (adapter.getTargets().size > 1){
                        adapter.getTargets().removeAt(0)
                    }

                    adapter.addTarget(position)
                    dialog.setResetState()
                    Log.d("TargetAdapter","item $position added to target list")
                    Log.d("TargetAdapter","targets = ${adapter.getTargets()}")
                }

            }

        }

    }

    /**
     * Interface used to handle clicking on targets in the fragment UsePowerDialog.
     * @see UsePowerDialog
     * @see TargetAdapter
     */
    open fun getSecondaryOnTargetHandler() : TargetAdapter.OnClickListener{

        return object : TargetAdapter.OnClickListener{

            override fun onClick(position: Int, dialog: UsePowerDialog, adapter: TargetAdapter) {

                Log.d("TargetAdapter","Clicked on pos $position")

                if (getSecondaryTargets() == App.TARGET_NONE)
                    return

                if (position in adapter.getTargets()){
                    Log.d("TargetAdapter","item $position removed from target list.")
                    adapter.removeTarget(position)
                    if (adapter.getTargets().size == 0){
                        dialog.setCancelState()
                    }
                    Log.d("TargetAdapter","targets = ${adapter.getTargets()}")
                    return
                }

                if (getSecondaryTargets() == App.TARGET_SINGLE){

                    if (adapter.getTargets().size > 0){
                        adapter.emptyTargets()
                    }

                    adapter.addTarget(position)
                    dialog.setResetState()
                    Log.d("TargetAdapter","item $position added to target list")
                    Log.d("TargetAdapter","targets = ${adapter.getTargets()}")
                    return
                }

                if (getSecondaryTargets() == App.TARGET_TWO){

                    if (adapter.getTargets().size > 1){
                        adapter.getTargets().removeAt(0)
                    }

                    adapter.addTarget(position)
                    dialog.setResetState()
                    Log.d("TargetAdapter","item $position added to target list")
                    Log.d("TargetAdapter","targets = ${adapter.getTargets()}")
                }

            }

        }

    }

    /**
     * Executes after the role has been initialized in the game activity.
     */
    open fun onStart(activity : GameActivity): Boolean?{
        return false
    }

    /**
     * Returns the list of player that could be targeted in emergency cases.
     * @param list list of alive players.
     * @return returns empty list by default. could be overridden in inheriting classes.
     */
    open fun getOnStartTargets(list : ArrayList<Role>): ArrayList<Role>{
        return ArrayList()
    }

    /**
     * Interface used to handle clicking on buttons in the fragment UsePowerDialog
     * @see UsePowerDialog
     */
    open fun getOnStartOnClickHandler() : UsePowerDialog.OnClickListener?{
        return null
    }

    /**
     * Interface used to handle clicking on targets in the fragment UsePowerDialog.
     * @see TargetAdapter
     */
    open fun getOnStartOnTargetHandler() : TargetAdapter.OnClickListener?{
        return null
    }

    /**
     * Returns whether the current role turn is playable or not.
     * * Returning (true) means that the turn will be played normally.
     * * Returning (false) means that the turn will be skipped.
     * @param round current round
     * @param list list of alive players
     */
    abstract fun canPlay(round : Int, list : ArrayList<Role>? = null) : Boolean

    /**
     * Allow the use of the primary ability of the role.
     * @param target only used when the ability supports only one target.
     * @return (true) if the target was successfully affected and can use his ability, (false) otherwise.
     */
    abstract fun usePrimary(target : Role): Boolean

    /**
     * Allow the use of the secondary ability of the role.
     * @param target only used when the ability supports only one target.
     * @return (true) if the target was successfully affected and can use his ability, (false) otherwise.
     */
    abstract fun useSecondary(target : Role): Boolean

    /**
     * Returns a list of the targets for the primary ability
     * @param list of roles to be extracted from.
     */
    open fun getTargetsPrimary(list : ArrayList<Role>) : ArrayList<Role>{

        val output = ArrayList<Role>()

        for (r : Role in list){
            if (role!!.isATargetPrimary(r))
                output.add(r)
        }

        return output
    }

    /**
     * Returns a list of the targets for the secondary ability
     * @param list of roles to be extracted from.
     */
    open fun getTargetsSecondary(list : ArrayList<Role>): ArrayList<Role>{

        val output = ArrayList<Role>()

        for (r : Role in list){
            if (role!!.isATargetSecondary(r))
                output.add(r)
        }

        return output

    }

    /**
     * Returns a list of the targets for the tertiary ability
     * @param list of roles to be extracted from.
     */
    open fun getTargetsTertiary(list : ArrayList<Role>): ArrayList<Role>{

        val output = ArrayList<Role>()

        for (r : Role in list){
            if (role!!.isATargetTertiary(r))
                output.add(r)
        }

        return output

    }

    /**
     * Return whether the current role should use his power or not.
     */
    abstract fun shouldUsePower(gameActivity: GameActivity): Boolean

}