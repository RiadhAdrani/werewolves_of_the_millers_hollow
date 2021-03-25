package com.example.werewolfofthemillershollow.widgets

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.util.Icons

/**
 * Allow the distribution of roles to players
 * @param list list of roles
 */
class RolesRollerDialog(private var list: ArrayList<Role>, onClick : OnClick) : AppCompatDialogFragment() {

    /**
     * Define state of the roll button.
     * true => roll,
     * false => done
     */
    private var state = true

    /**
     * Currently picked role index
     */
    private var currentIndex = -1

    /**
     * Allow handling actions within the RolesRollerDialog object
     * @see RolesRollerDialog
     */
    interface OnClick {

        /**
         * Intended to display info of a specific role.
         * @param role specified role
         * @param dialog Roles Roller Dialog fragment
         */
        fun info(role : Role, dialog : RolesRollerDialog)

        /**
         * Intended to allow the picking of a random role to display in the dialog screen
         * @param list list to choose from
         * @param dialog Roles Roller Dialog fragment
         */
        fun roll(list : ArrayList<Role>, dialog : RolesRollerDialog)

        /**
         * Intended to dismiss the dialog.
         * @param list list of roles
         * @param dialog Roles Roller Dialog fragment
         */
        fun cancel(list: ArrayList<Role>, dialog : RolesRollerDialog)

        /**
         * Intended to reset the list of roles to their initial value
         * @param list list of roles
         * @param dialog Roles Roller Dialog fragment
         */
        fun reset(list: ArrayList<Role>, dialog : RolesRollerDialog)
    }

    /**
     * Allow manipulation of actions in the dialog
     * @see OnClick
     */
    private var onClick : OnClick? = null

    /**
     * Dialog view.
     */
    private var dialog : View? = null

    init {
        this.onClick = onClick
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context)

        dialog = activity?.layoutInflater!!.inflate(R.layout.dialog_roles_roller,null)

        builder.setView(dialog)

        for (role : Role in list){
            role.isAlive = false
        }

        val roll = dialog?.findViewById<ImageView>(R.id.roll_button)
        roll?.setOnClickListener {
            if (onClick != null) onClick?.roll(list,this)
        }

        val cancel = dialog?.findViewById<TextView>(R.id.cancel_button)
        cancel?.setOnClickListener {
            if (onClick != null) onClick?.cancel(list,this)
        }

        val reset = dialog?.findViewById<TextView>(R.id.reset_button)
        reset?.setOnClickListener {
            if (onClick != null) onClick?.reset(list, this)
        }

        return builder.create()
    }

    /**
     * Change the icon displayed in the Roles Roller Dialog.
     * @see RolesRollerDialog
     * @param icon new icon
     */
    fun setIcon(icon : Int){

        val imageView = dialog?.findViewById<ImageView>(R.id.role_icon)
        imageView?.setImageDrawable(Icons.getDrawableIcon(icon, context!!))

    }

    /**
     * Update the text of the role in the Roles Roller Dialog.
     * @see RolesRollerDialog
     * @param text new text
     */
    fun setRoleText(text : String){

        val textView = dialog?.findViewById<TextView>(R.id.player_role)
        textView?.text = text

    }

    /**
     * Return any text written by the user in the player name box
     * @return user input as string
     */
    fun getInput() : String {

        val editText = dialog?.findViewById<EditText>(R.id.player_name_box)
        return editText?.text.toString()

    }

    /**
     * Clear input text field and clear focus.
     */
    fun clearInput() {
        val editText = dialog?.findViewById<EditText>(R.id.player_name_box)
        editText?.setText(R.string.no_string)
        editText?.clearFocus()
    }

    /**
     * getter for state
     */
    fun getState() : Boolean {
        return this.state
    }

    /**
     * setter for state.
     * @param state new state
     */
    fun setState(state : Boolean){
        this.state = state
    }

    /**
     * change the icon of the roll button
     */
    fun setRollButtonIcon(icon : Int){

        val imageView = dialog?.findViewById<ImageView>(R.id.roll_button)
        imageView?.setImageDrawable(Icons.getDrawableIcon(icon = icon, context = context!!))
    }

    /**
     * getter for current index
     */
    fun getCurrentIndex() : Int {
        return currentIndex
    }

    /**
     * setter for current Index.
     * @param index new index
     */
    fun setCurrentIndex(index : Int ){
        this.currentIndex = index
    }

    /**
     * Update and change the text of the cancel button.
     * @param textRes text resource id
     */
    fun setCancelButton(textRes : Int = R.string.cancel){

        val textView = dialog?.findViewById<TextView>(R.id.cancel_button)
        textView?.setText(textRes)

    }

}