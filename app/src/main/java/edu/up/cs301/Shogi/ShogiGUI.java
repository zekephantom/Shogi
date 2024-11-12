package edu.up.cs301.Shogi;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import edu.up.cs301.shogi.R;

/**
 * ShogiRenderer renders the board and pieces based on the state of ShogiBoard.
 */
public class ShogiGUI extends View {
    // TODO: check if private is the right access modifier for the Bitmap
    private ShogiState shogiState;
    private Paint paint;
    private List<Bitmap> scaledBitmaps = new ArrayList<>();

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

    public ShogiGUI(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paint = new Paint();
        loadBitmaps(context);
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
        initBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
        drawPieces(canvas);
    }

    private void drawBoard(Canvas canvas) {
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

        drawPieces(canvas);
    }

    /**
     function that returns what square has been touched
     */
     public  ShogiSquare gridSelection(float x, float y){

        // Set pos outside of possible answer
        ShogiSquare squareReturn = new ShogiSquare(9,11);

        // Find row
        for(int i = 0; i < 9; i++){
            float leftB = cellDimensions*i;
            if ((leftB < y) && ((leftB+cellDimensions)>y)){
                squareReturn.setRow(i);
                break;
            }
        }
        // Find col
        for(int i = 0; i < 11; i++){
            float topB = cellDimensions*i;
            if ((topB < x) && ((topB+cellDimensions) > x)){
                squareReturn.setCol(i);
                break;
            }
        }
        if (squareReturn.getRow() == 9 || squareReturn.getCol() == 11){
            Log.d("GUI", "Touch outside of legal fields");
        }
        return squareReturn;
    }


    private void drawPieces(Canvas canvas) {
        // Test on drawing a piece after scaling it
        //Bitmap scaledBitmap = Bitmap.createScaledBitmap(kingLower, (int) cellDimensions, (int) cellDimensions, true);
        //canvas.drawBitmap(scaledBitmap, cellDimensions*5, cellDimensions*8, null);

        int row, col;

        ArrayList<ShogiPiece> pieces = shogiState.getPieces();
        scaleBitmaps();

        for(int i = 0; i < scaledBitmaps.size(); i++){

            ShogiPiece piece = pieces.get(i);
            ShogiSquare piecePosition  = piece.getPosition();

            checkPromoteBitmap(piece.isPromoted(), piece, i);

            if (piece.isOnBoard()){
                // Ensure the position is within bounds
                row = Math.max(0, Math.min(piecePosition.getRow(), 8));
                col = Math.max(0, Math.min(piecePosition.getCol(), 8)) + 1; // col 0 -> captured Pieces

           }else{
                // Set column according to owner (10 for player 0, 0 for player 1)
                col = (piece.getOwner() == 0)? 10 : 0;

                // TODO: add a number onScreen to the captured piece depending how many pieces are on the field

                switch (piece.getType()) {
                    case Rook:
                        row = (piece.getOwner() == 0)? 2 : 6;
                        break;
                    case Bishop:
                        row = (piece.getOwner() == 0)? 3 : 5;
                        break;
                    case GoldGeneral:
                        row = (piece.getOwner() == 0)? 4 : 4;
                        break;
                    case SilverGeneral:
                        row = (piece.getOwner() == 0)? 5 : 3;
                        break;
                    case Knight:
                        row = (piece.getOwner() == 0)? 6 : 2;
                        break;
                    case Lance:
                        row = (piece.getOwner() == 0)? 7 : 1;
                        break;
                    case Pawn:
                        row = (piece.getOwner() == 0)? 8 : 0;
                        break;
                    default:
                        row = 0;
                }
           }

            // Calculate the position to draw the Bitmap
            float left = col * cellDimensions;
            float top = row * cellDimensions;

            // Draw the Bitmap on the canvas
            canvas.drawBitmap(scaledBitmaps.get(i), left, top, null);
        }

    }

    /**
     * Initialize BitMap ArrayList in the same order as the ArrayList of the pieces
     */
    private void initBitmap() {
        // Initialize pieces for Player 0 (bottom side)
        scaledBitmaps.add(lance);
        scaledBitmaps.add(knight);
        scaledBitmaps.add(silverGen);
        scaledBitmaps.add(goldGen);
        scaledBitmaps.add(kingLower);
        scaledBitmaps.add(goldGen);
        scaledBitmaps.add(silverGen);
        scaledBitmaps.add(knight);
        scaledBitmaps.add(lance);
        scaledBitmaps.add(bishop);
        scaledBitmaps.add(rook);
        for (int i = 0; i < 9; i++) {
            scaledBitmaps.add(pawn);
        }

        // Initialize pieces for Player 1 (top side)
        scaledBitmaps.add(flippedBitmap(lance));
        scaledBitmaps.add(flippedBitmap(knight));
        scaledBitmaps.add(flippedBitmap(silverGen));
        scaledBitmaps.add(flippedBitmap(goldGen));
        scaledBitmaps.add(flippedBitmap(kingLower));
        scaledBitmaps.add(flippedBitmap(goldGen));
        scaledBitmaps.add(flippedBitmap(silverGen));
        scaledBitmaps.add(flippedBitmap(knight));
        scaledBitmaps.add(flippedBitmap(lance));
        scaledBitmaps.add(flippedBitmap(bishop));
        scaledBitmaps.add(flippedBitmap(rook));
        for (int i = 0; i < 9; i++) {
            scaledBitmaps.add(flippedBitmap(pawn));
        }
    }

    /**
     *External Citation
     *Date: 19 September 2024
     *Problem: Couldn't figure out how to rotate a bitmap
     *Resource:
     * https://stackoverflow.com/a/29982596
     *Solution: I used the example code from this answer.
     */
    // rotates bitmap 180 degrees to be used as a "enemy" piece
    public static Bitmap flippedBitmap(Bitmap source)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(180);
        Bitmap flipped = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        return flipped;
    }

    private void checkPromoteBitmap(Boolean prom, ShogiPiece piece, int arrayPosition){
        switch (piece.getType()) {
            case Rook:
                scaledBitmaps.set(arrayPosition, (prom)? prom_rook : rook);
                break;
            case Bishop:
                scaledBitmaps.set(arrayPosition, (prom)? bishop : prom_bishop);
                break;
            case GoldGeneral: // no promotion possible
                scaledBitmaps.set(arrayPosition, (prom)? goldGen : goldGen);
                break;
            case SilverGeneral:
                scaledBitmaps.set(arrayPosition, (prom)? silverGen : prom_silver);
                break;
            case Knight:
                scaledBitmaps.set(arrayPosition, (prom)? prom_knight : knight);
                break;
            case Lance:
                scaledBitmaps.set(arrayPosition, (prom)? prom_lance : lance);
                break;
            case Pawn:
                scaledBitmaps.set(arrayPosition, (prom)? prom_pawn : pawn);
                break;
            default:
                Log.d("GUI", "No type for promotion");
        }
        scaleBitmaps();
    }

    private void scaleBitmaps() {
        for (int i = 0; i < scaledBitmaps.size(); i++) {
            scaledBitmaps.set(i, Bitmap.createScaledBitmap(scaledBitmaps.get(i), (int) cellDimensions, (int) cellDimensions, true));
        }
    }
}

