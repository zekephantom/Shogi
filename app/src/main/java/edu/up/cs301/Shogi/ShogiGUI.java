package edu.up.cs301.Shogi;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import edu.up.cs301.shogi.R;

/**
 * ShogiRenderer renders the board and pieces based on the state of ShogiBoard.
 */
public class ShogiGUI extends View {
    // TODO: check if private is the right access modifier for the Bitmap
    private ShogiState shogiState;
    private Paint paint;
    private List<Bitmap> scaledBitmaps;

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


    // Helpful variables for drawing
    private float cellWidth;
    private float cellHeight;
    private float cellDimensions;
    private float fieldDimensions;
    private float capturedFieldRadius;

    public ShogiGUI(Context context, AttributeSet attrs/*, ShogiState state*/) {
        super(context, attrs);
        //this.shogiState = state;
        this.paint = new Paint();
        loadBitmaps(context);
        init();
    }

    // setter method for the shogi state
    public void setShogiState(ShogiState state){
        this.shogiState = state;
        invalidate();
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
        // kingUpper = BitmapFactory.decodeResource(context.getResources(), R.drawable.kingupper); // optional line of code
    }

    public void init(){
        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
        drawPieces(canvas);
    }

    private void drawBoard(Canvas canvas) {
        // TODO: Recheck if drawBoard is complete
        canvas.drawColor(Color.WHITE);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Calculate cell size
        cellWidth = width / 11f;
        cellHeight = height / 9f;


        // uses the value that will make the chess board fit proper while still being squared
        cellDimensions = Math.min(cellWidth, cellHeight);
        capturedFieldRadius = (float) (0.4*cellDimensions);
        fieldDimensions = cellDimensions*9;

        // debugging
        /*
        Log.i("cellSize", "Cellwidth: "+cellWidth);
        Log.i("cellSize", "Cellheight: "+cellHeight);
        Log.i("cellSize", "Celldim: "+cellDimensions);
        */

        // Initializing colors used
        Paint paintBlack = new Paint();
        Paint paintBackground = new Paint();
        Paint paintCapturedField = new Paint();
        paintBlack.setColor(Color.BLACK);
        paintBlack.setStrokeWidth(4);
        paintBackground.setColor(0xFF926211);
        paintCapturedField.setColor(0xa3d3d3d3);

        // add background
        canvas.drawRect(0,0,width,height, paintBlack);
        // add background color for board
        canvas.drawRect(cellDimensions,0, cellDimensions+fieldDimensions, fieldDimensions, paintBackground);

        // Draw vertical lines
        for (int i = 1; i <= 10; i++) { // start at 1-10 when adding captured pieces
            float x = i * cellDimensions;
            canvas.drawLine(x, 0, x, fieldDimensions, paintBlack);
        }

        // Draw horizontal lines
        for (int i = 0; i <= 9; i++) {
            float y = i * cellDimensions;
            canvas.drawLine(cellDimensions, y, cellDimensions+fieldDimensions, y, paintBlack);
        }

        // draw fields for captured pieces
        // Player 2
        for (int i = 0; i < 7; i++){
            float x = (float) 0.5 * cellDimensions;
            float y = (float) 0.5 * cellDimensions + i * cellDimensions;
            canvas.drawCircle(x,y,capturedFieldRadius, paintCapturedField);
        }
        // Player 1
        for (int i = 2; i < 9; i++){
            float x = (float) 10.5 * cellDimensions;
            float y = (float) 0.5 * cellDimensions + i * cellDimensions;
            canvas.drawCircle(x,y,capturedFieldRadius, paintCapturedField);
        }
    }


    // function that figures out what square has been touched
    public ShogiSquare gridSelection(int x, int y){

        return null;
    }

    private void drawPieces(Canvas canvas) {
        // TODO: We must check if we need this method


    }

    private void scaleBitmapsIfNeeded() {
        if (scaledBitmaps.isEmpty() || scaledBitmaps.get(0).getWidth() != (int) cellDimensions) {
            scaledBitmaps.clear();
                for (ShogiPiece piece : shogiState.getPieces()) {

                    /*
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
                   */

                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(kingLower, (int) cellDimensions, (int) cellDimensions, true);
                    scaledBitmaps.add(scaledBitmap);
                }

        }
    }
}

