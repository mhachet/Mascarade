package com.mascarade.model.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mascarade.R;
import com.mascarade.model.cards.Eveque;
import com.mascarade.model.game.Bank;
import com.mascarade.model.game.Player;

public class AccueilMascarade extends Activity {

    private static final String MASCARADE = "ACCUEIL";
    private RelativeLayout layout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_mascarade);

        TextView mascaradeTittle = (TextView)findViewById(R.id.title_mascarade);
        //Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/ancient-german-gothic.otf");
        //mascaradeTittle.setTypeface(font);
        //layout = (RelativeLayout) RelativeLayout.inflate(this, R.layout.activity_accueil_mascarade, null);
        Button play = (Button)findViewById(R.id.button_jouer);
        //play.setTypeface(font);
        play.setOnClickListener(new ButtonOnClickListener(play));

        Button rulesButton = (Button)findViewById(R.id.button_rules);
        //rulesButton.setTypeface(font);

    }
    public void evequePower(Bank bank) {
        Player playerEveque = bank.getPlayerWithCard("Eveque");
        Eveque eveque = (Eveque) playerEveque.getCard();
        Log.d(MASCARADE, "eveque est : " + playerEveque.getId() + "  " + playerEveque.getTypeCard());

        eveque.activePower(playerEveque, bank);

    }

    class ButtonOnClickListener implements View.OnClickListener{
        private final Button button;
        ButtonOnClickListener(Button button) {
            this.button = button;
        }
        @Override public void onClick(View v) {
            Log.d(MASCARADE, button.getId() + " is clicked");
            Intent launchOptions = new Intent(AccueilMascarade.this, OptionsMascarade.class);
            startActivity(launchOptions);
        }
    }


}
