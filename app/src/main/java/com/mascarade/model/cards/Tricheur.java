package com.mascarade.model.cards;

import com.mascarade.model.game.Bank;
import com.mascarade.model.game.Player;

/**
 * Created by melanie on 12/03/16.
 */
public class Tricheur extends Card {

    //private final int[] nbPlayersTricheur = {4, 5, 6, 9, 10, 11, 12, 13};

    public Tricheur() {
    }

    /**
     * The 'Tricheur' power is to wi if the player has 10 or more gold coin.
     *
     * @param concernedPlayer
     * @param bank
     */
    public void activePower(Player concernedPlayer, Bank bank){
        int nbMoneyPlayer = concernedPlayer.getNbMoney();

        if(nbMoneyPlayer >= 10){
            bank.setWinner(concernedPlayer);
        }
    }


    /*
    public int[] getNbPlayersTricheur() {

        return nbPlayersTricheur;
    }
    */
}
