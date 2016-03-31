package com.mascarade.design;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.mascarade.R;
import com.mascarade.model.activities.PlateauMascarade;
import com.mascarade.model.game.Player;

/**
 * Created by melanie on 3/21/16.
 */
public class PlayerDesign extends View {

    private static final String PLAYER_DESIGN = "PLAYER_DESIGN";

    private Bitmap playerImage;
    private Player player;

    public PlayerDesign(Context context, Player player) {
        super(context);
        this.player = player;
    }

    public PlayerDesign(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerDesign(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        //int idImage = this.getIdImage();



    }


}
