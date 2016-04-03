package com.mascarade.model.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mascarade.R;
import com.mascarade.model.adapter.ListItem;
import com.mascarade.model.adapter.RolesAdapter;
import com.mascarade.model.cards.Card;
import com.mascarade.model.cards.Espionne;
import com.mascarade.model.cards.Fou;
import com.mascarade.model.game.Bank;
import com.mascarade.model.game.Player;
import com.mascarade.model.game.Tribunal;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlateauMascarade extends Activity {

    private static final String PLATEAU = "PLATEAU";
    private Tribunal tribunal = null;
    private Bank newGame = null;
    private boolean gameIsLaunch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_plateau_mascarade);

        Button leaveButton = (Button)findViewById(R.id.button_leave);
        leaveButton.setOnClickListener(new ButtonLeaveOnClickListener(leaveButton));

        Button startButton = (Button)findViewById(R.id.button_start);
        startButton.setOnClickListener(new ButtonStartGameOnClickListener(startButton));

        Button announceCardButton = (Button)findViewById(R.id.button_announceCard);
        announceCardButton.setClickable(false);
        announceCardButton.setEnabled(false);

        Button seeCardButton = (Button)findViewById(R.id.button_seeCard);
        seeCardButton.setClickable(false);
        seeCardButton.setEnabled(false);

    }
    class ButtonLeaveOnClickListener implements View.OnClickListener{
        private final Button button;

        ButtonLeaveOnClickListener(Button button) {

            this.button = button;
        }
        @Override
        public void onClick(View v) {
            Intent leaveGame = new Intent(PlateauMascarade.this, AccueilMascarade.class);
            startActivity(leaveGame);
        }
    }

    class ButtonStartGameOnClickListener implements View.OnClickListener{
        private final Button button;

        ButtonStartGameOnClickListener(Button button){
            this.button = button;
        }

        @Override
        public void onClick(View view){

            if(!gameIsLaunch) {
                Intent plateau = getIntent();

                int nbPlayers = Integer.parseInt(plateau.getStringExtra("nbPlayers"));
                String pseudo = plateau.getStringExtra("pseudo");
                newGame = new Bank(nbPlayers);
                newGame.initialiseCardsBank();
                newGame.initialiseNbPlayers(pseudo);

                newGame.distributionCards();

                tribunal = new Tribunal(newGame.getBankCardsListStart());

                drawRolesInGame(newGame);

                drawPlayersInGame(newGame);

                setVariablesOnBoard(newGame);

                this.button.setClickable(false);
            }
            else{
                Intent reloadGame = new Intent(PlateauMascarade.this, PlateauMascarade.this);
                startActivity(reloadGame);
            }

        }

    }

    public void setVariablesOnBoard(Bank bank){
        Player mainPlayer = bank.getMainPlayer();

        TextView textView_gold_player = (TextView)findViewById(R.id.textView_gold);
        int nbMoney_player = mainPlayer.getNbMoney();
        textView_gold_player.setText(Integer.toString(nbMoney_player));

        TextView textView_lastRoleKnown = (TextView)findViewById(R.id.textView_last_role_known);
        String lastRoleKnown = mainPlayer.getLastCardKnown();
        textView_lastRoleKnown.setText(lastRoleKnown);

        TextView textView_countRound = (TextView)findViewById(R.id.textView_round);
        int countRound = bank.getCountRound();
        textView_countRound.setText(Integer.toString(countRound));
    }

    public void drawPlayersInGame(Bank bank){

        LinearLayout linearLayout = null;

        ArrayList<Player> playerArrayList = bank.getListPlayers();

        double nbPlayers = bank.getNbPlayers();

        int nbCol = (int)Math.round(nbPlayers / 2);

        LinearLayout.LayoutParams linear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, (1/(float)nbCol));

        for(int i = 0 ; i < nbCol ; i++){
            LinearLayout linearLayoutPlayer_vertical = new LinearLayout(this);

            linearLayoutPlayer_vertical.setLayoutParams(linear);
            linearLayoutPlayer_vertical.setOrientation(LinearLayout.VERTICAL);
            linearLayoutPlayer_vertical.setGravity(Gravity.CENTER);

            Player player = playerArrayList.get(i);
            int idImage = this.getIdImageFromPlayer(player);

            String playerName = player.getName();

            TextView nbMoneyView = new TextView(this);
            nbMoneyView.setGravity(Gravity.CENTER);
            nbMoneyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            nbMoneyView.setBackgroundResource(R.drawable.gold_coin);
            nbMoneyView.setText(Integer.toString(player.getNbMoney()));

            TextView textView = new TextView(this);
            textView.setText(playerName);
            textView.setLayoutParams(linear);

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(idImage);
            imageView.setLayoutParams(linear);



            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));//, 1/nbCol
            linearLayout = (LinearLayout) findViewById(R.id.linearLayout_firstLine);
            linearLayout.setWeightSum(1);

            linearLayoutPlayer_vertical.addView(textView);
            linearLayoutPlayer_vertical.addView(imageView);
            linearLayoutPlayer_vertical.addView(nbMoneyView);
            linearLayout.addView(linearLayoutPlayer_vertical);
        }

        for(int j = (int)nbPlayers - 1 ; j >= nbCol ; j--) {

            LinearLayout linearLayoutPlayer_vertical = new LinearLayout(this);

            linearLayoutPlayer_vertical.setLayoutParams(linear);
            linearLayoutPlayer_vertical.setOrientation(LinearLayout.VERTICAL);
            linearLayoutPlayer_vertical.setGravity(Gravity.CENTER);

            Player player = playerArrayList.get(j);

            String namePlayer = player.getName();

            TextView textView = new TextView(this);
            textView.setText(namePlayer);
            textView.setLayoutParams(linear);

            TextView nbMoneyView = new TextView(this);
            nbMoneyView.setGravity(Gravity.CENTER);
            nbMoneyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            nbMoneyView.setBackgroundResource(R.drawable.gold_coin);
            nbMoneyView.setText(Integer.toString(player.getNbMoney()));

            int idImage = this.getIdImageFromPlayer(player);

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(idImage);
            imageView.setLayoutParams(linear);

            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));//, 1/nbCol
            linearLayout = (LinearLayout) findViewById(R.id.linearLayout_secondLine);
            linearLayout.setWeightSum(1);

            linearLayoutPlayer_vertical.addView(textView);
            linearLayoutPlayer_vertical.addView(imageView);
            linearLayoutPlayer_vertical.addView(nbMoneyView);
            linearLayout.addView(linearLayoutPlayer_vertical);
        }


        /*
        RolesAdapter adapter = new RolesAdapter(this, myList);
        listViewPlayersDesign.setAdapter(adapter);
        listViewPlayersDesign.setOnItemClickListener(adapter);
        */
    }



    public void drawRolesInGame(Bank bank){
        List<Card> cardArrayListInGame = bank.getBankCardsListStart();
        ArrayList<ListItem> myList = new ArrayList<>();
        ListView listViewCardsLabel = (ListView) findViewById(R.id.listView_roles_game);

        String [] names = new String[cardArrayListInGame.size()];
        int[] images = new int [cardArrayListInGame.size()];
        boolean paysan = false;
        for(int i = 0 ; i < cardArrayListInGame.size(); i++){
            Card card = cardArrayListInGame.get(i);
            String cardType = cardArrayListInGame.get(i).getTypeCard();

            int idImage =this.getIdImageFromCard(card);

            if(cardType.equals("Paysan") && paysan == false) {
                names[i] = cardType;
                images[i] = idImage;
                paysan = true;
            }
            else if(!cardType.equals("Paysan")){
                names[i] = cardType;
                images[i] = idImage;
            }

            Log.d(PLATEAU, "id : " + images[i] + " -- " + cardType.toLowerCase() + "_label");
            ListItem item = new ListItem(images[i], names[i]);
            item.setImageId(images[i]);
            item.setNameImage(names[i]);
            myList.add(item);
        }

        RolesAdapter adapter = new RolesAdapter(this, myList);
        listViewCardsLabel.setAdapter(adapter);
        listViewCardsLabel.setOnItemClickListener(adapter);
    }

    public int getIdImageFromCard(Card card){

        String cardType = card.getTypeCard();

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

    public int getIdImageFromPlayer(Player player){
        int nbPlayer = player.getId();
        int idImage = 0;
        if(nbPlayer == 0){
            idImage = R.drawable.mask_3;
        }
        else if(nbPlayer == 1){
            idImage = R.drawable.mask_8;
        }
        else if(nbPlayer == 2){
            idImage = R.drawable.mask_11;
        }
        else if(nbPlayer == 3){
            idImage = R.drawable.mask_6;
        }
        else if(nbPlayer == 4){
            idImage = R.drawable.mask_13;
        }
        else if(nbPlayer == 5){
            idImage = R.drawable.mask_2;
        }
        else if(nbPlayer == 6){
            idImage = R.drawable.mask_7;
        }
        else if(nbPlayer == 7){
            idImage = R.drawable.mask_8;
        }
        else if(nbPlayer == 8){
            idImage = R.drawable.mask_1;
        }
        else if(nbPlayer == 9){
            idImage = R.drawable.mask_10;
        }
        else if(nbPlayer == 10){
            idImage = R.drawable.mask_9;
        }
        else if(nbPlayer == 11){
            idImage = R.drawable.mask_12;
        }
        else if(nbPlayer == 12){
            idImage = R.drawable.mask_5;
        }

        return idImage;
    }

    public void fouPower(Bank game) {
        boolean changeCards = true;
        Player playerFou = game.getPlayerWithCard("Fou");
        Fou fou = (Fou) playerFou.getCard();
        Player firstPlayer = game.getRandomPlayer();
        Player secondPlayer = game.getRandomPlayer();
        while (firstPlayer.equals(playerFou)) {
            firstPlayer = game.getRandomPlayer();
        }
        while (secondPlayer.equals(playerFou) || secondPlayer.equals(firstPlayer)) {
            secondPlayer = game.getRandomPlayer();
        }
        fou.activePower(playerFou, firstPlayer, secondPlayer, changeCards);

    }

    public boolean espionnePower(Bank game, boolean exchange) {
        boolean exchangeDone = false;
        Player playerEspion = game.getPlayerWithCard("Espionne");
        Espionne espion = (Espionne) playerEspion.getCard();
        Log.d(PLATEAU, "espion est : " + playerEspion.getId() + "  " + playerEspion.getTypeCard());
        int idPlayerEspion = playerEspion.getId();
        Player opponentPlayer = null;
        if (idPlayerEspion < game.getListPlayers().size() - 1) {
            opponentPlayer = game.getListPlayers().get(idPlayerEspion + 1);
        } else {
            opponentPlayer = game.getListPlayers().get(idPlayerEspion - 1);
        }
        Log.d(PLATEAU, "playerOpponent : " + opponentPlayer.getId() + "  " + opponentPlayer.getTypeCard());
        espion.activePower(playerEspion, opponentPlayer, exchange);
        exchangeDone = true;


        return exchangeDone;
    }

    public int generateNbPlayers() {
        Random random = new Random();
        int nbPlayers = random.nextInt((13 - 4) + 1) + 4;

        return nbPlayers;
    }

}
