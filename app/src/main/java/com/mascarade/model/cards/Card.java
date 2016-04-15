package com.mascarade.model.cards;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.widget.ImageView;

import com.mascarade.R;
import com.mascarade.model.game.Player;

import java.util.ArrayList;

/**
 * Created by melanie on 12/03/16.
 */
public class Card {

    protected ArrayList<Integer> nbPlayers;
    protected boolean visibleAll = false;
    protected boolean visiblePlayer = false;
    //protected String imageLabelPath = "";

    public Card() {
    }

    public void initialiseNbPlayers(int[] nbPlayersTable) {
        nbPlayers = new ArrayList<>();

        for (int i = 0; i < nbPlayersTable.length; i++) {
            nbPlayers.add(nbPlayersTable[i]);
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

    public void activePower() {

    }

    public void exchangeCards(Player playerOne, Player playerTwo) {

    }


    public ArrayList<Integer> getNbPlayers() {
        return nbPlayers;
    }

    public void setNbPlayers(ArrayList<Integer> nbPlayers) {
        this.nbPlayers = nbPlayers;
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
}
