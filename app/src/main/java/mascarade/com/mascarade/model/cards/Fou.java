package com.model.cards;

import android.util.Log;
import com.model.game.Player;

/**
 * Created by melanie on 12/03/16.
 */
public class Fou extends Card {

    private final int[] nbPlayersFou = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    private static final String FOU = "FOU";

    public Fou() {
        this.initialiseNbPlayers(nbPlayersFou);
    }

    /**
     * The power of 'Fou' card is to win 1 piece from the bank
     * and change (or not) two cards from 2 others players (not himself).
     *
     * @param fouPlayer
     * @param firstPlayer
     * @param secondPlayer
     */
    public void activePower(Player fouPlayer, Player firstPlayer, Player secondPlayer, boolean activeChange) {
        fouPlayer.setNbMoney(fouPlayer.getNbMoney() + 1);

        Card cardPlayerFirst = firstPlayer.getCard();
        Card cardPlayerSecond = secondPlayer.getCard();

        if (activeChange) {
            Log.d(FOU, "first " + firstPlayer.getId() + " : " + firstPlayer.getTypeCard() + " second " + secondPlayer.getId() + "  " + secondPlayer.getTypeCard());

            Card tempCard = new Card();
            tempCard = cardPlayerFirst;
            firstPlayer.setCard(cardPlayerSecond);
            secondPlayer.setCard(tempCard);

            Log.d(FOU, "first " + firstPlayer.getId() + ": " + firstPlayer.getTypeCard() + " opponent " + secondPlayer.getId() + " : " + secondPlayer.getTypeCard());

        }

    }

    public int[] getNbPlayersFou() {
        return nbPlayersFou;
    }
}
