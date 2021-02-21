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
import com.example.werewolfofthemillershollow.settings.Icons

/**
 * Display an alert dialog to the user notifying him of a problem or displaying some information.
 * Could be customized to have up to three (3) button could be displayed within the dialog, one to the left, the other to the right
 * and one in the middle, the minimum is one (1) which is the right button (cancel/ok function).
 * @param icon custom icon, could be set to (-1) to hide the icon instead.
 * @param text custom text
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
class AlertDialog(private var icon : Int = R.drawable.ic_info,
                  private var text : Int,
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
        textView.text = getString(text)

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