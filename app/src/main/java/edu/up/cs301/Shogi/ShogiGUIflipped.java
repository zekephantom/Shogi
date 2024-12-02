package edu.up.cs301.Shogi;

import android.content.Context;
import android.util.AttributeSet;

public class ShogiGUIflipped extends ShogiGUI{

    private ShogiState shogiState;
    private ShogiState stateFlipped;
    private Context contextLocal;

    // TODO finish this class

    /**
     * ctr
     */
    public ShogiGUIflipped (Context context, AttributeSet attrs){
        super(context, attrs);
        contextLocal = context;
    }


}
