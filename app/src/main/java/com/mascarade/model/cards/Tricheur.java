package com.mascarade.model.cards;

import android.widget.TextView;

import com.mascarade.R;
import com.mascarade.model.game.Bank;
import com.mascarade.model.game.Player;

/**
 * Created by melanie on 12/03/16.
 */
public class Tricheur extends Card {

    //private final int[] nbPlayersTricheur = {4, 5, 6, 9, 10, 11, 12, 13};

    private Player concernedPlayer;
    private Bank bank;

    public Tricheur() {
        super();
    }

    /**
     * The 'Tricheur' power is to win if the player has 10 or more gold coin.
     *
     */
    public boolean activePower(Player concernedPlayer, Bank bank){
        this.setConcernedPlayer(concernedPlayer);
        this.setBank(bank);

        boolean isWinner = false;

        TextView textViewInstruction = (TextView)boardMascarade.findViewById(R.id.textView_instructions);
        int nbMoneyPlayer = concernedPlayer.getNbMoney();

        if(nbMoneyPlayer >= 10){
            bank.setWinner(concernedPlayer);
            isWinner = true;
        }

        return isWinner;
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

    /*
    public int[] getNbPlayersTricheur() {

        return nbPlayersTricheur;
    }
    */
}
