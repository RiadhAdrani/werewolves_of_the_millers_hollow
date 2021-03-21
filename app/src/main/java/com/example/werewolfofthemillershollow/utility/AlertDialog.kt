package com.example.werewolfofthemillershollow.utility

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons

/**
 * Display an alert dialog to the user notifying him of a problem or displaying some information.
 * Could be customized to have up to three (3) button could be displayed within the dialog, one to the left, the other to the right
 * and one in the middle, the minimum is one (1) which is the right button (cancel/ok function).
 * @param icon custom icon, could be set to (-1) to hide the icon instead.
 * @param text custom text id from res.
 * @param string custom text as string. If this parameter has a non-null value, [text] will be ignored.
 * @param checkText custom check box text, could be set to null to hide the check box and its text.
 * @param leftButton custom left button.
 * @param midButton custom mid button.
 * @param rightButton custom right button. By default it is set to dismiss the dialog, could be overridden.
 * @param leftButtonText custom text for the left button. default is (OK)
 * @param midButtonText custom text for the middle button. default is (OK)
 * @param rightButtonText custom text for the right button. default is (OK)
 * @param cancelable set if the dialog is cancelable or not.
 * @see OnClick
 */
class AlertDialog(private var icon : Int = R.drawable.ic_ww_wolf,
                  private var text : Int,
                  private var string : String? = null,
                  private var checkText : Int? = null,
                  private val leftButton : OnClick? = null,
                  private val leftButtonText : Int = R.string.ok,
                  private val midButton : OnClick? = null,
                  private val midButtonText : Int = R.string.ok,
                  private val rightButton : OnClick = okButton,
                  private val rightButtonText : Int = R.string.ok,
                  private val cancelable : Boolean = true,
                 ) : AppCompatDialogFragment() {

    interface OnClick{

        fun onClick(alertDialog :AlertDialog)

    }

    companion object {

        /**
         * Default value of the left button : Dismiss the alert dialog fragment.
         */
        val okButton = object : OnClick{

            override fun onClick(alertDialog: AlertDialog) {
                alertDialog.dismiss()
            }
        }

        /**
         * Display a dialog.
         * @param activity : calling activity.
         * @param icon : drawable res id.
         * @param text : string res id. This parameter will be ignored in case [contentText] is not null.
         * @param contentText : custom text to display.
         * @param checkText : checkBox text to display.
         * @param leftButton : left button on click action. by default it is null which means that the button won't render.
         * @param leftButtonText : left button text. Useless in case of a null value for [leftButton]. By default, it is set to [R.string.ok].
         * @param midButton : mid button on click action. by default it is null which means that the button won't render.
         * @param midButtonText : mid button text. Useless in case of a null value for [midButton]. By default, it is set to [R.string.ok].
         * @param rightButton : right button on click action. by default it is set to dismiss dialog.
         * @param rightButtonText : mid button text. By default, it is set to [R.string.ok].
         * @param cancelable set if the dialog is cancelable or not. By default it is true (cancelable).
         */
        fun displayDialog(
            activity: App,
            icon : Int = R.drawable.ic_wolves,
            text: Int,
            contentText: String? = null,
            checkText: Int? = null,
            leftButton: OnClick? = null,
            leftButtonText: Int = R.string.ok,
            midButton: OnClick? = null,
            midButtonText: Int = R.string.ok,
            rightButton : OnClick = okButton,
            rightButtonText : Int = R.string.ok,
            cancelable : Boolean = true,
        ){

            val display = AlertDialog(
                icon= icon,
                text = text,
                string = contentText,
                checkText= checkText,
                leftButton= leftButton,
                leftButtonText=leftButtonText,
                midButton = midButton,
                midButtonText = midButtonText,
                rightButton = rightButton,
                rightButtonText = rightButtonText,
                cancelable = cancelable
            )
            display.show(activity.supportFragmentManager, App.TAG_ALERT )
        }


    }

    private lateinit var dialog : View

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = android.app.AlertDialog.Builder(context)

        dialog = activity?.layoutInflater!!.inflate(R.layout.dialog_alert,null)

        builder.setView(dialog)

        val iconView : ImageView = dialog.findViewById(R.id.dialog_icon)

        if (icon != -1) {
            iconView.setImageDrawable(Icons.getDrawableIcon(icon, context!!))
        }
        else {
            iconView.visibility = View.GONE
        }

        val textView : TextView = dialog.findViewById(R.id.dialog_text)
        if (string != null){
            textView.text = string
        } else {
            textView.text = getString(text)
        }
        
        val checkBox : CheckBox = dialog.findViewById(R.id.dialog_check)
        val checkLabel : TextView = dialog.findViewById(R.id.dialog_check_info)
        if (checkText != null){
            checkLabel.text = getString(checkText!!)
        }
        else {
            checkLabel.text = getString(R.string.no_string)
            checkBox.visibility = View.GONE
            checkLabel.visibility = View.GONE
        }

        val buttonLeft : TextView = dialog.findViewById(R.id.dialog_button_left)
        if (leftButton != null){
            buttonLeft.text = getString(leftButtonText)
            buttonLeft.setOnClickListener {
                leftButton.onClick(this)
            }
        }
        else {
            buttonLeft.visibility = View.GONE
        }

        val buttonMid: TextView = dialog.findViewById(R.id.dialog_button_mid)
        if (midButton != null){
            buttonMid.text = getString(midButtonText)
            buttonMid.setOnClickListener {
                midButton.onClick(this)
            }
        }
        else {
            buttonMid.visibility = View.GONE
        }

        val buttonRight : TextView = dialog.findViewById(R.id.dialog_button_right)
        buttonRight.text = getString(rightButtonText)
        buttonRight.setOnClickListener {
            rightButton.onClick(this)
        }

        if (!cancelable)
            isCancelable = false

        return builder.create()
    }
}