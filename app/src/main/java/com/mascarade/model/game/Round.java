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

    private final static String ROUND = "ROUND";

    public Round(int nbRound, PlateauMascarade boardMascarade, Bank bank, Tribunal tribunal){
        this.nbRound = nbRound;
        this.boardMascarade = boardMascarade;
        this.bank = bank;
        this.tribunal = tribunal;
    }

    public void activeChangeCardButton(){
        Button changeCard = (Button)boardMascarade.findViewById(R.id.button_changeCard);
        changeCard.setOnClickListener(new ButtonChangeCardOnClickListener(changeCard));
    }

    public void activeSeeCardButton(){
        Button seeCardButton = (Button)boardMascarade.findViewById(R.id.button_seeCard);
        seeCardButton.setOnClickListener(new ButtonSeeCardOnClickListener(seeCardButton));
    }

    public void activeAnnounceCardButton(){
        Button announceCardButton = (Button)boardMascarade.findViewById(R.id.button_announceCard);
        announceCardButton.setOnClickListener(new ButtonAnnounceCardOnClickListener(announceCardButton, boardMascarade));

    }

    public void hideCard(Card playerCard, String idCard, Activity boardMascarade){

        String idStringImageView = "imageViewCard_" + playerCard.getTypeCard() + "_" + idCard;
        Log.d(ROUND, "idStringImageViewCard : " + idStringImageView);
        ImageView cardView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
        Log.d(ROUND, "cardViewTag : " + cardView.getTag());
        cardView.setImageResource(R.drawable.card_darkside);

    }

    class ButtonChangeCardOnClickListener implements View.OnClickListener{

        private final Button buttonChangeCard;

        ButtonChangeCardOnClickListener(Button button){
            this.buttonChangeCard = button;
        }
        @Override
        public void onClick(View v){

        }
    }

    class ButtonAnnounceCardOnClickListener implements View.OnClickListener{

        private final Button buttonAnnounceCard;
        private final PlateauMascarade boardMascarade;

        ButtonAnnounceCardOnClickListener(Button buttonAnnounceCard, PlateauMascarade boardMascarade){
            this.buttonAnnounceCard = buttonAnnounceCard;
            this.boardMascarade = boardMascarade;
        }
        @Override
        public void onClick(View v){

            final TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
            textViewInstruction.setText("Vous souhaitez activer le pouvoir de votre carte.");

            ArrayList<Card> listCards = bank.getBankCardsListStart();
            ArrayList<String> listCardsType = new ArrayList<>();
            for(int i = 0; i < listCards.size(); i++){
                String cardType = listCards.get(i).getTypeCard();
                listCardsType.add(cardType);
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

                    if(announcementIsCorrect){
                        activePowerPlayer(mainPlayer);
                    }

                }

            });

            AlertDialog roleCardChoicePlayerAlert = roleCardChoicePlayerBuilder.create();
            roleCardChoicePlayerAlert.show();
        }
    }

    class ButtonSeeCardOnClickListener implements View.OnClickListener{

        private final Button buttonSeeCard;

        ButtonSeeCardOnClickListener(Button button){
            this.buttonSeeCard = button;
        }
        @Override
        public void onClick(View v){

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
        else if(typePower.equals("Paysan")){
            this.activePowerPaysan(playerConcerned);
        }
        else if(typePower.equals("Reine")){
            this.activePowerReine(playerConcerned);
        }
        else if(typePower.equals("Roi")){
            this.activePowerRoi(playerConcerned);
        }
        else if(typePower.equals("Sorciere")){
            this.activePowerSorciere(playerConcerned);
        } else if(typePower.equals("Tricheur")){
            this.activePowerTricheur(playerConcerned);
        }
        else if(typePower.equals("Veuve")){
            this.activePowerVeuve(playerConcerned);
        }
        else if(typePower.equals("Voleur")){
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

        this.activateListenersCards(playerConcerned);

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

        this.activateListenersCards(playerConcerned);
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

        textViewInstruction.setText("Vous récupérez " + tribunalMoney + " du tribunal.");
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
                        activateListenersCards(playerConcerned);
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
     * Get 3 gold
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

        this.activateListenersCards(playerConcerned);
    }

    /**
     * If player has 10 gold, he win.
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
                String idStringImageView = "imageViewCard_" + playerCardOpponent.getTypeCard() + "_" + idCard;
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
                String idCard = "_center_" + j + 1;
                String idStringImageView = "imageViewCard_" + cardCenter.getTypeCard() + "_" + idCard;

                ImageView cardView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
                cardView.setClickable(false);
                cardView.setEnabled(false);
            }
        }

    }

    public void activateListenersCards(Player mainPlayer){

        ArrayList<Player> playerArrayList = bank.getListPlayers();

        for(int i = 0; i < playerArrayList.size() ; i++){
            Player player = playerArrayList.get(i);
            if(!player.isPlayer()){
                Card playerCardOpponent = playerArrayList.get(i).getCard();
                String idCard = Integer.toString(playerArrayList.get(i).getId());
                String idStringImageView = "imageViewCard_" + playerCardOpponent.getTypeCard() + "_" + idCard;
                ImageView cardView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
                cardView.setOnTouchListener(new OnTouchListenerCard(playerCardOpponent, cardView, mainPlayer));
            }

        }

        int nbPlayer = bank.getNbPlayers();
        if(nbPlayer < 6) {
            ArrayList<Card> cardsCenter = bank.getBankCardsCenter();
            for (int j = 0; j < cardsCenter.size(); j++) {
                Card cardCenter = cardsCenter.get(j);
                String idCard = "_center_" + j + 1;
                String idStringImageView = "imageViewCard_" + cardCenter.getTypeCard() + "_" + idCard;

                ImageView cardView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
                cardView.setOnTouchListener(new OnTouchListenerCard(cardCenter, cardView, mainPlayer));
            }
        }


    }

    class OnTouchListenerCard implements View.OnTouchListener {

        private final Card cardOpponent;
        private final ImageView imageCard;
        private final Player playerConcerned;

        OnTouchListenerCard(Card cardOpponent, ImageView imageCard, Player playerConcerned){
            this.cardOpponent = cardOpponent;
            this.imageCard = imageCard;
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

                Log.d(ROUND, "playerConcernedCard : " + typeCardPlayerConcerned + "  card : " + cardOpponent.getTypeCard());

                if (typeCardPlayerConcerned.equals("Sorciere")) {
                    Player opponentPlayer = bank.getPlayerWithCard(cardOpponent.getTypeCard());
                    textViewInstructions.setText("Vous avez sélectionné le " + opponentPlayer.getName() + " pour échanger votre or.");
                    Sorciere playerConcernedCard = (Sorciere) playerConcerned.getCard();
                    playerConcernedCard.activePower(playerConcerned, opponentPlayer);

                } else if (typeCardPlayerConcerned.equals("Espionne")) {
                    /*Player opponentPlayer = bank.getPlayerWithCard(cardOpponent.getTypeCard());
                    textViewInstructions.setText("Vous avez sélectionné le " + opponentPlayer.getName() + " pour échanger votre or.");
                    Espionne playerConcernedCard = (Espionne)playerConcerned.getCard();
                    playerConcernedCard.setPlayer(playerConcerned);
                    playerConcernedCard.setBoardMascarade(boardMascarade);
                    playerConcernedCard.setOpponent(opponentPlayer);*/
                    //playerConcernedCard.setChangeCards();

                } else if (typeCardPlayerConcerned.equals("Eveque")) {
                    textViewInstructions.setText("carte eveque");
                } else if (typeCardPlayerConcerned.equals("Fou")) {
                    textViewInstructions.setText("carte fou");
                } else if (typeCardPlayerConcerned.equals("Inquisiteur")) {
                    textViewInstructions.setText("carte inquisteur");
                } else if (typeCardPlayerConcerned.equals("Juge")) {

                    textViewInstructions.setText("carte juge");
                    Juge playerConcernedCard = (Juge)playerConcerned.getCard();
                    playerConcernedCard.setConcernedPlayer(playerConcerned);

                } else if (typeCardPlayerConcerned.equals("Paysan")) {
                    textViewInstructions.setText("carte paysan");

                    if (cardOpponent.getTypeCard().equals("Paysan")) {
                        Player partnerPlayer = bank.getPlayerWithCard(cardOpponent.getTypeCard());
                        String idStringImageView = "imageViewCard_" + partnerPlayer.getTypeCard() + "_" + partnerPlayer.getId();
                        ImageView paysanPartnerImageView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
                        paysanPartnerImageView.setImageResource(R.drawable.paysan_card);

                        Paysan playerConcernedCard = (Paysan) playerConcerned.getCard();
                        playerConcernedCard.activePower(playerConcerned, partnerPlayer, true);
                    }


                } else if (typeCardPlayerConcerned.equals("Reine")) {

                    textViewInstructions.setText("carte reine");
                    Reine playerConcernedCard = (Reine)playerConcerned.getCard();
                    playerConcernedCard.setConcernedPlayer(playerConcerned);

                } else if (typeCardPlayerConcerned.equals("Roi")) {

                    textViewInstructions.setText("carte roi");
                    Roi playerConcernedCard = (Roi)playerConcerned.getCard();
                    playerConcernedCard.setConcernedPlayer(playerConcerned);

                } else if (typeCardPlayerConcerned.equals("Tricheur")) {

                    textViewInstructions.setText("carte tricheur");

                } else if (typeCardPlayerConcerned.equals("Veuve")) {
                    textViewInstructions.setText("carte veuve");
                }

            }
            return true;
        }
    }


}
