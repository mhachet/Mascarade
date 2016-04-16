package com.mascarade.model.cards;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mascarade.R;
import com.mascarade.model.game.Bank;
import com.mascarade.model.game.Player;
import com.mascarade.model.game.Tribunal;

import java.util.ArrayList;

/**
 * Created by melanie on 12/03/16.
 */
public class Card {

    protected boolean visibleAll = false;
    protected boolean visiblePlayer = false;
    private final String CARD = "CARD";
    protected Bank bank;
    protected Activity boardMascarade;

    //protected String imageLabelPath = "";

    public Card() {

    }

    public void activePower(Player playerConcerned) {
        String rolePlayer = playerConcerned.getCardType();
        Log.d(CARD, "activation of player role " + rolePlayer);

        if(rolePlayer.equals("Juge")) {
            Roi cardRoiPlayer = (Roi)playerConcerned.getCard();
            cardRoiPlayer.activePower(playerConcerned);
        }
        else if(rolePlayer.equals("Espionne")) {
            Espionne cardEspionnePlayer = (Espionne)playerConcerned.getCard();
            //cardEspionnePlayer.activePower(playerConcerned, playerOpponent, changeCards);
        }
        else if(rolePlayer.equals("Eveque")) {
            Eveque cardEvequePlayer = (Eveque)playerConcerned.getCard();
            cardEvequePlayer.activePower(playerConcerned, this.getBank());
        }
        else if(rolePlayer.equals("Fou")) {
            Fou cardFouPlayer = (Fou)playerConcerned.getCard();
            //cardFouPlayer.activePower(playerConcerned, firstPlayer, secondPlayer, activeChange);
        }
        else if(rolePlayer.equals("Inquisiteur")) {
            Inquisiteur cardInquisiteurPlayer = (Inquisiteur)playerConcerned.getCard();
            //cardInquisiteurPlayer.activePower(); not done
        }
        else if(rolePlayer.equals("Paysan")) {
            Paysan cardPaysanPlayer = (Paysan)playerConcerned.getCard();
            //cardPaysanPlayer.activePower(otherPaysan, concernedPlayer, partnerPlayer);
        }
        else if(rolePlayer.equals("Reine")) {
            Reine cardReinePlayer = (Reine)playerConcerned.getCard();
            cardReinePlayer.activePower(playerConcerned);
        }
        else if(rolePlayer.equals("Roi")) {
            Roi cardRoiPlayer = (Roi)playerConcerned.getCard();
            cardRoiPlayer.activePower(playerConcerned);
        }
        else if(rolePlayer.equals("Sorciere")) {
            Sorciere cardSorcierePlayer = (Sorciere)playerConcerned.getCard();
            //cardSorcierePlayer.activePower(playerConcerned, opponentPlayer);
        }
        else if(rolePlayer.equals("Tricheur")) {
            Tricheur cardTricheurPlayer = (Tricheur)playerConcerned.getCard();
            cardTricheurPlayer.activePower(playerConcerned, this.getBank());
        }
        else if(rolePlayer.equals("Veuve")) {
            Veuve cardVeuvePlayer = (Veuve)playerConcerned.getCard();
            cardVeuvePlayer.activePower(playerConcerned);
        }
        else if(rolePlayer.equals("Voleur")) {
            Voleur cardVoleurPlayer = (Voleur)playerConcerned.getCard();
            cardVoleurPlayer.activePower(playerConcerned, this.getBank());
        }

    }

    public Bitmap getLabelCard(String imageLabelPath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(imageLabelPath, options);

        return bitmap;
        //selected_photo.setImageBitmap(bitmap);
    }

    public int getIdImageLabelFromCard(){

        String cardType = this.getTypeCard();

        int idImage = 0;

        if(cardType.equals("Juge")) {
            idImage = R.drawable.juge_label;
        }
        else if(cardType.equals("Espionne")) {
            idImage = R.drawable.espionne_label;
        }
        else if(cardType.equals("Eveque")) {
            idImage = R.drawable.eveque_label;
        }
        else if(cardType.equals("Fou")) {
            idImage = R.drawable.fou_label;
        }
        else if(cardType.equals("Inquisiteur")) {
            idImage = R.drawable.inquisiteur_label;
        }
        else if(cardType.equals("Paysan")) {
            idImage = R.drawable.paysans_label;
        }
        else if(cardType.equals("Reine")) {
            idImage = R.drawable.reine_label;
        }
        else if(cardType.equals("Roi")) {
            idImage = R.drawable.roi_label;
        }
        else if(cardType.equals("Sorciere")) {
            idImage = R.drawable.sorciere_label;
        }
        else if(cardType.equals("Tricheur")) {
            idImage = R.drawable.tricheur_label;
        }
        else if(cardType.equals("Veuve")) {
            idImage = R.drawable.veuve_label;
        }
        else if(cardType.equals("Voleur")) {
            idImage = R.drawable.voleur_label;
        }

        return idImage;
    }

    public int getIdCardImageFromCard(){

        String cardType = this.getTypeCard();

        int idImage = 0;

        if(cardType.equals("Juge")) {
            idImage = R.drawable.juge_card;
        }
        else if(cardType.equals("Espionne")) {
            idImage = R.drawable.espionne_card;
        }
        else if(cardType.equals("Eveque")) {
            idImage = R.drawable.eveque_card;
        }
        else if(cardType.equals("Fou")) {
            idImage = R.drawable.fou_card;
        }
        else if(cardType.equals("Inquisiteur")) {
            idImage = R.drawable.inquisiteur_card;
        }
        else if(cardType.equals("Paysan")) {
            idImage = R.drawable.paysan_card;
        }
        else if(cardType.equals("Reine")) {
            idImage = R.drawable.reine_card;
        }
        else if(cardType.equals("Roi")) {
            idImage = R.drawable.roi_card;
        }
        else if(cardType.equals("Sorciere")) {
            idImage = R.drawable.sorciere_card;
        }
        else if(cardType.equals("Tricheur")) {
            idImage = R.drawable.tricheur_card;
        }
        else if(cardType.equals("Veuve")) {
            idImage = R.drawable.veuve_card;
        }
        else if(cardType.equals("Voleur")) {
            idImage = R.drawable.voleur_card;
        }

        return idImage;
    }


    public void exchangeCards(Player playerOne, Player playerTwo) {

    }

    public String getTypeCard() {
        return this.getClass().getSimpleName();
    }

    public boolean isVisibleAll() {
        return visibleAll;
    }

    public void setVisibleAll(boolean visibleAll) {
        this.visibleAll = visibleAll;
    }

    public boolean isVisiblePlayer() {
        return visiblePlayer;
    }

    public void setVisiblePlayer(boolean visiblePlayer) {
        this.visiblePlayer = visiblePlayer;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Activity getBoardMascarade() {
        return boardMascarade;
    }

    public void setBoardMascarade(Activity boardMascarade) {
        this.boardMascarade = boardMascarade;
    }
}
