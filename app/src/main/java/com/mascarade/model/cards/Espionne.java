package com.mascarade.model.cards;

import android.util.Log;
import android.widget.ImageView;

import com.mascarade.model.game.Player;

/**
 * Created by melanie on 12/03/16.
 */


public class Espionne extends Card {
    //private final int[] nbPlayersEspionne = {7, 10, 11, 12, 13};
    private static final String ESPIONNE = "ESPIONNE";

    private Player espionnePlayer;
    private Player opponent;
    private boolean changeCards;

    public Espionne() {
        super();
    }

    /**
     * The power of spy card is to see another player card
     * and his/her card in order to change (or not) theses two cards.
     * Player espionnePlayer, Player opponent, boolean changeCards
     */
    public void activePower(Player espionnePlayer, Player opponent, boolean changeCards) {
        this.setOpponent(opponent);
        this.setPlayer(espionnePlayer);
        this.setChangeCards(changeCards);

        Card concernedPlayerCard = espionnePlayer.getCard();
        Card opponentPlayerCard = opponent.getCard();

        concernedPlayerCard.setVisiblePlayer(true);
        opponentPlayerCard.setVisiblePlayer(true);

        // draw box to propose exchange
        // and retrieve player choice
        if (changeCards) {
            Log.d(ESPIONNE, "player " + espionnePlayer.getId() + " : " + espionnePlayer.getTypeCard() + " opponent " + opponent.getId() + "  " + opponent.getTypeCard());

            Card tempCard = opponentPlayerCard;
            opponent.setCard(concernedPlayerCard);
            espionnePlayer.setCard(tempCard);

            Log.d(ESPIONNE, "player " + espionnePlayer.getId() + ": " + espionnePlayer.getTypeCard() + " opponent " + opponent.getId() + " : " + opponent.getTypeCard());
        }

        concernedPlayerCard.setVisiblePlayer(false);
        opponentPlayerCard.setVisiblePlayer(false);

    }

    public Player getEspionnePlayer() {
        return espionnePlayer;
    }

    public void setEspionnePlayer(Player espionnePlayer) {
        this.espionnePlayer = espionnePlayer;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public boolean isChangeCards() {
        return changeCards;
    }

    public void setChangeCards(boolean changeCards) {
        this.changeCards = changeCards;
    }
}
