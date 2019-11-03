package com.bavin.mohsen.backnardeban.Classes;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class ChilliTextView extends android.support.v7.widget.AppCompatTextView {
    public ChilliTextView(Context context) {
        super( context );
    }

    public ChilliTextView(Context context, AttributeSet attrs) {
        super( context, attrs );
      String  fontName = "iran_sans.ttf";
        Typeface iran_sans = Typeface.createFromAsset(context.getAssets(), fontName);
        this.setTypeface(iran_sans);
    }
}
