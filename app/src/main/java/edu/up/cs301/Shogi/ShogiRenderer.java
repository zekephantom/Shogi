package edu.up.cs301.Shogi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import edu.up.cs301.shogi.R;

/**
 * ShogiRenderer renders the board and pieces based on the state of ShogiBoard.
 */
public class ShogiRenderer extends View {
    private ShogiState shogiState;

    private Paint paint;

    private Bitmap kingLower;
    private Bitmap rook;
    private Bitmap prom_rook;
    private Bitmap bishop;
    private Bitmap prom_bishop;
    private Bitmap goldGen;
    private Bitmap silverGen;
    private Bitmap prom_silver;
    private Bitmap knight;
    private Bitmap prom_knight;
    private Bitmap lance;
    private Bitmap prom_lance;
    private Bitmap pawn;
    private Bitmap prom_pawn;

    public ShogiRenderer(Context context, ShogiState state) {
        super(context);
        this.shogiState = state;
        this.paint = new Paint();
        loadBitmaps(context);
    }

    public void loadBitmaps(Context context) {
        /**
         * External Citation
         *  Date: 8 November 2024
         *  Problem: Could not remember how to load up the .png files of Shogi pieces from src\main\res\drawable
         *  Resource: From Course Moodle website, file titled "PaintingImagesOnASurfaceView.pdf"
         *  Solution: Used the code format from number 3.
         */

        kingLower = BitmapFactory.decodeResource(context.getResources(), R.drawable.kinglower);
        // kingUpper = BitmapFactory.decodeResource(context.getResources(), R.drawable.kingupper); // optional line of code
        rook = BitmapFactory.decodeResource(context.getResources(), R.drawable.rook);
        prom_rook = BitmapFactory.decodeResource(context.getResources(), R.drawable.prom_rook);
        bishop = BitmapFactory.decodeResource(context.getResources(), R.drawable.bishop);
        prom_bishop = BitmapFactory.decodeResource(context.getResources(), R.drawable.prom_bishop);
        goldGen = BitmapFactory.decodeResource(context.getResources(), R.drawable.goldgen);
        silverGen = BitmapFactory.decodeResource(context.getResources(), R.drawable.silvergen);
        prom_silver = BitmapFactory.decodeResource(context.getResources(), R.drawable.prom_silver);
        knight = BitmapFactory.decodeResource(context.getResources(), R.drawable.knight);
        prom_knight = BitmapFactory.decodeResource(context.getResources(), R.drawable.prom_knight);
        lance = BitmapFactory.decodeResource(context.getResources(), R.drawable.lance);
        prom_lance = BitmapFactory.decodeResource(context.getResources(), R.drawable.prom_lance);
        pawn = BitmapFactory.decodeResource(context.getResources(), R.drawable.pawn);
        prom_pawn = BitmapFactory.decodeResource(context.getResources(), R.drawable.prom_pawn);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
        drawPieces(canvas);
    }

    private void drawBoard(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(4);
        int cellSize = canvas.getWidth() / 9;

        // Draw vertical lines
        for (int i = 0; i <= 9; i++) {
            canvas.drawLine(i * cellSize, 0, i * cellSize, canvas.getHeight(), paint);
        }

        // Draw horizontal lines
        for (int i = 0; i <= 9; i++) {
            canvas.drawLine(0, i * cellSize, canvas.getWidth(), i * cellSize, paint);
        }
    }

    private void drawPieces(Canvas canvas) {
    }
}

