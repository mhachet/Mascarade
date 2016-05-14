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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_mascarade);

        TextView mascaradeTittle = (TextView)findViewById(R.id.title_mascarade);
        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/cloister-black-light-light.ttf");
        mascaradeTittle.setTypeface(font);

        Button play = (Button)findViewById(R.id.button_jouer);
        //play.setTypeface(font);
        play.setOnClickListener(new ButtonOnClickListener(play));

        Button rulesButton = (Button)findViewById(R.id.button_rules);
        //rulesButton.setTypeface(font);
        rulesButton.setOnClickListener(new ButtonRulesOnClickListener(rulesButton));

        Button leaveButton = (Button)findViewById(R.id.button_leave);
        leaveButton.setOnClickListener(new ButtonLeaveOnClickListener(leaveButton));

    }

    class ButtonRulesOnClickListener implements View.OnClickListener{
        private final Button button;
        ButtonRulesOnClickListener(Button button){
            this.button = button;
        }

        @Override
        public void onClick(View v) {
            Log.d(MASCARADE, button.getId() + " is clicked");
            Intent httpActivity = new Intent(AccueilMascarade.this, HttpMascaradeActivity.class);
            startActivity(httpActivity);
        }
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

    class ButtonLeaveOnClickListener implements View.OnClickListener{
        private final Button button;
        ButtonLeaveOnClickListener(Button button) {

            this.button = button;
        }
        @Override
        public void onClick(View v) {
            finish();
            System.exit(0);
        }
    }

}
