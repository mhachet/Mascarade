package com.mascarade.model.cards;

import android.util.Log;
import com.mascarade.model.game.Player;
import com.mascarade.model.game.Bank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melanie on 12/03/16.
 */
public class Eveque extends Card {

    //private final int[] nbPlayersEveque = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    private static final String EVEQUE = "EVEQUE";
    private Player concernedPlayer;
    private Bank bank;

    private int nbMoneyRetrieved = 0;

    public Eveque() {


    }

    /**
     * The Eveque power allow to take 2 gold pieces to the richest player.
     * If equality between several players, the concerned player chose as he wants.
     *
     * Player concernedPlayer, Bank bank
     */
    public void activePower() {
        int moneyRetrieved = 0;
        //int maxMoney = this.findMaxMoneyOfPlayers(bank);
        List<Player> richestPlayers = this.findRichestPlayers(bank);
        Player opponentPlayer = richestPlayers.get(0);
        int nbMoneyOpponent = opponentPlayer.getNbMoney();
        Log.d(EVEQUE, "before eveque has " + concernedPlayer.getNbMoney() + " and opponent " + opponentPlayer.getNbMoney());
        if (nbMoneyOpponent >= 2) {
            concernedPlayer.setNbMoney(concernedPlayer.getNbMoney() + 2);
            opponentPlayer.setNbMoney(opponentPlayer.getNbMoney() - 2);
            moneyRetrieved = 2;

        } else {
            concernedPlayer.setNbMoney(concernedPlayer.getNbMoney() + opponentPlayer.getNbMoney());
            opponentPlayer.setNbMoney(0);
            moneyRetrieved = opponentPlayer.getNbMoney();
        }
        Log.d(EVEQUE, "after eveque has " + concernedPlayer.getNbMoney() + " and opponent " + opponentPlayer.getNbMoney());

        this.setNbMoneyRetrieved(moneyRetrieved);

        //return moneyRetrieved;
    }

    public int findMaxMoneyOfPlayers(Bank bank) {
        ArrayList<Player> playerArrayList = bank.getListPlayers();
        int max = 0;
        for (int p = 0; p < playerArrayList.size(); p++) {
            Player player = playerArrayList.get(p);
            int moneyPlayer = player.getNbMoney();
            if (max <= moneyPlayer) {
                max = moneyPlayer;
            }
        }

        return max;
    }

    public ArrayList<Player> findRichestPlayers(Bank bank) {
        int maxMoney = this.findMaxMoneyOfPlayers(bank);
        ArrayList<Player> playerArrayList = bank.getListPlayers();
        ArrayList<Player> richestPlayers = new ArrayList<>();
        for (int p = 0; p < playerArrayList.size(); p++) {
            Player player = playerArrayList.get(p);
            if(!player.equals(this)) {
                Log.d(EVEQUE, "richest player in eveque" + player.getName());
                int moneyPlayer = player.getNbMoney();
                if (maxMoney == moneyPlayer) {
                    richestPlayers.add(player);
                }
            }
            else{
                Log.d(EVEQUE, "main player in eveque" + player.getName());
            }
        }
        return richestPlayers;
    }

    public int getNbMoneyRetrieved() {
        return nbMoneyRetrieved;
    }

    public void setNbMoneyRetrieved(int nbMoneyRetrieved) {
        this.nbMoneyRetrieved = nbMoneyRetrieved;
    }

    public Player getConcernedPlayer() {
        return concernedPlayer;
    }

    public void setConcernedPlayer(Player concernedPlayer) {
        this.concernedPlayer = concernedPlayer;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
