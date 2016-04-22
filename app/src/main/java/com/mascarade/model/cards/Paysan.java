package com.mascarade.model.cards;

import com.mascarade.model.game.Player;

/**
 * Created by melanie on 12/03/16.
 */
public class Paysan extends Card {

   // private final int[] nbPlayersPaysan = {8, 9, 10, 11, 12, 13};

    private boolean otherPaysan;
    private Player concernedPlayer;
    private Player partnerPlayer;

    public Paysan() {

    }

    /**
     * The paysan power is to get 1 gold coin.
     * if the other paysan is revealed : each 2 paysans get 2 gold coins
     *
     * concerned card need to be visible for all
     * partner too if revealed
     *
     * boolean otherPaysan, Player concernedPlayer, Player partnerPlayer
     */
    public void activePower(){


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

    public Player getPartnerPlayer() {
        return partnerPlayer;
    }

    public void setPartnerPlayer(Player partnerPlayer) {
        this.partnerPlayer = partnerPlayer;
    }

    public boolean isOtherPaysan() {
        return otherPaysan;
    }

    public void setOtherPaysan(boolean otherPaysan) {
        this.otherPaysan = otherPaysan;
    }

    public Player getConcernedPlayer() {
        return concernedPlayer;
    }

    public void setConcernedPlayer(Player concernedPlayer) {
        this.concernedPlayer = concernedPlayer;
    }

/*public int[] getNbPlayersPaysan() {
        return nbPlayersPaysan;
    }
    */
}
