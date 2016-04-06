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
import com.mascarade.model.game.Round;
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

        Button button_next = (Button)findViewById(R.id.button_next);
        button_next.setOnClickListener(new ButtonNextOnClickListener(button_next));

    }
    class ButtonLeaveOnClickListener implements View.OnClickListener{
        private final Button button;

        ButtonLeaveOnClickListener(Button button) {

            this.button = button;
        }
        @Override
        public void onClick(View v) {
            finish();
            Intent leaveGame = new Intent(PlateauMascarade.this, AccueilMascarade.class);
            startActivity(leaveGame);
        }
    }

    class ButtonNextOnClickListener implements View.OnClickListener{
        private final Button button;

        ButtonNextOnClickListener(Button button) {

            this.button = button;
        }
        @Override
        public void onClick(View v) {
            TextView textView_nbRound = (TextView)findViewById(R.id.textView_round);
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

                Round firstRound = new Round(0, PlateauMascarade.this);

                drawRolesInGame(newGame);

                drawPlayersInGame(newGame);

                setVariablesOnBoard(newGame);

                this.button.setClickable(false);
                this.button.setEnabled(false);

                gameIsLaunch = true;




                //firstRound.drawCardsRoundZero(newGame);


            }

        }

    }

    public void setVariablesOnBoard(Bank bank){
        Player mainPlayer = bank.getMainPlayer();

        TextView textView_lastRoleKnown = (TextView)findViewById(R.id.textView_last_role_known);
        String lastRoleKnown = mainPlayer.getLastCardKnown();
        textView_lastRoleKnown.setText(lastRoleKnown);

        TextView textView_countRound = (TextView)findViewById(R.id.textView_round);
        int countRound = bank.getCountRound();
        textView_countRound.setText(Integer.toString(countRound));

        TextView textView_tribunal_gold = (TextView)findViewById(R.id.textView_tribunal_gold);
        textView_tribunal_gold.setText("0");
    }

    public void drawPlayersInGame(Bank bank){

        LinearLayout linearLayout = null;
        ArrayList<Player> playerArrayList = bank.getListPlayers();
        double nbPlayers = bank.getNbPlayers();
        int nbCol = 0;

        if(nbPlayers == 4){
            nbCol = 3;
        }
        else {
            nbCol = (int) Math.round(nbPlayers / 2);
        }

        LinearLayout.LayoutParams linear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, (1/(float)nbCol));
        LinearLayout.LayoutParams linearLayoutCard = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f);

        for(int i = 0 ; i < nbCol ; i++){
            LinearLayout linearLayoutPlayer_vertical = new LinearLayout(this);
            linearLayoutPlayer_vertical.setLayoutParams(linear);
            linearLayoutPlayer_vertical.setOrientation(LinearLayout.VERTICAL);
            linearLayoutPlayer_vertical.setGravity(Gravity.CENTER);
            Log.d(PLATEAU, "nbPlayer : " + nbPlayers + " nbCol : " + nbCol + " i : " + i);

            Player player = playerArrayList.get(i);
            //int idImage = this.getIdImageFromPlayer(player);

            boolean isMainPlayer = player.isPlayer();
            if (isMainPlayer) {
                Log.d(PLATEAU, player.getId() + " isPlayer : " + isMainPlayer);
                linearLayoutPlayer_vertical.setBackgroundColor(Color.parseColor("#e3aa79"));
            } else {
                Log.d(PLATEAU, player.getId() + " is not Player : " + isMainPlayer);
            }
            String playerName = player.getName();

            TextView nbMoneyView = new TextView(this);
            nbMoneyView.setGravity(Gravity.CENTER);
            nbMoneyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            nbMoneyView.setBackgroundResource(R.drawable.gold_coin);
            nbMoneyView.setText(Integer.toString(player.getNbMoney()));

            TextView textView = new TextView(this);
            textView.setText(playerName);
            textView.setTextSize(10);
            textView.setLayoutParams(linear);

                /*
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(idImage);
                imageView.setLayoutParams(linear);

                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));//, 1/nbCol
                imageView.setLayoutParams(new LinearLayout.LayoutParams(40,40));
                */
            linearLayout = (LinearLayout) findViewById(R.id.linearLayout_firstLine);
            linearLayout.setWeightSum(1);

            ImageView cardImageView = new ImageView(this);
            cardImageView.setImageResource(getIdCardImageFromCard(player.getCard()));
            cardImageView.setLayoutParams(linearLayoutCard);
            //Log.d(PLATEAU, "width : " + cardImageView.getLayoutParams().width + " height : " + cardImageView.getLayoutParams().height);
            cardImageView.getLayoutParams().height = 250;
            cardImageView.getLayoutParams().width = 166;
            cardImageView.requestLayout();

            linearLayoutPlayer_vertical.addView(textView);
            //linearLayoutPlayer_vertical.addView(imageView);
            linearLayoutPlayer_vertical.addView(nbMoneyView);
            linearLayoutPlayer_vertical.addView(cardImageView);
            linearLayout.addView(linearLayoutPlayer_vertical);
        }

        if(nbPlayers == 4){
            Log.d(PLATEAU, "nbPlayer : " + nbPlayers + " nbCol : " + nbCol );

            linearLayout = (LinearLayout) findViewById(R.id.linearLayout_firstLine);

            LinearLayout linearLayoutPlayer_vertical = new LinearLayout(this);
            linearLayoutPlayer_vertical.setBackgroundColor(Color.parseColor("#f5e0b2"));
            linearLayoutPlayer_vertical.setLayoutParams(linear);
            linearLayoutPlayer_vertical.setOrientation(LinearLayout.VERTICAL);
            linearLayoutPlayer_vertical.setGravity(Gravity.CENTER);

            TextView textView = new TextView(this);
            textView.setText("Centre de la table");
            textView.setTextSize(10);
            textView.setLayoutParams(linear);

            TextView textView_firstCard = new TextView(this);
            textView_firstCard.setText("Carte n°1");
            textView_firstCard.setPadding(10,10,10,10);
            textView_firstCard.setTextSize(10);
            textView_firstCard.setLayoutParams(linear);

            ArrayList<Card> listCardCenter = bank.getBankCardsCenter();
            Card firstCardCenter = listCardCenter.get(0);

            ImageView cardImageView = new ImageView(this);
            cardImageView.setImageResource(getIdCardImageFromCard(firstCardCenter));
            cardImageView.setLayoutParams(linearLayoutCard);
            //Log.d(PLATEAU, "width : " + cardImageView.getLayoutParams().width + " height : " + cardImageView.getLayoutParams().height);
            cardImageView.getLayoutParams().height = 250;
            cardImageView.getLayoutParams().width = 166;
            cardImageView.requestLayout();

            linearLayoutPlayer_vertical.addView(textView);
            linearLayoutPlayer_vertical.addView(textView_firstCard);
            linearLayoutPlayer_vertical.addView(cardImageView);

            linearLayout.addView(linearLayoutPlayer_vertical);

        }
        for(int j = (int)nbPlayers - 1 ; j > nbCol -1 ; j--) {
            Log.d(PLATEAU, "nbPlayer : " + nbPlayers + " nbCol : " + nbCol + " j : " + j);

            LinearLayout linearLayoutPlayer_vertical = new LinearLayout(this);

            linearLayoutPlayer_vertical.setLayoutParams(linear);
            linearLayoutPlayer_vertical.setOrientation(LinearLayout.VERTICAL);
            linearLayoutPlayer_vertical.setGravity(Gravity.CENTER);
            //linearLayoutPlayer_vertical.setPadding(5, 5, 5, 5);
            //int left, int top, int right, int bottom

            Player player = playerArrayList.get(j);

            String namePlayer = player.getName();

            boolean isMainPlayer = player.isPlayer();
            if (isMainPlayer) {
                linearLayoutPlayer_vertical.setBackgroundColor(Color.parseColor("#e3aa79"));
            }
            TextView textView = new TextView(this);
            textView.setText(namePlayer);
            textView.setTextSize(10);
            textView.setLayoutParams(linear);

            TextView nbMoneyView = new TextView(this);
            nbMoneyView.setGravity(Gravity.CENTER);
            nbMoneyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            nbMoneyView.setBackgroundResource(R.drawable.gold_coin);
            nbMoneyView.setText(Integer.toString(player.getNbMoney()));

            //int idImage = this.getIdImageFromPlayer(player);

                /*
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(idImage);
                imageView.setLayoutParams(linear);

                //imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                  //      LinearLayout.LayoutParams.WRAP_CONTENT));//, 1/nbCol
                imageView.setLayoutParams(new LinearLayout.LayoutParams(40,40));

                */
            linearLayout = (LinearLayout) findViewById(R.id.linearLayout_secondLine);
            linearLayout.setWeightSum(1);

            ImageView cardImageView = new ImageView(this);
            cardImageView.setImageResource(getIdCardImageFromCard(player.getCard()));
            cardImageView.setLayoutParams(linearLayoutCard);
            //Log.d(PLATEAU, "width : " + cardImageView.getLayoutParams().width + " height : " + cardImageView.getLayoutParams().height);
            cardImageView.getLayoutParams().height = 250;
            cardImageView.getLayoutParams().width = 166;
            cardImageView.requestLayout();

            linearLayoutPlayer_vertical.addView(textView);
            //linearLayoutPlayer_vertical.addView(imageView);
            linearLayoutPlayer_vertical.addView(nbMoneyView);
            linearLayoutPlayer_vertical.addView(cardImageView);
            linearLayout.addView(linearLayoutPlayer_vertical);

        }

        if(nbPlayers == 4){
            Log.d(PLATEAU, "nbPlayer : " + nbPlayers + " nbCol : " + nbCol );

            linearLayout = (LinearLayout) findViewById(R.id.linearLayout_secondLine);

            LinearLayout linearLayoutPlayer_vertical = new LinearLayout(this);

            linearLayoutPlayer_vertical.setLayoutParams(linear);
            linearLayoutPlayer_vertical.setOrientation(LinearLayout.VERTICAL);
            linearLayoutPlayer_vertical.setGravity(Gravity.CENTER);
            //linearLayoutPlayer_vertical.setPadding(5, 5, 5, 5);
            linearLayoutPlayer_vertical.setBackgroundColor(Color.parseColor("#f5e0b2"));

            /*
            TextView textView = new TextView(this);
            textView.setText("Centre de la table");
            textView.setTextSize(10);
            textView.setLayoutParams(linear);
            */

            ArrayList<Card> listCardCenter = bank.getBankCardsCenter();
            Card firstCardCenter = listCardCenter.get(1);

            TextView textView_secondCard = new TextView(this);
            textView_secondCard.setText("Carte n°2");
            textView_secondCard.setPadding(10, 10, 10, 10);
            textView_secondCard.setTextSize(10);
            textView_secondCard.setLayoutParams(linear);

            ImageView cardImageView = new ImageView(this);
            cardImageView.setImageResource(getIdCardImageFromCard(firstCardCenter));
            cardImageView.setLayoutParams(linearLayoutCard);
            //Log.d(PLATEAU, "width : " + cardImageView.getLayoutParams().width + " height : " + cardImageView.getLayoutParams().height);
            cardImageView.getLayoutParams().height = 250;
            cardImageView.getLayoutParams().width = 166;
            cardImageView.requestLayout();

            //relinearLayoutPlayer_vertical.addView(textView);
            linearLayoutPlayer_vertical.addView(textView_secondCard);
            linearLayoutPlayer_vertical.addView(cardImageView);
            linearLayout.addView(linearLayoutPlayer_vertical);

        }

        if(nbPlayers == 5) {

            LinearLayout view_board = (LinearLayout)findViewById(R.id.view_board);
            view_board.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f));

            LinearLayout linearLayout_horiz_5players = (LinearLayout)findViewById(R.id.linearLayout_horiz_5players);
            linearLayout_horiz_5players.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            LinearLayout linearLayout_centralCard_5players = new LinearLayout(this);
            linearLayout_centralCard_5players.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 0.3f));

            linearLayout_centralCard_5players.setOrientation(LinearLayout.VERTICAL);
            linearLayout_centralCard_5players.setGravity(Gravity.CENTER);
            //linearLayoutPlayer_vertical.setPadding(5, 5, 5, 5);
            linearLayout_centralCard_5players.setBackgroundColor(Color.parseColor("#f5e0b2"));
            //linearLayout_centralCard_5players.setBackgroundColor(Color.BLUE);

            TextView textViewCenter = new TextView(this);
            textViewCenter.setText("Centre de la table");
            textViewCenter.setGravity(Gravity.CENTER_VERTICAL);
            textViewCenter.setTextSize(10);
            textViewCenter.setLayoutParams(linear);

            ArrayList<Card> listCardCenter = bank.getBankCardsCenter();
            Card firstCardCenter = listCardCenter.get(0);

            TextView textView_secondCard = new TextView(this);
            textView_secondCard.setText("Carte n°1");
            textView_secondCard.setGravity(Gravity.CENTER_VERTICAL);
            textView_secondCard.setPadding(10, 10, 10, 10);
            textView_secondCard.setTextSize(10);
            textView_secondCard.setLayoutParams(linear);

            ImageView cardImageView = new ImageView(this);
            cardImageView.setImageResource(getIdCardImageFromCard(firstCardCenter));
            cardImageView.setLayoutParams(linearLayoutCard);
            //Log.d(PLATEAU, "width : " + cardImageView.getLayoutParams().width + " height : " + cardImageView.getLayoutParams().height);
            cardImageView.getLayoutParams().height = 250;
            cardImageView.getLayoutParams().width = 166;

            cardImageView.requestLayout();

            linearLayout_centralCard_5players.addView(textViewCenter);
            linearLayout_centralCard_5players.addView(textView_secondCard);
            linearLayout_centralCard_5players.addView(cardImageView);
            linearLayout_horiz_5players.addView(linearLayout_centralCard_5players);

        }

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

            //Log.d(PLATEAU, "id : " + images[i] + " -- " + cardType.toLowerCase() + "_label");
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

    public int getIdCardImageFromCard(Card card){

        String cardType = card.getTypeCard();

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

    public Tribunal getTribunal() {
        return tribunal;
    }

    public void setTribunal(Tribunal tribunal) {
        this.tribunal = tribunal;
    }

    public Bank getNewGame() {
        return newGame;
    }

    public void setNewGame(Bank newGame) {
        this.newGame = newGame;
    }

    public boolean isGameIsLaunch() {
        return gameIsLaunch;
    }

    public void setGameIsLaunch(boolean gameIsLaunch) {
        this.gameIsLaunch = gameIsLaunch;
    }
}
