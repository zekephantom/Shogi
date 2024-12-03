package edu.up.cs301.Shogi;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class ShogiGUIflipped extends ShogiGUI{

    private ShogiState shogiState;
    private ShogiState stateFlipped;


    // TODO finish this class

    /**
     * ctr
     */
    public ShogiGUIflipped (Context context, AttributeSet attrs){
        super(context, attrs);
        super.contextLocal = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        super.drawBoard(canvas);
        super.drawPriorMove(canvas);
        super.drawSelected(canvas);
        super.drawPossibleMoves(canvas);
        super.drawCheck(canvas);
        //drawPieces(canvas);
        super.drawCapturedCount(canvas);
    }

    private void drawPieces(Canvas canvas){
        return;
    }


}
