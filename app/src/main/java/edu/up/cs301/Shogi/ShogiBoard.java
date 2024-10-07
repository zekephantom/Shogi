package edu.up.cs301.Shogi;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Bitmap;
import java.util.*;

import edu.up.cs301.shogi.R;


public class ShogiBoard extends SurfaceView implements SurfaceHolder.Callback {
    private DrawingThread drawingThread;
    private List<ShogiPiece> shogiPieces;
    private List<ShogiPiece> capturedPieces;


    public ShogiBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);

        // Initialize the list of game pieces
        shogiPieces = new ArrayList<>();
        capturedPieces = new ArrayList<>();
        loadShogiPieces(context);
    }

    private void loadShogiPieces(Context context) {
        // Load Bitmaps for different pieces
        Bitmap kingLower = BitmapFactory.decodeResource(getResources(), R.drawable.kinglower);
        Bitmap rook = BitmapFactory.decodeResource(getResources(), R.drawable.rook);
        Bitmap prom_rook = BitmapFactory.decodeResource(getResources(), R.drawable.prom_rook);
        Bitmap bishop = BitmapFactory.decodeResource(getResources(), R.drawable.bishop);
        Bitmap prom_bishop = BitmapFactory.decodeResource(getResources(), R.drawable.prom_bishop);
        Bitmap goldGen = BitmapFactory.decodeResource(getResources(), R.drawable.goldgen);
        Bitmap silverGen = BitmapFactory.decodeResource(getResources(), R.drawable.silvergen);
        Bitmap prom_silver = BitmapFactory.decodeResource(getResources(), R.drawable.prom_silver);
        Bitmap knight = BitmapFactory.decodeResource(getResources(), R.drawable.knight);
        Bitmap prom_knight = BitmapFactory.decodeResource(getResources(), R.drawable.prom_knight);
        Bitmap lance = BitmapFactory.decodeResource(getResources(), R.drawable.lance);
        Bitmap prom_lance = BitmapFactory.decodeResource(getResources(), R.drawable.prom_lance);
        Bitmap pawn = BitmapFactory.decodeResource(getResources(), R.drawable.pawn);
        Bitmap prom_pawn = BitmapFactory.decodeResource(getResources(), R.drawable.prom_pawn);

/*
        // bottom side players pieces
        shogiPieces.add(new ShogiPiece(pawn, 6, 0));
        shogiPieces.add(new ShogiPiece(pawn, 6, 1));
        shogiPieces.add(new ShogiPiece(pawn, 5, 2));
        shogiPieces.add(new ShogiPiece(pawn, 6, 3));
        shogiPieces.add(new ShogiPiece(pawn, 6, 4));
        shogiPieces.add(new ShogiPiece(pawn, 6, 5));
        shogiPieces.add(new ShogiPiece(pawn, 5, 6));
        shogiPieces.add(new ShogiPiece(pawn, 6, 7));
        shogiPieces.add(new ShogiPiece(pawn, 6, 8));
        shogiPieces.add(new ShogiPiece(lance, 8, 0));
        shogiPieces.add(new ShogiPiece(lance, 8, 8));
        shogiPieces.add(new ShogiPiece(prom_knight, 2, 2));
        shogiPieces.add(new ShogiPiece(knight, 8, 7));
        shogiPieces.add(new ShogiPiece(silverGen, 8, 2));
        shogiPieces.add(new ShogiPiece(silverGen, 8, 6));
        shogiPieces.add(new ShogiPiece(goldGen, 8, 3));
        shogiPieces.add(new ShogiPiece(goldGen, 8, 5));
        shogiPieces.add(new ShogiPiece(kingLower, 8, 4));
        shogiPieces.add(new ShogiPiece(bishop, 5, 3));
        shogiPieces.add(new ShogiPiece(rook, 7, 7));

        // top side players pieces
        shogiPieces.add(new ShogiPiece(enemy(pawn), 2, 0));
        shogiPieces.add(new ShogiPiece(enemy(pawn), 2, 1));
        shogiPieces.add(new ShogiPiece(enemy(pawn), 2, 3));
        shogiPieces.add(new ShogiPiece(enemy(pawn), 3, 4));
        shogiPieces.add(new ShogiPiece(enemy(pawn), 2, 5));
        shogiPieces.add(new ShogiPiece(enemy(pawn), 2, 6));
        shogiPieces.add(new ShogiPiece(enemy(pawn), 2, 7));
        shogiPieces.add(new ShogiPiece(enemy(pawn), 2, 8));
        shogiPieces.add(new ShogiPiece(enemy(lance), 0, 0));
        shogiPieces.add(new ShogiPiece(enemy(lance), 0, 8));
        shogiPieces.add(new ShogiPiece(enemy(knight), 0, 1));
        shogiPieces.add(new ShogiPiece(enemy(knight), 0, 7));
        shogiPieces.add(new ShogiPiece(enemy(silverGen), 1, 2));
        shogiPieces.add(new ShogiPiece(enemy(silverGen), 1, 6));
        shogiPieces.add(new ShogiPiece(enemy(goldGen), 1, 4));
        shogiPieces.add(new ShogiPiece(enemy(goldGen), 1, 5));
        shogiPieces.add(new ShogiPiece(enemy(kingLower), 0, 4));
        shogiPieces.add(new ShogiPiece(enemy(bishop), 1, 7));
        shogiPieces.add(new ShogiPiece(enemy(rook), 1, 3));

        // captured pieces (row: maybe 0 for left/upper pieces and 1 for right/lower pieces
        // future automate that specific piece automatically has location specified
        capturedPieces.add(new ShogiPiece(pawn, 8, 1));*/
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Start the drawing thread and pass the list of game pieces
        drawingThread = new DrawingThread(getHolder(), shogiPieces, capturedPieces);
        drawingThread.setRunning(true);
        drawingThread.start();
        drawingThread.requestRedraw(); // Initial draw
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (drawingThread != null) {
            drawingThread.setRunning(false);
            drawingThread.requestRedraw(); // Wake the thread if waiting
            boolean retry = true;
            while (retry) {
                try {
                    drawingThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    // Retry stopping the thread
                }
            }
            drawingThread = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Handle surface changes if necessary
    }

    /**
     *External Citation
     *Date: 19 September 2024
     *Problem: Couldn't figure out how to rotate a bitmap
     *Resource: https://stackoverflow.com/a/29982596
     *Solution: I used the example code from this answer.
     */
    // rotates bitmap 180 degrees to be used as a "enemy" piece
    public static Bitmap enemy(Bitmap source)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(180);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public void initializeBoard() {
        // Set up game pieces in their initial positions.
        shogiPieces.clear(); // Clear any existing pieces.

        // Add initial pieces for Player 1 and Player 2.
        // (This code can be similar to the `loadGamePieces()` method but more tailored to setting up the board at the start of a game.)
        loadShogiPieces(getContext()); // Load initial pieces.

        // Request an initial draw to display the board.
        if (drawingThread != null) {
            drawingThread.requestRedraw();
        }
    }
}
