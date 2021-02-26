package com.example.werewolfofthemillershollow

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.werewolfofthemillershollow.roles.*
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons
import com.example.werewolfofthemillershollow.turn.*
import com.example.werewolfofthemillershollow.utility.*

/**
 * Manage how game is played and progressed.
 * @see NewGameActivity
 */
class GameActivity : App() {

    interface OnCall{
        fun onCall(dialog : VotingDialog)
    }

    /**
     * Current player turn index
     */
    private var index : Int = 0

    /**
     * Turn count
     */
    private var round : Int = 1

    /**
     * List of alive players
     */
    lateinit var playerList : ArrayList<Role>

    /**
     * List of dead players
     */
    lateinit var deadList : ArrayList<Role>

    /**
     * List of current roles turns
     */
    lateinit var turnList : ArrayList<Turn<*>>

    /**
     * Allow the game master to kick the current player.
     */
    private lateinit var kickButton : ImageView

    /**
     * Display the current player name.
     */
    private lateinit var playerName : TextView

    /**
     * Allow the access to more options.
     */
    private lateinit var moreOptions : ImageView

    /**
     * Display the icon of the currently playing role.
     */
    private lateinit var roleIcon : ImageView

    /**
     * Display the name of the currently playing role.
     */
    private lateinit var roleName : TextView

    /**
     * Display the different status effects applied to this player.
     */
    private lateinit var statusEffects : RecyclerView

    /**
     * Adapter for status effect recycler view.
     */
    private lateinit var statusEffectAdapter : StatusEffectAdapter

    /**
     * Display needed info for the game master/narrator.
     */
    private lateinit var narratorText : TextView

    /**
     * Allow the current player to use his first ability.
     */
    private lateinit var abilityOne : ImageView

    /**
     * Allow the current player to use his secondary ability if he has one.
     */
    private lateinit var abilityTwo : ImageView

    /**
     * Allow the current player to use his tertiary ability if he has one.
     */
    private lateinit var abilityThree : ImageView

    /**
     * Allow the user to skip this player
     */
    private lateinit var skip : ImageView

    /**
     * Display the current turn count.
     */
    private lateinit var turn : TextView

    /**
     * reference to the servant in the game.
     * * Null if no servant exists.
     */
    var servantRef : Servant? = null

    /**
     * reference to the current captain in the game.
     */
    lateinit var captainRef: Role

    /**
     * targets killed by the wolf pack this night.
     */
    var wolfTargets : ArrayList<Role> = ArrayList()

    /**
     * reference to the barber player.
     */
    var barberRef : Barber? = null

    /**
     * Reference to the barber turn.
     */
    var barberTurnRef : BarberTurn? = null

    /**
     * List of events that occured last night (round).
     */
    var events : ArrayList<Event> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)

        kickButton = findViewById(R.id.kick_player)
        kickButton.setOnClickListener {
            //TODO : onClickListener
        }

        playerName = findViewById(R.id.player_name)

        moreOptions = findViewById(R.id.more_options)
        moreOptions.setOnClickListener {
            //TODO : onClickListener
        }

        turn = findViewById(R.id.turn_count)
        setTurn()

        skip = findViewById(R.id.skip)
        skip.setOnClickListener {
            turnList[index].onSkip(this)
        }

        roleIcon = findViewById(R.id.role_icon)

        roleName = findViewById(R.id.role_name)

        statusEffects = findViewById(R.id.status_effects)
        statusEffectAdapter = StatusEffectAdapter(list = ArrayList(),context = baseContext, listener = null)
        statusEffects.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL, false)
        statusEffects.adapter = statusEffectAdapter

        narratorText = findViewById(R.id.narrator_info)

        abilityOne = findViewById(R.id.ability_one)

        abilityTwo = findViewById(R.id.ability_two)

        abilityThree = findViewById(R.id.ability_three)

        deadList = ArrayList()

        playerList = intent.getSerializableExtra("list") as ArrayList<Role>

        for (role : Role in playerList){
            role.debug(tag = "Role")
        }

        turnList = createTurns(playerList)

        displayNext()
    }

    /**
     * Update role icon drawable with a new one.
     * @param icon new icon id
     */
    private fun setIcon(icon : Int){
        if (icon == -1) return
        roleIcon.setImageResource(icon)
    }

    /**
     * Update the role textView.
     * @param name new role name
     */
    private fun setRole(name : String){
        roleName.text = name
    }

    /**
     * Update the player name TextView.
     * @param name new name
     */
    private fun setName(name : String){
        playerName.text = name
    }

    /**
     * Update narrator instructions TextView.
     * @param text new text
     */
    private fun setInstructions(text : String){
        narratorText.text = text
    }

    /**
     * Update the text of turn TextView with round.
     */
    private fun setTurn(){
        turn.text = round.toString()
    }

    /**
     * Update primary ability icon.
     * @param icon new icon
     */
    private fun setPrimaryIcon(icon : Int){
        abilityOne.setImageResource(icon)
    }

    /**
     * Update secondary ability icon.
     * @param icon new icon
     */
    private fun setSecondaryIcon(icon : Int){
        abilityTwo.setImageResource(icon)
    }

    /**
     * Create a list of ordered turns from the list of players.
     * @param list list of player
     * @return list of turns in order
     */
    private fun createTurns(list : ArrayList<Role>) : ArrayList<Turn<*>> {

        val output = ArrayList<Turn<*>>()

        val servant = ServantTurn(Servant(baseContext),this)
        if (servant.addTurn(output = output, list = list, baseContext)) {
            for (role : Role in playerList){
                if (role.name == getString(R.string.servant_name)){
                    servantRef = role as Servant
                    break
                }
            }
            Log.d("Role","servantRef = ${this.servantRef!!.name} : ${this.servantRef!!.player}")
        }

        val guardian = GuardianTurn(Guardian(baseContext),this)
        guardian.addTurn(output = output, list = list, baseContext)

        val wolfpack = WolfpackTurn(Werewolf(baseContext),this)
        wolfpack.addTurn(output = output, list = list, baseContext)

        val infect = InfectTurn(FatherOfWolves(baseContext),this)
        infect.addTurn(output = output, list = list, baseContext)

        val sorcerer = SorcererTurn(Sorcerer(baseContext),this)
        sorcerer.addTurn(output = output, list = list, baseContext)

        val seer = SeerTurn(Seer(baseContext),this)
        seer.addTurn(output = output, list = list, baseContext)

        val knight = KnightTurn(Knight(baseContext),this)
        knight.addTurn(output = output, list = list, baseContext)

        val barber = BarberTurn(Barber(baseContext), this)
        if (barber.addTurn(output = output, list = list, baseContext)){
            for (role : Role in playerList){
                if (role.name == barber.getRole().name){
                    barberRef = role as Barber
                    break
                }
            }
            barberTurnRef = output[output.size - 1] as BarberTurn
            Log.d("Role","barberRef = ${barberRef!!.name} : ${this.barberRef!!.player}")
        }

        val captain = CaptainTurn(Captain(baseContext), this)
        if (captain.addTurn(output = output, list = list, baseContext)) {
            for (role : Role in playerList){
                if (role.isCaptain){
                    captainRef = role
                    break
                }
            }
            Log.d("Role","captainRef = ${this.captainRef.name} : ${this.captainRef.player}")
        }


        return output
    }

    /**
     * Update the information displayed in the activity.
     */
    fun displayNext(){

        val currentPlayer = turnList[index]

        currentPlayer.debug()

        try {
            setName(currentPlayer.getPlayer(list = playerList))
        }catch (e : Exception){
            Log.d("DEBUG_ROLE","$e")
        }finally {
            currentPlayer.getRole().debug()
        }

        setIcon(currentPlayer.getIcon())

        if (currentPlayer.getPrimaryAbility() != null)
            setPrimaryIcon(currentPlayer.getPrimaryAbility()!!.icon)
        else
            setPrimaryIcon(Icons.noAbility)

        if (currentPlayer.getSecondaryAbility() != null)
            setSecondaryIcon(currentPlayer.getSecondaryAbility()!!.icon)
        else
            setSecondaryIcon(Icons.noAbility)

        setRole(currentPlayer.getRoleToDisplay(context = baseContext, list = playerList))
        setInstructions(currentPlayer.getInstructions(context = baseContext, list = playerList))
        statusEffectAdapter.setList(currentPlayer.getRole().getStatusEffects())

        if (currentPlayer.onStart(this)!!){

            val dialog = UsePowerDialog(
                currentPlayer,
                currentPlayer.getOnStartAbility()!!,
                currentPlayer.getOnStartOnClickHandler(),
                currentPlayer.getOnTargetHandler(),
                null,
                false,
                this
            )

            dialog.show(supportFragmentManager, TAG_ALERT)
            return
        }

        val onDismissed = object : UsePowerDialog.OnDismissed{

            override fun onDismissed() {
                next()
            }

        }

        if (currentPlayer.getPrimaryAbility() != null)
            abilityOne.visibility = View.VISIBLE
        else
            abilityOne.visibility = View.GONE

        abilityOne.setOnClickListener {

            if (currentPlayer.getPrimaryAbility() == null) {
                Log.d("Ability","No Primary Ability")
                return@setOnClickListener
            }

            if (!currentPlayer.getPrimaryAbility()!!.isUsable()){
                Log.d("Ability","Primary is not usable")
                Toast.makeText(baseContext,R.string.cant_use_power,Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (currentPlayer.getPrimaryAbility()!!.times != ABILITY_NONE){
                val dialog = UsePowerDialog(
                    currentPlayer,
                    currentPlayer.getPrimaryAbility()!!,
                    currentPlayer.getOnClickHandler(),
                    currentPlayer.getOnTargetHandler(),
                    null,
                    true,
                    this
                )

                dialog.show(supportFragmentManager, TAG_ALERT)
            }
            else {
                Toast.makeText(baseContext,R.string.no_power,Toast.LENGTH_LONG).show()
            }

        }

        if (currentPlayer.getSecondaryAbility() != null)
            abilityTwo.visibility = View.VISIBLE
        else
            abilityTwo.visibility = View.GONE

        abilityTwo.setOnClickListener {

            if (currentPlayer.getSecondaryAbility() == null)
                return@setOnClickListener

            if (!currentPlayer.getSecondaryAbility()!!.isUsable()){
                Toast.makeText(baseContext,R.string.cant_use_power,Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (currentPlayer.getSecondaryAbility()!!.times != ABILITY_NONE){
                val dialog = UsePowerDialog(
                    currentPlayer,
                    currentPlayer.getSecondaryAbility()!!,
                    currentPlayer.getOnClickHandler(),
                    currentPlayer.getOnTargetHandler(),
                    onDismissed,
                    true,
                    this
                )

                dialog.show(supportFragmentManager, TAG_ALERT)
            }
            else {
                Toast.makeText(baseContext,R.string.no_power,Toast.LENGTH_LONG).show()
            }

        }

        if (currentPlayer.getTertiaryAbility() != null)
            abilityThree.visibility = View.VISIBLE
        else
            abilityThree.visibility = View.GONE

        abilityThree.setOnClickListener {

            if (currentPlayer.getTertiaryAbility() == null)
                return@setOnClickListener

            if (!currentPlayer.getTertiaryAbility()!!.isUsable()){
                Toast.makeText(baseContext,R.string.cant_use_power,Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (currentPlayer.getTertiaryAbility()!!.times != ABILITY_NONE){
                val dialog = UsePowerDialog(
                    currentPlayer,
                    currentPlayer.getTertiaryAbility()!!,
                    currentPlayer.getOnClickHandler(),
                    currentPlayer.getOnTargetHandler(),
                    onDismissed,
                    true,
                    this
                )

                dialog.show(supportFragmentManager, TAG_ALERT)
            }
            else {
                Toast.makeText(baseContext,R.string.no_power,Toast.LENGTH_LONG).show()
            }

        }

    }

    /**
     * Move to the next role.
     * Uses Recursive call.
     */
    fun next(){

        index ++

        if (index == turnList.size){
            morning()
            return
        }

        if (!turnList[index].canPlay(round = round, list = playerList))
            next()
        else
            displayNext()

    }

    fun removeRole(role : Role): Boolean{
        deadList.add(role)
        return playerList.remove(role)
    }

    fun removeTurn(turn : Turn<*>): Boolean{
        return turnList.remove(turn)
    }

    /**
     * function used to initialize the discussion in the morning.
     */
    private fun resolve(){

        for (role : Role in playerList){
            if (role.isTalking){
                events.add(Event.talkFirst(this,role.player!!))
                break
            }
        }

        for (turn : Turn<*> in turnList){
            if (turn.getRole().name == getString(R.string.seer_name)){
                val seer = turn as SeerTurn
                if (!seer.getRole().isKilled)
                    events.add(Event.seen(this, seer.getRole().getSeenRole()!!))
            }

            if (turn.getRole().isKilled){
                if (turn.getRole().isServed)
                    turn.servant(this, events)
            }

        }

        var i = 0
        while (i < turnList.size){
            if (turnList[i].getRole().isKilled && !turnList[i].getRole().isServed){
                turnList.removeAt(i)
                i--
            }

            i++
        }

        i=0
        while (i < playerList.size){
            playerList[i].resetStatusEffects()

            if (playerList[i].isKilled){
                if (playerList[i].isServed){
                    playerList[i].servant(this)
                }
                events.add(Event.died(this, playerList[i].player!!))
                deadList.add(playerList[i])
                playerList.removeAt(i)
                i--
            }

            i++
        }

    }

    /**
     * Start a new round.
     * * Should be used only after dismissing every dialog.
     */
    private fun newRound(){

        val onClick = object : AlertDialog.OnClick{
            override fun onClick(alertDialog: AlertDialog) {
                alertDialog.dismiss()
                events.clear()
                index = -1
                next()
            }
        }

        val dialog = AlertDialog(text = R.string.good_night, rightButton = onClick)
        dialog.show(supportFragmentManager, TAG_ALERT)

    }

    /**
     * Start the after round process.
     * * Display event dialog.
     * * Display discussion dialog.
     * * Display voting dialog. (in case of equality of votes, another discussion between the
     *   affected players (who have equal votes) should take place. In case of equal voting for more than 2 players,
     *   the process is repeated but with a free for all form of discussion : no captain needed to choose
     *   which player should start talking first.
     * * Display player defending dialog.
     * * Display execution dialog : Players should vote who should be executed.
     */
    private fun morning(){

        resolve()
        round ++
        setTurn()
        wolfTargets.clear()

        val onClick = object : EventsDialog.OnClick{
            override fun onClick(): Boolean {

                val onVote = object : DiscussionDialog.OnNext{
                    override fun onNext(): Boolean {

                        val onVoting = object : VotingAdapter.OnVote{
                            override fun onIncrement(adapter: VotingAdapter, position: Int) {
                                adapter.addVote(position)
                            }

                            override fun onDecrement(adapter: VotingAdapter, position: Int) {
                                adapter.decrementVote(position)
                            }

                        }

                        val onCast = object : VotingDialog.OnVoteCast {
                            override fun onVoteCast(dialog: VotingDialog) {

                                dialog.dismiss()
                                val list = voteResult(dialog.adapter.list, dialog.adapter.votes)

                                when {
                                    list.isEmpty() -> {
                                        newRound()
                                    }
                                    list.size == 1 -> {
                                        defendSolo(list)
                                    }
                                    list.size in 2..3 -> {
                                        threePlusVoted(list)
                                    }
                                    list.size >= 4 -> {

                                    }
                                }
                            }

                        }

                        vote(
                            playerList,
                            playerList.size,
                            "Vote Suspicious Players",
                            "Vote Suspicious Players",
                            onVoting,
                            barberTurnRef!!.onCall(),
                            onCast
                        )

                        return true
                    }

                }

                discussion(playerList,onVote)
                return true
            }
        }

        val eventDialog = EventsDialog(events = events,onClick = onClick,cancelable = false)
        eventDialog.show(supportFragmentManager,TAG_ALERT)

    }

    /**
     * Open a discussion dialog.
     */
    private fun discussion(list : ArrayList<Role>, onNext: DiscussionDialog.OnNext){

        val dialog = DiscussionDialog( list, this, onNext = onNext)
        dialog.show(supportFragmentManager,TAG_ALERT)

    }

    /**
     * Open a voting dialog.
     * @param list list of players to be voted on.
     * @param title title of the dialog.
     * @param content description of the dialog.
     * @param onVote on vote handler. See [VotingAdapter.OnVote]
     * @param onBarberCall on barber call handler. See [OnCall]
     * @param onCast on vote casting handler. See [VotingDialog.OnVoteCast]
     */
    private fun vote(
        list : ArrayList<Role>,
        voters : Int,
        title : String,
        content : String,
        onVote : VotingAdapter.OnVote,
        onBarberCall : OnCall,
        onCast : VotingDialog.OnVoteCast){

        val dialog = VotingDialog(
            gameActivity = this,
            list = list,
            voters = voters,
            title = title,
            text = content,
            onBarberCall = onBarberCall,
            onVoteCast = onCast,
        )

        dialog.show(supportFragmentManager, TAG_ALERT)

    }

    /**
     * Returns a list of the most voted players in a voting poll.
     * @param list playerList
     * @param votes vote count.
     * @return return a list of the most voted players.
     */
    private fun voteResult(list : ArrayList<Role>, votes : ArrayList<Int>): ArrayList<Role>{

        val output = ArrayList<Role>()

        var max = 1
        for (i : Int in votes){
            if (i > max){
                max = i
            }
        }

        for (x in 0 until list.size){
            if (votes[x] == max)
                output.add(list[x])
        }

        return output
    }

    private fun defendSolo(list: ArrayList<Role>){

        val onVoteCast = object  : VotingDialog.OnVoteCast{
            override fun onVoteCast(dialog: VotingDialog) {

                dialog.dismiss()

                val done = object : OnAction.OnDone{
                    override fun onDone(onAction: OnAction) {
                        newRound()
                    }
                }
                val action = OnAction(this@GameActivity, done)

                if (dialog.adapter.votes[0] < 0){
                    newRound()
                }

                if (dialog.adapter.votes[0] == 0){

                    val yes = object : AlertDialog.OnClick{
                        override fun onClick(alertDialog: AlertDialog) {
                            alertDialog.dismiss()
                            dialog.adapter.list[0].kill(playerList)
                            action.onStart()
                        }
                    }

                    val no = object : AlertDialog.OnClick{
                        override fun onClick(alertDialog: AlertDialog) {
                            alertDialog.dismiss()
                            newRound()
                        }
                    }

                    val captainChoice = AlertDialog(
                        text = R.string.captain_execute,
                        icon = Icons.info,
                        rightButton = yes,
                        rightButtonText = R.string.yes,
                        leftButton = no,
                        leftButtonText = R.string.no)
                    captainChoice.show(supportFragmentManager, TAG_ALERT)
                }

                if (dialog.adapter.votes[0] > 0){
                    dialog.adapter.list[0].kill(playerList)
                    action.onStart()
                }
            }
        }

        val onNext = object : DiscussionDialog.OnNext{
            override fun onNext(): Boolean {
                val dialog = VotingDialog(
                    gameActivity = this@GameActivity,
                    list = list,
                    voters = playerList.size - list.size,
                    title = getString(R.string.execute),
                    text = "${getString(R.string.execute)} ${list[0].player} ?",
                    onVoteCast = onVoteCast,
                    onBarberCall = barberTurnRef!!.onCall(),
                    execution = true
                )
                dialog.show(supportFragmentManager, TAG_ALERT)
                return true
            }
        }

        val discuss = DiscussionDialog(
            playerList = list,
            gameActivity = this,
            display = "${list[0].player} ${getString(R.string.discussion_description_single)}",
            onNext = onNext,
            cancelable = false
        )
        discuss.show(supportFragmentManager, TAG_ALERT)

    }

    private fun threePlusVoted(list : ArrayList<Role>){
    }

}