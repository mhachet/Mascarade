package com.mascarade.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import junit.framework.Assert;

import java.text.AttributedCharacterIterator;

/**
 * Created by melanie on 3/27/16.
 */
public class TextFontCloisterBlack extends TextView {

    public TextFontCloisterBlack(Context context, AttributeSet setAttribute){
        super(context, setAttribute);

        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/cloister-black-light-light.ttf"));
    }
}
