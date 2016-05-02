package com.mascarade.model.cards;

import android.util.Log;

import com.mascarade.model.game.Player;

/**
 * Created by melanie on 12/03/16.
 */
public class Roi extends Card {

    //private final int[] nbPlayersRoi = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    private final String ROI = "ROI";

    private Player concernedPlayer;

    public Roi() {
        super();
    }

    public Roi (Player concernedPlayer){
        this.concernedPlayer = concernedPlayer;
    }

    public void activePower(Player concernedPlayer){
        this.setConcernedPlayer(concernedPlayer);
        concernedPlayer.setNbMoney(concernedPlayer.getNbMoney() + 3);
        Log.d(ROI, " money player : " + concernedPlayer.getNbMoney());
    }

    public Player getConcernedPlayer() {
        return concernedPlayer;
    }

    public void setConcernedPlayer(Player concernedPlayer) {
        this.concernedPlayer = concernedPlayer;
    }
}
