package com.example.werewolfofthemillershollow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons
import com.example.werewolfofthemillershollow.utility.AlertDialog
import com.example.werewolfofthemillershollow.utility.RoleAdapter
import com.example.werewolfofthemillershollow.utility.RolesRollerDialog

/**
 * Activity allowing the user to customize his game,
 * in addition to distributing role to players in a random way.
 * @see Role
 */
class NewGameActivity : AppCompatActivity() {

    /**
     * Return button : allow user to back-track to the previous activity
     * @see MainActivity
     */
    private lateinit var returnButton: ImageView

    /**
     * Allow the user to start distributing roles.
     */
    private lateinit var rollButton: Button

    /**
     * Allow the user to start the game after distributing the roles.
     */
    private lateinit var startButton : Button

    /**
     * Recycler view for currently selected players
     */
    private lateinit var currentPlayersRV : RecyclerView

    /**
     * Adapter for current player recycler view
     */
    private lateinit var currentPlayersAdapter : RoleAdapter

    /**
     * Recycler view for available roles
     */
    private lateinit var availableRolesRV : RecyclerView

    /**
     * Adapter for available roles recycler view
     */
    private lateinit var availableRolesAdapter : RoleAdapter

    /**
     * Indicates the current number of players
     */
    private lateinit var numberOfPlayer : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_game)

        numberOfPlayer = findViewById(R.id.current_players_count)

        val generate : Button = findViewById(R.id.generate)

        generate.setOnClickListener {
            generateRoles()
        }

        returnButton = findViewById(R.id.return_button)
        returnButton.setOnClickListener {
            returnToMainActivity()
        }

        rollButton = findViewById(R.id.roll_button)
        rollButton.setOnClickListener {

            val error: Int

            if (currentPlayersAdapter.getList().isEmpty()) {
                Toast.makeText(baseContext,R.string.not_enough_player,Toast.LENGTH_SHORT).show()
            }
            else {
                error = Role.isListValid(currentPlayersAdapter.getList())

                if (error == Role.NO_ERROR)
                    rolesRoller()
                else {

                    val alert = AlertDialog(
                        text = Role.errorMessage(error = error)
                    )
                    alert.isCancelable = true
                    alert.show(supportFragmentManager,App.TAG_ALERT)

                }
            }

        }

        startButton = findViewById(R.id.start_button)
        startButton.setOnClickListener {
            startGame()
        }

        startButton.setOnLongClickListener {
            return@setOnLongClickListener startDummyGame()
        }

        currentPlayersRV = findViewById(R.id.current_players_rv)
        availableRolesRV = findViewById(R.id.roles_rv)

        currentPlayersAdapter = RoleAdapter(context = applicationContext, list = ArrayList())
        currentPlayersRV.layoutManager = GridLayoutManager(baseContext,resources.getInteger(R.integer.role_span_count))
        currentPlayersAdapter.setListener(listener = currentPlayersListener())
        currentPlayersRV.adapter = currentPlayersAdapter

        availableRolesAdapter = RoleAdapter(context = applicationContext, list = Role.getRoles(applicationContext))
        availableRolesRV.layoutManager = GridLayoutManager(baseContext, resources.getInteger(R.integer.role_span_count))
        availableRolesAdapter.setListener(listener = availableRoleListener())
        availableRolesRV.adapter = availableRolesAdapter

        updateNumberOfPlayers()

    }

    /**
     * Return an implementation of the interface needed for interaction and click even handling
     * in RoleAdapter for current players recycler view.
     * @see RoleAdapter
     * @see RoleAdapter.OnItemClick
     * @return Interface implementation
     */
    private fun currentPlayersListener() : RoleAdapter.OnItemClick{
        return object : RoleAdapter.OnItemClick{

            override fun onClick(position: Int) {

                val item = currentPlayersAdapter.getList()[position]

                currentPlayersAdapter.removeItem(position)

                if (item.isUnique()) availableRolesAdapter.addItem(item,0)

                updateNumberOfPlayers()
            }

            override fun onHold(position: Int): Boolean {
                return true
            }

        }
    }

    /**
     * Return an implementation of the interface needed for interaction and click even handling
     * in RoleAdapter for available role recycler view.
     * @see RoleAdapter
     * @see RoleAdapter.OnItemClick
     * * @return Interface implementation
     */
    private fun availableRoleListener() : RoleAdapter.OnItemClick{
        return object : RoleAdapter.OnItemClick{

            override fun onClick(position: Int) {

                val item = availableRolesAdapter.getList()[position]

                if (item.isUnique()) availableRolesAdapter.removeItem(position)

                currentPlayersAdapter.addItem(item,0)

                updateNumberOfPlayers()
            }

            override fun onHold(position: Int): Boolean {
                return true
            }

        }
    }

    /**
     * Generate role according to the user's input.
     */
    private fun generateRoles() {

        val number : Int = if (numberOfPlayer.text.isEmpty())
            7
        else
            Integer.parseInt(numberOfPlayer.text.toString())

        currentPlayersAdapter.getList().clear()
        currentPlayersAdapter.setList(Role.getRoles(baseContext,number))

        for (role : Role in currentPlayersAdapter.getList()){

            if (role.isUnique()) {
                val index = Role.deleteRole(role,availableRolesAdapter.getList())
                if ( index != -1) availableRolesAdapter.notifyItemRemoved(index)
            }
        }

        updateNumberOfPlayers()
    }

    /**
     * Automatically update the EditText of number of player displayed.
     */
    private fun updateNumberOfPlayers() {
        numberOfPlayer.setText("${currentPlayersAdapter.getList().size}")
        numberOfPlayer.clearFocus()
    }

    /**
     * Return to main activity, and finish this activity
     */
    private fun returnToMainActivity() {
        val i = Intent(applicationContext,MainActivity::class.java)
        startActivity(i)
        finish()
    }

    /**
     * OnClick listener for roll button, initialize roles roller dialog.
     */
    private fun rolesRoller(){

        val onClick = object : RolesRollerDialog.OnClick{

            override fun info(role: Role, dialog : RolesRollerDialog) {
            }

            override fun roll(list: ArrayList<Role>, dialog : RolesRollerDialog) {

                if (!dialog.getState()){

                    val inputText = dialog.getInput().trim()

                    if (invalidPlayerNameAlert(input = inputText, list = list))
                        return

                    list[dialog.getCurrentIndex()].setPlayer(inputText)

                    Toast.makeText(
                        baseContext,
                        "${list[dialog.getCurrentIndex()].getName()} " +
                                "${getString(R.string.is_now_named)} " +
                                "${list[dialog.getCurrentIndex()].getPlayer()} ",
                        Toast.LENGTH_SHORT).show()

                    dialog.clearInput()
                    dialog.setState(true)
                    dialog.setRoleText(getString(R.string.role))
                    dialog.setRollButtonIcon(Icons.roll)

                    if (areAllRolesPicked(list)){
                        dialog.setCancelButton(R.string.done)
                    }

                    return
                }

                if (areAllRolesPicked(list)) {
                    Toast.makeText(baseContext,R.string.roles_distributed,Toast.LENGTH_SHORT).show()
                }
                else {

                    dialog.setCurrentIndex(pickRandomAliveRole(list))
                    val role = list[dialog.getCurrentIndex()]
                    role.setIsAlive(true)
                    dialog.setIcon(role.getIcon()!!)
                    dialog.setRoleText(role.getName()!!)
                    dialog.setState(false)
                    dialog.setRollButtonIcon(Icons.done)
                    dialog.clearInput()
                }
            }

            override fun cancel(list: ArrayList<Role>, dialog : RolesRollerDialog) {

                if (!areAllRolesNamed(list)){
                    for (role : Role in list){
                        role.setIsAlive(true)
                        role.setPlayer(null)
                    }
                }

                dialog.dismiss()

            }

            override fun reset(list: ArrayList<Role>, dialog : RolesRollerDialog) {
                for (role : Role in list){
                    role.setIsAlive(false)
                    role.setPlayer(null)
                    dialog.setRoleText(getString(R.string.role))
                    dialog.setState(true)
                    dialog.setRollButtonIcon(Icons.roll)
                    dialog.setCancelButton()
                    dialog.clearInput()
                }
            }

        }

        val dialog = RolesRollerDialog(currentPlayersAdapter.getList(),onClick)
        dialog.isCancelable = false
        dialog.show(supportFragmentManager,"ROLES_ROLLER")
    }

    /**
     * Return the index of a non-Alive role in the list.
     * This function uses recursion.
     * @param list list to return from
     * @return a valid index from list
     */
    private fun pickRandomAliveRole(list : ArrayList<Role>) : Int {

        val index = App.random(max = list.size)

        if (index < 0 && index >= list.size)
            return pickRandomAliveRole(list)

        if (!list[index].getIsAlive()!!){
            return index
        }

        return pickRandomAliveRole(list)
    }

    /**
     * Return whether the roles are all picked or not.
     * @param list list to check
     */
    private fun areAllRolesPicked(list : ArrayList<Role>) : Boolean{

        for (role : Role in list){
            if (!role.getIsAlive()!!)
                return false
        }

        return true

    }

    /**
     * Return whether all the players are named or not.
     * @param list list to check
     */
    private fun areAllRolesNamed(list : ArrayList<Role>) : Boolean{

        for (role : Role in list){
            if (role.getPlayer() == null)
                return false
        }

        return true

    }

    /**
     * Display an alert based of the input made by the user.
     * @param input user input
     * @param list list of current players
     */
    private fun invalidPlayerNameAlert(input : String, list : ArrayList<Role>) : Boolean{

        if (input.trim().isEmpty()){

            val dialog = AlertDialog(
                icon = R.drawable.ic_info,
                text = R.string.empty_name_alert)
            dialog.show(supportFragmentManager,App.TAG_ALERT)

            return true
        }


        for (role : Role in list){
            if (role.getPlayer().equals(input) && !role.getPlayer()?.trim().equals(resources.getString(R.string.no_string))){

                val dialog = AlertDialog(
                    icon = R.drawable.ic_info,
                    text = R.string.existing_name_alert)
                dialog.show(supportFragmentManager,App.TAG_ALERT)

                return true
            }
        }

        return false

    }

    /**
     * Start the game: open GameActivity if there is no issues regarding the composition of the players.
     * The method check if all roles are picked (have been given a name) in addition to making another check
     * concerning the composition of the players and how teams play in the game.
     * @see GameActivity
     * @see Role.isListValid
     * @see areAllRolesPicked
     */
    private fun startGame(){

        if (currentPlayersAdapter.getList().size < 7){
            Toast.makeText(baseContext,R.string.not_enough_player,Toast.LENGTH_LONG).show()
            return
        }

        if (!areAllRolesNamed(list = currentPlayersAdapter.getList())){
            Toast.makeText(baseContext,R.string.roles_not_distributed,Toast.LENGTH_LONG).show()
            return
        }

        val error = Role.isListValid(currentPlayersAdapter.getList())

        if (error != Role.NO_ERROR){
            Toast.makeText(baseContext,Role.errorMessage(error),Toast.LENGTH_LONG).show()
            return
        }

        for (role : Role in currentPlayersAdapter.getList()){
            role.debug(tag = "Role")
        }

        val i = Intent(baseContext,GameActivity::class.java)
        i.putExtra("list", currentPlayersAdapter.getList())
        finish()
        startActivity(i)

    }

    /**
     * Starts a dummy game for testing purpose.
     * @return true
     */
    private fun startDummyGame() : Boolean{

        if (currentPlayersAdapter.getList().size < 7){
            Toast.makeText(baseContext,R.string.not_enough_player,Toast.LENGTH_LONG).show()
        }
        else {
            val i = Intent(baseContext,GameActivity::class.java)
            i.putExtra("list", Role.fillWithDummyNames(currentPlayersAdapter.getList()))
            finish()
            startActivity(i)

            for (role : Role in currentPlayersAdapter.getList()){
                role.debug(tag = "Role")
            }
        }

        return true
    }

}