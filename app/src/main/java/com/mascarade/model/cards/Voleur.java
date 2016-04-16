package com.mascarade.model.cards;

import com.mascarade.model.game.Bank;
import com.mascarade.model.game.Player;

import java.util.ArrayList;

/**
 * Created by melanie on 12/03/16.
 */
public class Voleur extends Card {

    //private final int[] nbPlayersVoleur = {4, 7, 13};

    public Voleur(){

    }

    public void activePower(Player concernedPlayer, Bank bank){
        int nbPlayers = bank.getNbPlayers();
        int idConcernedPlayer = concernedPlayer.getId();
        ArrayList<Player> playerArrayList = bank.getListPlayers();

        Player rightPlayer = null;
        Player leftPlayer = null;

        if(idConcernedPlayer == 0){
            rightPlayer= playerArrayList.get(nbPlayers - 1);
            leftPlayer = playerArrayList.get(1);
        }
        else if(idConcernedPlayer == nbPlayers - 1){
            rightPlayer = playerArrayList.get(idConcernedPlayer - 1);
            leftPlayer = playerArrayList.get(0);
        }
        else{
            rightPlayer = playerArrayList.get(idConcernedPlayer - 1);
            leftPlayer = playerArrayList.get(idConcernedPlayer + 1);
        }

        if(rightPlayer.getNbMoney() > 0){
            rightPlayer.setNbMoney(rightPlayer.getNbMoney() - 1);
            concernedPlayer.setNbMoney(concernedPlayer.getNbMoney() + 1);
        }

        if(leftPlayer.getNbMoney() > 0) {
            leftPlayer.setNbMoney(leftPlayer.getNbMoney() - 1);
            concernedPlayer.setNbMoney(concernedPlayer.getNbMoney() + 1);
        }
    }

    /*
    public Voleur() {
        this.initialiseNbPlayers(nbPlayersVoleur);
    }
    */
}
