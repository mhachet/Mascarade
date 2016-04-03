package com.mascarade.model.game;

import com.mascarade.model.cards.Card;

/**
 * Created by melanie on 13/03/16.
 */
public class Player {

    private int nbMoney = 6;
    private Card card;
    private String cardType = "";
    private int id = 0;
    private boolean powerAvailable = false;
    private String name = "";
    private boolean isPlayer = false;
    private String lastCardKnown = "inconnu";

    public Player(int nbMoney, Card card, int id) {
        this.card = card;
        this.nbMoney = nbMoney;
        this.id = id;
    }

    /**
     *
     * First power : to annouce its card
     * if correct : card power is activated
     * else : player must pay 1 gold coin
     *
     * @param announcedCard
     * @return
     */
    public boolean annoucementCard(String announcedCard, Tribunal tribunal){
        boolean trueCard = false;

        if(announcedCard.equals(this.getTypeCard())){
            trueCard = true;
            Card cardActived = this.getCard();
            cardActived.activePower();
        }
        else{
            this.setNbMoney(this.getNbMoney() - 1);
            tribunal.setNbMoney(tribunal.getNbMoney() + 1);
        }
        return trueCard;
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
            Card tempCard = new Card();

            tempCard = this.getCard();
            this.setCard(opponentCard);
            opponent.setCard(tempCard);
        }
        else{
            // do something : draw dark side even if no swap
        }
    }

    public int getNbMoney() {

        return nbMoney;
    }

    public void setNbMoney(int nbMoney) {

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
        this.card = card;
        this.cardType = this.getTypeCard();
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
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
