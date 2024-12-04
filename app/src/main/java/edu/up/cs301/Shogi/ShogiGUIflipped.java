package edu.up.cs301.Shogi;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

public class ShogiGUIflipped extends ShogiGUI implements ShogiGUIBase{

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
        drawPieces(canvas);
        super.drawCapturedCount(canvas);
    }

    private void drawPieces(Canvas canvas){
        int row, col;

        ArrayList<ShogiPiece> pieces = shogiState.getPieces();

        // reset captured count array to 0
        for (int i = 0; i < capturedCount.length; i++) {
            Arrays.fill(capturedCount[i], 0);
        }

        for(int i = 0; i < scaledBitmaps.size(); i++){

            ShogiPiece piece = pieces.get(i);
            ShogiSquare piecePosition = piece.getPosition();

            checkPromoteBitmap(piece.isPromoted(), piece, i);

            checkOwnerBitmap(piece.getOwner(), i);

            if (piece.isOnBoard()){

                // flip position for other player
                row = 8 - piecePosition.getRow();
                col = 8 - piecePosition.getCol();

            }else{
                // Set column according to owner (10 for player 0, 0 for player 1)
                col = (piece.getOwner() == 0)? 0 : 10;
                // Set row according to piece type and owner
                row = 8 - piecePosition.getRow();
                // increment captured count if there is a piece already
                if(shogiState.getPiece(piecePosition) != null) capturedCount[row][col]++;
            }

            // Calculate the position to draw the Bitmap
            float left = col * cellDimensions;
            float top = row * cellDimensions;

            // Draw the Bitmap on the canvas
            canvas.drawBitmap(scaledBitmaps.get(i), left, top, null);
        }

    }

}
