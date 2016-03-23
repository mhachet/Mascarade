package com.mascarade.model.cards;

import com.mascarade.model.game.Player;

/**
 * Created by melanie on 12/03/16.
 */
public class Paysan extends Card {

    private final int[] nbPlayersPaysan = {8, 9, 10, 11, 12, 13};

    public Paysan() {
        this.initialiseNbPlayers(nbPlayersPaysan);
    }

    /**
     * The paysan power is to get 1 gold coin.
     * if the other paysan is revealed : each 2 paysans get 2 gold coins
     *
     * concerned card need to be visible for all
     * partner too if revealed
     *
     * @param otherPaysan
     * @param concernedPlayer
     * @param partnerPlayer
     */
    public void activePower(boolean otherPaysan, Player concernedPlayer, Player partnerPlayer){

        Card partnerCard = partnerPlayer.getCard();
        Card concernedCard = concernedPlayer.getCard();

        if(!otherPaysan){
            concernedPlayer.setNbMoney(concernedPlayer.getNbMoney() + 1);
        }
        else{
            if(partnerCard.getTypeCard().equals("Paysan")){
                partnerPlayer.setNbMoney(partnerPlayer.getNbMoney() + 2);
                concernedPlayer.setNbMoney(concernedPlayer.getNbMoney() + 2);
            }
        }

    }

    public int[] getNbPlayersPaysan() {
        return nbPlayersPaysan;
    }
}
