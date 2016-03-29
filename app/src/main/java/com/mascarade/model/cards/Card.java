package com.mascarade.model.cards;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.widget.ImageView;

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
