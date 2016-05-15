package com.mascarade.model.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mascarade.R;
import com.mascarade.model.activities.PlateauMascarade;
import com.mascarade.model.cards.Card;
import com.mascarade.model.cards.Espionne;
import com.mascarade.model.cards.Eveque;
import com.mascarade.model.cards.Juge;
import com.mascarade.model.cards.Paysan;
import com.mascarade.model.cards.Reine;
import com.mascarade.model.cards.Roi;
import com.mascarade.model.cards.Sorciere;
import com.mascarade.model.cards.Tricheur;
import com.mascarade.model.cards.Veuve;
import com.mascarade.model.cards.Voleur;

import java.util.ArrayList;

/**
 * Created by melanie on 4/3/16.
 */
public class Round {

    private int nbRound = 0;
    private PlateauMascarade boardMascarade;
    private Bank bank;
    private Tribunal tribunal;
    private String actionChoosen = "";
    private Player opponentPlayer;
    private Game game;

    private final static String ROUND = "ROUND";

    public Round(int nbRound, PlateauMascarade boardMascarade, Bank bank, Tribunal tribunal, Game game){
        this.nbRound = nbRound;
        this.boardMascarade = boardMascarade;
        this.bank = bank;
        this.tribunal = tribunal;
        this.game = game;
    }

    public void activeChangeCardButton(){
        this.setEnableClickableButtonChangeCards(true);
        Button buttonChangeCard = (Button)boardMascarade.findViewById(R.id.button_changeCard);
        buttonChangeCard.setOnTouchListener(new ButtonChangeCardOnClickListener());
    }

    public void activeSeeCardButton(){
        this.setEnableClickableButtonSeeCard(true);
        Button seeCardButton = (Button)boardMascarade.findViewById(R.id.button_seeCard);
        seeCardButton.setOnTouchListener(new ButtonSeeCardOnClickListener(seeCardButton, bank.getMainPlayer()));
    }

    public void activeAnnounceCardButton(){
        this.setEnableClickableButtonAnnounceCard(true);
        Button announceCardButton = (Button)boardMascarade.findViewById(R.id.button_announceCard);
        announceCardButton.setOnTouchListener(new ButtonAnnounceCardOnClickListener(announceCardButton, boardMascarade));

    }

    public void activeFinallyRoundButton(Player opponentPlayer){
        this.setEnableClickableButtonFinishRound(true);
        Button finallyRoundButton = (Button)boardMascarade.findViewById(R.id.button_finally_round);
        finallyRoundButton.setOnTouchListener(new ButtonFinallyRoundOnClickListener(opponentPlayer));
    }

    class ButtonFinallyRoundOnClickListener implements View.OnTouchListener{
        Player opponentPlayer;
        ButtonFinallyRoundOnClickListener(Player opponentPlayer){
            this.opponentPlayer = opponentPlayer;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            game.finishRound(getActionChoosen(), tribunal);
            setEnableClickableButtonFinishRound(false);
            return false;
        }
    }

    class ButtonChangeCardOnClickListener implements View.OnTouchListener{

        ButtonChangeCardOnClickListener(){

        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {

            setEnableClickableButtonAnnounceCard(false);
            setEnableClickableButtonChangeCards(false);
            setEnableClickableButtonSeeCard(false);
            setEnableClickableButtonFinishRound(false);

            TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
            textViewInstruction.setText("Vous souhaitez échanger votre carte avec un autre joueur.");
            Player mainPlayer = bank.getMainPlayer();
            activateListenersCards(mainPlayer, "change");

            setActionChoosen("change");

            activeFinallyRoundButton(null);

            return false;
        }
    }

    class ButtonAnnounceCardOnClickListener implements View.OnTouchListener{

        private final Button buttonAnnounceCard;
        private final PlateauMascarade boardMascarade;
        private static final boolean cardChange = false;

        ButtonAnnounceCardOnClickListener(Button buttonAnnounceCard, PlateauMascarade boardMascarade){
            this.buttonAnnounceCard = buttonAnnounceCard;
            this.boardMascarade = boardMascarade;
        }
        @Override
        public boolean onTouch(View v, MotionEvent event){

            setEnableClickableButtonAnnounceCard(false);
            setEnableClickableButtonChangeCards(false);
            setEnableClickableButtonSeeCard(false);
            setEnableClickableButtonFinishRound(false);

            setActionChoosen("power");

            final TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
            textViewInstruction.setText("Vous souhaitez activer le pouvoir de votre carte.");

            ArrayList<Card> listCards = bank.getBankCardsListStart();
            ArrayList<String> listCardsType = new ArrayList<>();
            for(int i = 0; i < listCards.size(); i++){
                String cardType = listCards.get(i).getTypeCard();
                if(!listCardsType.contains(cardType)){
                    listCardsType.add(cardType);
                }

            }
            final CharSequence[] charSequenceCards = listCardsType.toArray(new CharSequence[listCardsType.size()]);
            final Player mainPlayer = bank.getMainPlayer();

            AlertDialog.Builder roleCardChoicePlayerBuilder = new AlertDialog.Builder(boardMascarade);
            roleCardChoicePlayerBuilder.setTitle("Quel carte êtes vous ?");
            roleCardChoicePlayerBuilder.setItems(charSequenceCards, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // The 'which' argument contains the index position
                    // of the selected item
                    final String cardChosen = charSequenceCards[which].toString();
                    boolean announcementIsCorrect = mainPlayer.announceCard(cardChosen, tribunal);

                    if (announcementIsCorrect) {
                        activePowerPlayer(mainPlayer);
                    }
                    activeFinallyRoundButton(null);


                }

            });

            AlertDialog roleCardChoicePlayerAlert = roleCardChoicePlayerBuilder.create();
            roleCardChoicePlayerAlert.show();

            return false;
        }
    }

    class ButtonSeeCardOnClickListener implements View.OnTouchListener{

        private final Button buttonSeeCard;
        private Player playerConcerned;

        ButtonSeeCardOnClickListener(Button button, Player playerConcerned){
            this.buttonSeeCard = button;
            this.playerConcerned = playerConcerned;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            setEnableClickableButtonAnnounceCard(false);
            setEnableClickableButtonChangeCards(false);
            setEnableClickableButtonSeeCard(false);
            setEnableClickableButtonFinishRound(false);

            setActionChoosen("see");

            TextView textViewInstructions = (TextView) boardMascarade.findViewById(R.id.textView_instructions);

            final Card cardPlayerConcerned = playerConcerned.getCard();
            final String idCard = Integer.toString(playerConcerned.getId());
            cardPlayerConcerned.showCard(idCard, boardMascarade);

            activeFinallyRoundButton(null);

            return false;
        }
    }



    public void activePowerPlayer(Player playerConcerned){

        String typePower = playerConcerned.getCardType();


        if(typePower.equals("Espionne")){
            this.activePowerEspione(playerConcerned);
        }
        else if(typePower.equals("Eveque")){
            this.activePowerEveque(playerConcerned);
        }
        else if(typePower.equals("Fou")){
            this.activePowerFou(playerConcerned);
        }
        else if(typePower.equals("Inquisiteur")){
        }
        else if(typePower.equals("Juge")){
            this.activePowerJuge(playerConcerned);
        }
        else if(typePower.equals("Paysan")) {
            this.activePowerPaysan(playerConcerned);
        }
        else if(typePower.equals("Reine")) {
            this.activePowerReine(playerConcerned);
        }
        else if(typePower.equals("Roi")){
            this.activePowerRoi(playerConcerned);
        }
        else if(typePower.equals("Sorciere")) {
            this.activePowerSorciere(playerConcerned);
        }
        else if(typePower.equals("Tricheur")) {
            this.activePowerTricheur(playerConcerned);
        }
        else if(typePower.equals("Veuve")) {
            this.activePowerVeuve(playerConcerned);
        }
        else if(typePower.equals("Voleur")) {
            this.activePowerVoleur(playerConcerned);
        }
    }

    /**
     * See her card and another player and decide if she wants to change both cards.
     *
     * @param playerConcerned
     */
    public void activePowerEspione(Player playerConcerned){
        TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
        textViewInstruction.setText("Choisissez la carte du joueur que vous souhaitez voir.");

        this.activateListenersCards(playerConcerned, "power");

    }

    /**
     * Get 2 gold from the richest player
     *
     * @param playerConcerned
     */
    public void activePowerEveque(Player playerConcerned){
        TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
        Eveque evequeCardPlayerConcerned = (Eveque)playerConcerned.getCard();
        String nameRichestPlayer = evequeCardPlayerConcerned.activePower(playerConcerned, bank).getName();

        int nbMoneyRetrieved = evequeCardPlayerConcerned.getNbMoneyRetrieved();

        textViewInstruction.setText("Vous prenez " + nbMoneyRetrieved + " gold à " + nameRichestPlayer);

    }

    /**
     *
     * @param playerConcerned
     */
    public void activePowerFou(Player playerConcerned){
        TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
        textViewInstruction.setText("Vous recevez une pièce d'or de la banque.\nChoisissez deux cartes que vous souhaiter échanger ou non.");

        this.activateListenersCards(playerConcerned, "power");
    }

    public void activePowerInquisiteur(Player playerConcerned){

    }

    /**
     * Retrieve all gold from 'tribunal'
     *
     * @param playerConcerned
     */
    public void activePowerJuge(Player playerConcerned){
        TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
        Juge jugeCardPlayerConcerned = (Juge)playerConcerned.getCard();

        int tribunalMoney = tribunal.getNbMoney();

        jugeCardPlayerConcerned.activePower(playerConcerned, tribunal);

        textViewInstruction.setText("Vous récupérez " + tribunalMoney + " or du tribunal.");
    }

    /**
     * Get 1 gold from bank
     * and 2 if player can find the other paysan.
     * The other paysan get 2 gold too.
     *
     * @param playerConcerned
     */
    public void activePowerPaysan(final Player playerConcerned) {
        TextView textViewInstruction = (TextView) boardMascarade.findViewById(R.id.textView_instructions);
        final Paysan paysanCardPlayerConcerned = (Paysan) playerConcerned.getCard();
        textViewInstruction.setText("Trouvez l'autre paysan !");

        AlertDialog.Builder builder = new AlertDialog.Builder(boardMascarade);
        builder.setTitle("Vous êtes un paysan");
        builder.setMessage("Voulez vous tenter de trouver l'autre paysan ?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        activateListenersCards(playerConcerned, "power");
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        paysanCardPlayerConcerned.activePower(playerConcerned, null, false);
                    }
                });
        builder.create();
        builder.show();

    }

    /**
     * Get 2 gold from bank.
     *
     * @param playerConcerned
     */
    public void activePowerReine(Player playerConcerned){
        TextView textViewInstruction = (TextView) boardMascarade.findViewById(R.id.textView_instructions);
        //Log.d(ROUND, "player reine : " + playerConcerned.getName());
        Reine reineCardPlayerConcerned = (Reine)playerConcerned.getCard();
        //reineCardPlayerConcerned.setPlayer(playerConcerned);
        Log.d(ROUND, "reine player : " + reineCardPlayerConcerned.getPlayer().getName());
        reineCardPlayerConcerned.activePower(playerConcerned);
        textViewInstruction.setText("Vous gagnez 2 pièces d'or.");
    }

    /**
     * Get 3 gold from bank.
     *
     * @param playerConcerned
     */
    public void activePowerRoi(Player playerConcerned){
        TextView textViewInstruction = (TextView) boardMascarade.findViewById(R.id.textView_instructions);
        Roi roiCardPlayerConcerned = (Roi)playerConcerned.getCard();
        Log.d(ROUND, "roi player : " + roiCardPlayerConcerned.getPlayer().getName());
        roiCardPlayerConcerned.activePower(playerConcerned);
        textViewInstruction.setText("Vous gagnez 3 pièces d'or.");
    }

    /**
     * Exchange gold with another player
     *
     * @param playerConcerned
     */
    public void activePowerSorciere(Player playerConcerned){
        TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
        textViewInstruction.setText("Choisissez la carte du joueur avec qui vous souhaitez échanger votre or.");

        this.activateListenersCards(playerConcerned, "power");
    }

    /**
     * If player has 10 gold, he wins.
     *
     * @param concernedPlayer
     */
    public void activePowerTricheur(Player concernedPlayer){
        TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);

        Tricheur tricheurCardPlayer = (Tricheur)concernedPlayer.getCard();
        boolean isWinner = tricheurCardPlayer.activePower(concernedPlayer, bank);

        if(isWinner) {
            Player playerWinner = bank.getWinner();

            if (playerWinner.equals(concernedPlayer)) {
                textViewInstruction.setText("Vous activez le pouvoir du Tricheur.\n" +
                        "Le gagnant est " + concernedPlayer.getName());
            }
        }
        else{
            textViewInstruction.setText("Vous n'avez pas assez d'argent. Le pouvoir est sans effet.");
        }
    }

    /**
     * The total gold of the player is 10.
     *
     * @param playerConcerned
     */
    public void activePowerVeuve(Player playerConcerned){
        TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
        Veuve veuveCardPlayer = (Veuve)playerConcerned.getCard();

        veuveCardPlayer.activePower(playerConcerned);

        textViewInstruction.setText("Vous avez un total de 10 pièces d'or.");
    }

    /**
     *
     * He takes 1 gold from left player and 1 gold from right player.
     *
     * @param playerConcerned
     */
    public void activePowerVoleur(Player playerConcerned){
        TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
        textViewInstruction.setText("Vous activez le pouvoir du voleur.");

        Voleur voleurCardPlayer = (Voleur)playerConcerned.getCard();
        voleurCardPlayer.activePower(playerConcerned, bank);

    }

    public void desactivateListenersCards(Player mainPlayer){

        ArrayList<Player> playerArrayList = bank.getListPlayers();

        for(int i = 0; i < playerArrayList.size() ; i++) {
            Player player = playerArrayList.get(i);
            if (!player.isPlayer()) {
                Card playerCardOpponent = playerArrayList.get(i).getCard();
                String idCard = Integer.toString(playerArrayList.get(i).getId());
                String idStringImageView = "imageViewCard_" + idCard;
                ImageView cardView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
                cardView.setClickable(false);
                cardView.setEnabled(false);
            }
        }

        int nbPlayer = bank.getNbPlayers();
        if(nbPlayer < 6) {
            ArrayList<Card> cardsCenter = bank.getBankCardsCenter();
            for (int j = 0; j < cardsCenter.size(); j++) {
                Card cardCenter = cardsCenter.get(j);
                String idCard = "center_" + j + 1;
                String idStringImageView = "imageViewCard_" + idCard;

                ImageView cardView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
                cardView.setClickable(false);
                cardView.setEnabled(false);
            }
        }

    }

    public void activateListenersCards(Player mainPlayer, String action){

        ArrayList<Player> playerArrayList = bank.getListPlayers();

        for(int i = 0; i < playerArrayList.size() ; i++){
            Player player = playerArrayList.get(i);
            if(!player.isPlayer()){
                Player opponentPlayer = playerArrayList.get(i);
                Card playerCardOpponent = opponentPlayer.getCard();
                String idCard = Integer.toString(playerArrayList.get(i).getId());
                String idStringImageView = "imageViewCard_" + idCard;
                ImageView cardView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
                Log.d(ROUND, "mainPlayer : " + mainPlayer.getId());
                cardView.setClickable(true);
                cardView.setEnabled(true);
                if(action.equals("power")){
                    cardView.setOnTouchListener(new OnTouchListenerCardPower(playerCardOpponent, mainPlayer));
                }
                else if(action.equals("change")){
                    cardView.setOnTouchListener(new OnTouchListenerCardChange(opponentPlayer, mainPlayer));
                }

            }

        }

        int nbPlayer = bank.getNbPlayers();
        if(nbPlayer < 6) {
            ArrayList<Card> cardsCenter = bank.getBankCardsCenter();
            for (int j = 0; j < cardsCenter.size(); j++) {
                Card cardCenter = cardsCenter.get(j);
                String idCard = "center_" + j + 1;
                String idStringImageView = "imageViewCard_" + idCard;

                ImageView cardView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
                cardView.setOnTouchListener(new OnTouchListenerCardPower(cardCenter, mainPlayer));
            }
        }


    }

    class OnTouchListenerCardChange implements View.OnTouchListener{

        private Player opponentPlayer;
        private Player playerConcerned;

        OnTouchListenerCardChange(Player opponentPlayer, Player playerConcerned){
            this.opponentPlayer = opponentPlayer;
            this.playerConcerned = playerConcerned;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();

            if (action == MotionEvent.ACTION_DOWN) {
                TextView textViewInstructions = (TextView) boardMascarade.findViewById(R.id.textView_instructions);

                Log.d(ROUND, "cardOpponent : " + opponentPlayer.getTypeCard());

                AlertDialog.Builder builderChangeCards = new AlertDialog.Builder(boardMascarade);
                builderChangeCards.setTitle("Souhaitez vous échanger votre carte avec un autre joueur ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
                                textViewInstruction.setText("Vous avez échangé vos cartes.");
                                playerConcerned.swapCards(opponentPlayer, true);
                                desactivateListenersCards(playerConcerned);
                                setEnableClickableButtonChangeCards(false);
                                activeFinallyRoundButton(opponentPlayer);
                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
                                textViewInstruction.setText("Vous n'avez pas échangé vos cartes/");
                                desactivateListenersCards(playerConcerned);
                                setEnableClickableButtonChangeCards(false);
                                activeFinallyRoundButton(opponentPlayer);

                            }
                        });
                builderChangeCards.create();
                builderChangeCards.show();

            }
            return true;
        }
    }


    class OnTouchListenerCardPower implements View.OnTouchListener {

        private Card cardOpponent;
        private Player playerConcerned;

        OnTouchListenerCardPower(Card cardOpponent, Player playerConcerned){
            this.cardOpponent = cardOpponent;
            this.playerConcerned = playerConcerned;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if(action == MotionEvent.ACTION_BUTTON_RELEASE){
                Log.d(ROUND, "ACTION_BUTTON_RELEASE");
            }
            if (action == MotionEvent.ACTION_DOWN) {
                String typeCardPlayerConcerned = playerConcerned.getCardType();

                TextView textViewInstructions = (TextView) boardMascarade.findViewById(R.id.textView_instructions);

                Log.d(ROUND, "playerConcernedCard : " + playerConcerned.getName() + "  card : " + playerConcerned.getTypeCard() + " id : " + playerConcerned.getId());
                Log.d(ROUND, "playerOpponentCard : " + cardOpponent.getPlayer().getName() + " card : " + cardOpponent.getTypeCard() + " id : " + cardOpponent.getPlayer().getId());

                if (typeCardPlayerConcerned.equals("Espionne")) {
                    final Player opponentPlayer = bank.getPlayerWithCard(cardOpponent);
                    setOpponentPlayer(opponentPlayer);

                    textViewInstructions.setText("Le " + opponentPlayer.getName() + " est un(e) " + cardOpponent.getTypeCard() + ".");
                    final Espionne playerConcernedCard = (Espionne)playerConcerned.getCard();
                    desactivateListenersCards(playerConcerned);

                    final String idConcernedPlayer = Integer.toString(playerConcerned.getId());

                    final String idOpponentPlayer = Integer.toString(opponentPlayer.getId());
                    cardOpponent.showCard(idOpponentPlayer, boardMascarade);

                    AlertDialog.Builder builder = new AlertDialog.Builder(boardMascarade);
                    builder.setTitle("Vous êtes une espionne");
                    builder.setMessage("Voulez vous échanger votre carte avec votre adversaire ( "+ cardOpponent.getTypeCard() + ") ?")
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    playerConcernedCard.activePower(playerConcerned, opponentPlayer, true);

                                }
                            })
                            .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    playerConcernedCard.activePower(playerConcerned, opponentPlayer, false);

                                }
                            });
                    builder.create();
                    builder.show();




                } else if (typeCardPlayerConcerned.equals("Eveque")) {

                    textViewInstructions.setText("carte eveque : pas de carte à sélectionner");
                    desactivateListenersCards(playerConcerned);

                } else if (typeCardPlayerConcerned.equals("Fou")) {
                    // need to select 2 cards
                    textViewInstructions.setText("carte fou : sélectionnez 2 joueurs");

                } else if (typeCardPlayerConcerned.equals("Inquisiteur")) {
                    Player opponentPlayer = bank.getPlayerWithCard(cardOpponent);
                    setOpponentPlayer(opponentPlayer);
                    textViewInstructions.setText("Vous avez sélectionné le " + opponentPlayer.getName() + ". Il doit annoncer son personnage");

                } else if (typeCardPlayerConcerned.equals("Juge")) {

                    textViewInstructions.setText("carte juge :  pas de carte à sélectionner");
                    desactivateListenersCards(playerConcerned);

                } else if (typeCardPlayerConcerned.equals("Paysan")) {

                    if (cardOpponent.getTypeCard().equals("Paysan")) {


                        Player partnerPlayer = bank.getPlayerWithCard(cardOpponent);
                        String idStringImageView = "imageViewCard_" + partnerPlayer.getId();
                        ImageView paysanPartnerImageView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
                        paysanPartnerImageView.setImageResource(R.drawable.paysan_card);

                        desactivateListenersCards(playerConcerned);

                        Paysan playerConcernedCard = (Paysan) playerConcerned.getCard();
                        playerConcernedCard.activePower(playerConcerned, partnerPlayer, true);
                    }


                } else if (typeCardPlayerConcerned.equals("Reine")) {

                    textViewInstructions.setText("carte reine : pas de carte à sélectionner");
                    desactivateListenersCards(playerConcerned);

                } else if (typeCardPlayerConcerned.equals("Roi")) {

                    textViewInstructions.setText("carte roi: pas de carte à sélectionner");
                    desactivateListenersCards(playerConcerned);

                } else if (typeCardPlayerConcerned.equals("Sorciere")) {
                    Player opponentPlayer = bank.getPlayerWithCard(cardOpponent);
                    setOpponentPlayer(opponentPlayer);

                    textViewInstructions.setText("Vous avez sélectionné le " + opponentPlayer.getName() + " pour échanger votre or.");
                    Sorciere playerConcernedCard = (Sorciere) playerConcerned.getCard();
                    playerConcernedCard.activePower(playerConcerned, opponentPlayer);

                    desactivateListenersCards(playerConcerned);

                } else if (typeCardPlayerConcerned.equals("Tricheur")) {

                    textViewInstructions.setText("carte tricheur: pas de carte à sélectionner");
                    desactivateListenersCards(playerConcerned);

                } else if (typeCardPlayerConcerned.equals("Veuve")) {
                    textViewInstructions.setText("carte veuve: pas de carte à sélectionner");
                    desactivateListenersCards(playerConcerned);
                }
                else if(typeCardPlayerConcerned.equals("Voleur")){
                    textViewInstructions.setText("carte voleur: pas de carte à sélectionner");
                    desactivateListenersCards(playerConcerned);

                }

            }
            return true;
        }
    }



    public void setEnableClickableButtonChangeCards(boolean enableClickable){
        Button buttonChangeCard = (Button)boardMascarade.findViewById(R.id.button_changeCard);
        buttonChangeCard.setClickable(enableClickable);
        buttonChangeCard.setEnabled(enableClickable);
    }

    public void setEnableClickableButtonSeeCard(boolean enableClickable){
        Button buttonSeeCard = (Button)boardMascarade.findViewById(R.id.button_seeCard);
        buttonSeeCard.setClickable(enableClickable);
        buttonSeeCard.setEnabled(enableClickable);
    }

    public void setEnableClickableButtonAnnounceCard(boolean enableClickable){
        Button buttonAnnounceCard = (Button)boardMascarade.findViewById(R.id.button_announceCard);
        buttonAnnounceCard.setClickable(enableClickable);
        buttonAnnounceCard.setEnabled(enableClickable);
    }

    public void setEnableClickableButtonFinishRound(boolean enableClickable){
        Button buttonFinishRound = (Button)boardMascarade.findViewById(R.id.button_finally_round);
        buttonFinishRound.setClickable(enableClickable);
        buttonFinishRound.setEnabled(enableClickable);
    }

    public int getNbRound() {
        return nbRound;
    }

    public void setNbRound(int nbRound) {
        this.nbRound = nbRound;
        TextView textViewRound = (TextView)boardMascarade.findViewById(R.id.textView_round);
        textViewRound.setText(Integer.toString(nbRound));
    }

    public PlateauMascarade getBoardMascarade() {
        return boardMascarade;
    }

    public void setBoardMascarade(PlateauMascarade boardMascarade) {
        this.boardMascarade = boardMascarade;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Tribunal getTribunal() {
        return tribunal;
    }

    public void setTribunal(Tribunal tribunal) {
        this.tribunal = tribunal;
    }

    public String getActionChoosen() {
        return actionChoosen;
    }

    public void setActionChoosen(String actionChoosen) {
        this.actionChoosen = actionChoosen;
    }

    public Player getOpponentPlayer() {
        return opponentPlayer;
    }

    public void setOpponentPlayer(Player opponentPlayer) {
        this.opponentPlayer = opponentPlayer;
    }
}
