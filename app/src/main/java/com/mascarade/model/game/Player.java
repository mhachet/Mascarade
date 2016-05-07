package com.mascarade.model.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mascarade.R;
import com.mascarade.model.cards.Card;

/**
 * Created by melanie on 13/03/16.
 */
public class Player {

    private Activity boardMascarade;
    private int nbMoney = 6;
    private Card card;
    private String cardType = "";
    private int id = 0;
    private boolean powerAvailable = false;
    private String name = "";
    private boolean isPlayer = false;
    private String lastCardKnown = "inconnu";
    private static final String PLAYER = "PLAYER";

    public Player(int nbMoney, Card card, int id) {
        this.card = card;
        this.nbMoney = nbMoney;
        this.id = id;
    }

    /**
     * First power : to announce its card
     * if correct : card power is activated
     * else : player must pay 1 gold coin
     *
     *
     * @param announcedCard
     * @param tribunal
     * @return
     */
    public boolean announceCard(String announcedCard, final Tribunal tribunal){
        boolean announceCorrect = false;

        String cardPlayer = this.getTypeCard();
        Log.d(PLAYER, "player : " + this.getName());
        TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);

        if(announcedCard.equals(this.getTypeCard())){

            Card cardActived = this.getCard();

            String idMainPlayer = Integer.toString(this.getId());
            Log.d(PLAYER, "good choice " + announcedCard + "  -  " + cardPlayer);
            cardActived.showCard(idMainPlayer, boardMascarade);

            cardActived.setBoardMascarade(boardMascarade);
            cardActived.setPlayer(this);

            textViewInstruction.setText("Quelle mémoire ! ");
            //cardActived.activePower();

            announceCorrect = true;

        } else {
            textViewInstruction.setText("Mauvais choix ! Vous n'êtes pas un(e) " + announcedCard);
            final Player player = this;
            Log.d(PLAYER, "bad choice " + announcedCard + "  -  " + cardPlayer);
            AlertDialog.Builder builder = new AlertDialog.Builder(boardMascarade);
            builder.setTitle("Vous vous êtes trompé de rôle");
            builder.setMessage("Vous donnez 1 pièce d'or au Tribunal")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            boolean transaction = tribunal.getMoneyFromPlayer(player);
                            Log.d(PLAYER, "player has enough gold to give to the tribunal : " + transaction);
                            // give 1 gold to the "Tribunal"
                        }
                    });
            builder.create();
            builder.show();
            textViewInstruction.setText("Le tribunal vous prend 1 pièce d'or.");
        }

        return announceCorrect;
    }

    /**
     * Second player power is to see its own card
     */
    public void lookCard(){
        Card cardPlayer = this.getCard();
        cardPlayer.setVisiblePlayer(true);
        //ajouter bouton ok
        cardPlayer.setVisiblePlayer(false);
    }

    /**
     * The thirs player power is swap (or not) its card with another player
     *
     * @param opponent
     */
    public void swapCards(Player opponent, boolean swap){

        if(swap) {
            Card opponentCard = opponent.getCard();
            Log.d(PLAYER, "before opponent : " + opponent.getName() + " => " + opponentCard.getTypeCard());
            Card tempCard = this.getCard();
            Log.d(PLAYER, "before mainplayer : " + this.getName() + " => " + this.getTypeCard());

            this.setCard(opponentCard);
            Card newCardPlayer = this.getCard();
            Log.d(PLAYER, "after mainplayer : " + this.getName() + " => " + this.getTypeCard());
            //newCardPlayer.hideCard(Integer.toString(this.getId()), boardMascarade);

            opponent.setCard(tempCard);
            Card newOpponentCard = opponent.getCard();
            Log.d(PLAYER, "after opponent : " + opponent.getName() + " => " + opponentCard.getTypeCard());
            //newOpponentCard.hideCard(Integer.toString(opponent.getId()), boardMascarade);


        }
        else{
            // do something : draw dark side even if no swap
        }
    }

    public int getNbMoney() {

        return nbMoney;
    }

    public void setNbMoney(int nbMoney) {
        String idStringTextViewGold = "gold_player_" + this.getId();
        TextView textViewGoldPlayer = (TextView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringTextViewGold);
        //Log.d(PLAYER, "textView before : " + textViewGoldPlayer.getText() + " " + this.getName());

        textViewGoldPlayer.setText(Integer.toString(nbMoney));
        this.nbMoney = nbMoney;
    }

    public String getTypeCard() {
        Card card = this.getCard();
        return card.getClass().getSimpleName();
    }

    public Card getCard() {

        return card;
    }

    public void setCard(Card card) {

        String idStringImageView = "imageViewCard_" + cardType + "_" + this.getId();
        int idCardImage = card.getIdCardImageFromCard();
        //Log.d(PLAYER, "idStringImageView setCard : " + idStringImageView);
        ImageView cardView = (ImageView)boardMascarade.findViewById(R.id.linearLayout_horiz_5players).findViewWithTag(idStringImageView);
        if (cardView != null) {
            //Log.d(PLAYER, "cardView not null  " + idCardImage);
            cardView.setImageResource(idCardImage);
        }
        this.card = card;
        this.cardType = this.getTypeCard();


    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public Activity getBoardMascarade() {
        return boardMascarade;
    }

    public void setBoardMascarade(Activity boardMascarade) {
        this.boardMascarade = boardMascarade;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public boolean isPowerAvailable() {
        return powerAvailable;
    }

    public void setPowerAvailable(boolean powerAvailable) {
        this.powerAvailable = powerAvailable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public String getLastCardKnown() {
        return lastCardKnown;
    }

    public void setLastCardKnown(String lastCardKnown) {
        this.lastCardKnown = lastCardKnown;
    }

    public void setIsPlayer(boolean isPlayer) {
        this.isPlayer = isPlayer;
    }
}
