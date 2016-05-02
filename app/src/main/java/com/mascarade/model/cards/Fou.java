package com.mascarade.model.cards;

import android.util.Log;
import com.mascarade.model.game.Player;

/**
 * Created by melanie on 12/03/16.
 */
public class Fou extends Card {

    //private final int[] nbPlayersFou = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    private static final String FOU = "FOU";
    private Player fouPlayer;
    private Player firstPlayer;
    private Player secondPlayer;
    private boolean activeChange;

    public Fou() {
        super();
    }


    /**
     * The power of 'Fou' card is to win 1 piece from the bank
     * and change (or not) two cards from 2 others players (not himself).
     *
     * Player fouPlayer, Player firstPlayer, Player secondPlayer, boolean activeChange
     */
    public void activePower() {
        fouPlayer.setNbMoney(fouPlayer.getNbMoney() + 1);

        Card cardPlayerFirst = firstPlayer.getCard();
        Card cardPlayerSecond = secondPlayer.getCard();

        if (activeChange) {
            Log.d(FOU, "first " + firstPlayer.getId() + " : " + firstPlayer.getTypeCard() + " second " + secondPlayer.getId() + "  " + secondPlayer.getTypeCard());

            Card tempCard = cardPlayerFirst;
            firstPlayer.setCard(cardPlayerSecond);
            secondPlayer.setCard(tempCard);

            Log.d(FOU, "first " + firstPlayer.getId() + ": " + firstPlayer.getTypeCard() + " opponent " + secondPlayer.getId() + " : " + secondPlayer.getTypeCard());

        }

    }

    public Player getFouPlayer() {
        return fouPlayer;
    }

    public void setFouPlayer(Player fouPlayer) {
        this.fouPlayer = fouPlayer;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public boolean isActiveChange() {
        return activeChange;
    }

    public void setActiveChange(boolean activeChange) {
        this.activeChange = activeChange;
    }
}
