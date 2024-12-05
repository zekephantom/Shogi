package edu.up.cs301.Shogi;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.up.cs301.shogi.R;

/**
 * ShogiGUI renders the board and pieces based on the state of ShogiBoard.
 */
//TODO flip GUI for second network player
public class ShogiGUI extends View {
    protected ShogiState shogiState;
    protected Context contextLocal;
    protected List<Bitmap> scaledBitmaps = new ArrayList<>();
    protected boolean isEnglish = false;
    protected int playerNum;

    protected Bitmap kingLower;
    protected Bitmap rook;
    protected Bitmap prom_rook;
    protected Bitmap bishop;
    protected Bitmap prom_bishop;
    protected Bitmap goldGen;
    protected Bitmap silverGen;
    protected Bitmap prom_silver;
    protected Bitmap knight;
    protected Bitmap prom_knight;
    protected Bitmap lance;
    protected Bitmap prom_lance;
    protected Bitmap pawn;
    protected Bitmap prom_pawn;

    protected ShogiSquare selectedSquare;
    protected ArrayList<ShogiSquare> possibleMoves;
    protected ShogiSquare priorOrig;
    protected ShogiSquare priorTarget;
    protected int[][] capturedCount = new int[9][11];


    // Helpful variables for drawing
    protected float width;
    protected float height;
    protected float radius;
    protected float cellWidth;
    protected float cellHeight;
    protected float cellDimensions;
    protected float fieldDimensions;
    protected float capturedFieldRadius;


    /**
     * Constructor
     * @param context
     * @param attrs
     */
    public ShogiGUI(Context context, AttributeSet attrs) {
        super(context, attrs);
        contextLocal = context; // needed to call the loadBitmaps method outside of the ctr
    }

// ------------------------ Setter methods ---------------------------

    // setter method for the playerNum
    public void setPlayerNumber(int playerN){
        playerNum = playerN;
    }

    // setter method for the shogi state
    public void setShogiState(ShogiState state){
        this.shogiState = state;
        invalidate();
    }

    // setter method for selected piece
    public void setSelected(ShogiSquare selected){
        this.selectedSquare = selected;
        invalidate();
    }
    // setter method for possible moves
    public void setPossibleMoves(ArrayList<ShogiSquare> possible){
        this.possibleMoves = possible;
        invalidate();
    }
    // setter method for previous move squares
    public void setPriorMoveSquares(ShogiSquare orig, ShogiSquare target){
        this.priorOrig = orig;
        this.priorTarget = target;
        invalidate();
    }
    // setter that reimports bitmaps iif language has been changed
    public void setLanguage(boolean isEng) {
        this.isEnglish = isEng;
        loadBitmaps(contextLocal);
        invalidate();

    }
// ------------------------- INIT methods -----------------------------

    public void loadBitmaps(Context context) {
        /**
         * External Citation
         *  Date: 8 November 2024
         *  Problem: Could not remember how to load up the .png files of Shogi pieces from src\main\res\drawable
         *  Resource: From Course Moodle website, file titled "PaintingImagesOnASurfaceView.pdf"
         *  Solution: Used the code format from number 3.
         */

        // Uses the right bitmaps according to the boolean for language
        if(isEnglish){
            kingLower = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.eng_king));

            rook = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.eng_rook));
            prom_rook = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.eng_prook));
            bishop = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.eng_bishop));
            prom_bishop = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.eng_pbishop));
            goldGen = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.eng_goldgen));
            silverGen = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.eng_silvergen));
            prom_silver = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.eng_psilver));
            knight = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.eng_knight));
            prom_knight = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.eng_pknight));
            lance = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.eng_lance));
            prom_lance = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.eng_plance));
            pawn = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.eng_pawn));
            prom_pawn = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.eng_ppawn));
            // kingUpper = BitmapFactory.decodeResource(context.getResources(), R.drawable.kingupper); // optional line of code
        }else {
            kingLower = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.kinglower));

            rook = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.rook));
            prom_rook = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.prom_rook));
            bishop = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bishop));
            prom_bishop = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.prom_bishop));
            goldGen = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.goldgen));
            silverGen = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.silvergen));
            prom_silver = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.prom_silver));
            knight = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.knight));
            prom_knight = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.prom_knight));
            lance = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.lance));
            prom_lance = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.prom_lance));
            pawn = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.pawn));
            prom_pawn = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.prom_pawn));
            // kingUpper = BitmapFactory.decodeResource(context.getResources(), R.drawable.kingupper); // optional line of code
        }
        // Creates arrayList in the same order to the arrayList holding all the pieces
        initBitmap();
    }


    // Initializes variables and cellDimensions only if there are changes
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        // Calculate cell size
        cellWidth = w / 11f;
        cellHeight = h / 9f;

        // uses the value that will make the chess board fit proper while still being squared
        cellDimensions = Math.min(cellWidth, cellHeight);
        capturedFieldRadius = (float) (0.4*cellDimensions);
        fieldDimensions = cellDimensions*9;
        radius = cellDimensions / (float)1.5; // for the circles being drawn

        // load bitmaps in here to make sure all dimensions are available
        loadBitmaps(contextLocal);

        // debugging
        /*
        Log.i("cellSize", "Cellwidth: "+cellWidth);
        Log.i("cellSize", "Cellheight: "+cellHeight);
        Log.i("cellSize", "Celldim: "+cellDimensions);
        */
    }

// ----------------------- Drawing is happening here -------------------

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
        drawPriorMove(canvas);
        drawSelected(canvas);
        drawPossibleMoves(canvas);
        drawCheck(canvas);
        drawPieces(canvas);
        drawCapturedCount(canvas);
    }

    /**
     * draws the background of the board, the grid for the fields,
     * and the patches for the captured pieces
     * @param canvas
     */
    protected void drawBoard(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        // Initializing colors used
        Paint paintBlack = new Paint();
        Paint paintBackground = new Paint();
        Paint paintCapturedField = new Paint();
        paintBlack.setColor(Color.BLACK);
        paintBlack.setStrokeWidth(4);
        paintBackground.setColor(0xFF926211); // brown
        paintCapturedField.setColor(0xa3d3d3d3); // grey

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

        // Draw circles that display promotion zones
        float miniCircleR = cellDimensions/12;
        canvas.drawCircle(cellDimensions*4, cellDimensions*3, miniCircleR, paintBlack);
        canvas.drawCircle(cellDimensions*4, cellDimensions*6, miniCircleR, paintBlack);
        canvas.drawCircle(cellDimensions*7, cellDimensions*3, miniCircleR, paintBlack);
        canvas.drawCircle(cellDimensions*7, cellDimensions*6, miniCircleR, paintBlack);

        // draw fields for captured pieces
        // Player 1
        for (int i = 0; i < 7; i++){
            float x = (float) 0.5 * cellDimensions;
            float y = (float) 0.5 * cellDimensions + i * cellDimensions;
            canvas.drawCircle(x,y,capturedFieldRadius, paintCapturedField);
        }
        // Player 0
        for (int i = 2; i < 9; i++){
            float x = (float) 10.5 * cellDimensions;
            float y = (float) 0.5 * cellDimensions + i * cellDimensions;
            canvas.drawCircle(x,y,capturedFieldRadius, paintCapturedField);
        }
    }

    /**
     *
     * draws the pieces in right location with the right bitmap
     * according to (position, owner, promotion, captured)
     * @param canvas
     */
    private void drawPieces(Canvas canvas) {

        int row, col;

        ArrayList<ShogiPiece> pieces = shogiState.getPieces();

        // reset captured count array to 1
        // capturedCount[][] = 1;
        for (int i = 0; i < capturedCount.length; i++) {
            Arrays.fill(capturedCount[i], 0);
        }

        for(int i = 0; i < scaledBitmaps.size(); i++){

            ShogiPiece piece = pieces.get(i);
            ShogiSquare piecePosition  = piece.getPosition();

            checkPromoteBitmap(piece.isPromoted(), piece, i);

            int pieceOwner = (playerNum == 0) ? piece.getOwner() : 1 - piece.getOwner();
            checkOwnerBitmap(pieceOwner, i);

            if (piece.isOnBoard()){

                //  get row/col
                row = (playerNum == 0) ? piecePosition.getRow() : 8 - piecePosition.getRow();
                col = (playerNum == 0) ? piecePosition.getCol()+1 : 8 - piecePosition.getCol()+1;

            }else{
                // Set column according to owner (10 for player 0, 0 for player 1)
                if (playerNum == 0){
                    col = (piece.getOwner() == 0)? 10 : 0;
                } else {
                    col = (piece.getOwner() == 0)? 0 : 10;
                }
                // Set row according to piece type and owner
                row = (playerNum == 0) ? piecePosition.getRow() : 8 - piecePosition.getRow();
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

    /**
     * draws a blue orb around the piece that has been selected
     * @param canvas
     */
    public void drawSelected(Canvas canvas){

        if (selectedSquare == null) return;
        int selectedColor = 0xFF00FFFF; // cyan

        float left = (switchLogicToGraphic(selectedSquare).getCol() + (float)0.5) * cellDimensions;
        float top = (selectedSquare.getRow() + (float)0.5) * cellDimensions;

        drawCricle(canvas, selectedColor,left, top);
    }

    /**
     * Draws white semitransparent squares from where to where a piece
     * has moved in the previous move
     * @param canvas
     */
    public void drawPriorMove(Canvas canvas){
        if(priorOrig == null || priorTarget == null) return;
        Paint paintPrior = new Paint();
        paintPrior.setColor(0x90FFFFFF); // faint white

        float left = switchLogicToGraphic(priorOrig).getCol() * cellDimensions;
        float top = switchLogicToGraphic(priorOrig).getRow() * cellDimensions;
        canvas.drawRect(left, top, left+cellDimensions, top+cellDimensions, paintPrior);

        left = switchLogicToGraphic(priorTarget).getCol() * cellDimensions;
        top = switchLogicToGraphic(priorTarget).getRow() * cellDimensions;
        canvas.drawRect(left, top, left+cellDimensions, top+cellDimensions, paintPrior);

    }

    /**
     * draws white circles on all the squares where a
     * selected piece can move to
     * @param canvas
     */
    public void drawPossibleMoves(Canvas canvas){

        if(selectedSquare == null || possibleMoves == null) return;
        int possibleMoveColor = 0xFFFFFFFF; // white

        for (ShogiSquare move : possibleMoves){

            float left = (move.getCol() + (float)1.5) * cellDimensions;
            float top = (move.getRow() + (float)0.5) * cellDimensions;

            drawCricle(canvas, possibleMoveColor,left, top);

        }
    }

    /**
     * draws a red circle around the King if it has been checked
     * @param canvas
     */
    public void drawCheck(Canvas canvas){
        int checkColor = 0xFFFF0000; // red
        ShogiSquare king;
        if (shogiState.isKingInCheck(shogiState.getCurrentPlayer())){
            if (shogiState.getCurrentPlayer() == 0) {
                king = shogiState.getPieces().get(4).getPosition();
            }
            else {
                king = shogiState.getPieces().get(24).getPosition();
            }
            float left = (king.getCol() + (float)1.5) * cellDimensions;
            float top = (king.getRow() + (float)0.5) * cellDimensions;
            drawCricle(canvas, checkColor,left, top);
        }
        if (shogiState.isKingInCheck(1 - shogiState.getCurrentPlayer())){
            if (shogiState.getCurrentPlayer() == 0) {
                king = shogiState.getPieces().get(4).getPosition();
            }
            else {
                king = shogiState.getPieces().get(24).getPosition();
            }
            float left = (king.getCol() + (float)1.5) * cellDimensions;
            float top = (king.getRow() + (float)0.5) * cellDimensions;
            drawCricle(canvas, checkColor,left, top);
        }
    }


    /**
     * draws a number next to each captured pieces if it is more than 1
     * @param canvas
     */
    public void drawCapturedCount(Canvas canvas){
        Log.d("GUI", "inside draw captured count");
        Paint circleFill = new Paint();
        Paint circleOutline = new Paint();
        Paint numbers = new Paint();
        circleFill.setColor(Color.WHITE);
        circleOutline.setColor(Color.BLACK);
        numbers.setColor(Color.BLACK);
        circleOutline.setStrokeWidth(3);
        circleOutline.setStyle(Paint.Style.STROKE);
        numbers.setTextSize(cellDimensions/4);

        float xOffset = cellDimensions*3/4;
        float yOffset = cellDimensions/4;
        float numberR = cellDimensions/5;

        // numbers player 1
        for(int i = 0; i < 7; i++){
            if(capturedCount[i][0] > 1){
                canvas.drawCircle(xOffset, i*cellDimensions+yOffset, numberR, circleFill);
                canvas.drawCircle(xOffset, i*cellDimensions+yOffset, numberR, circleOutline);
                canvas.drawText(String.valueOf(capturedCount[i][0]), (float)0.68*cellDimensions, (i+(float)0.33)*cellDimensions, numbers);
            }
        }
        // numbers player 2
        for(int i = 2; i < 9; i++){
            if(capturedCount[i][10]>1){
                canvas.drawCircle(10*cellDimensions+xOffset, i*cellDimensions+yOffset, numberR, circleFill);
                canvas.drawCircle(10*cellDimensions+xOffset, i*cellDimensions+yOffset, numberR, circleOutline);
                canvas.drawText(String.valueOf(capturedCount[i][10]), (float)10.68*cellDimensions, (i+(float)0.33)*cellDimensions, numbers);

            }
        }

    }

// -------------------------- Helper functions and initialization -----------------

    /**
     function that returns what square has been touched
     important ! -> switches column 0 to column 9 (captured col for player 1)
     also selects pieces differently according to each player
        in order to function with the game logic
     */
     public  ShogiSquare gridSelection(float x, float y){

        // Set pos outside of possible answer
        ShogiSquare squareReturn = new ShogiSquare(9,11);

        // Find row
        for(int i = 0; i < 9; i++){
            float topBound = cellDimensions*i;
            if ((topBound < y) && ((topBound+cellDimensions)>y)){
                squareReturn.setRow(i);
                break;
            }
        }
        // Find col
        for(int i = 0; i < 11; i++){
            float leftBoundary = cellDimensions*i;
            if ((leftBoundary < x) && ((leftBoundary+cellDimensions) > x)){
                if (i == 0) squareReturn.setCol(9);
                else if (i == 10) squareReturn.setCol(10);
                else squareReturn.setCol(i-1);
                break;
            }
        }

        // the empty spots around the captured fields that are unused
        if (squareReturn.getRow() == 9 || squareReturn.getCol() == 11
        || squareReturn.getRow() == 0 && squareReturn.getCol() == 10
        || squareReturn.getRow() == 1 && squareReturn.getCol() == 10
        || squareReturn.getRow() == 7 && squareReturn.getCol() == 9
        || squareReturn.getRow() == 8 && squareReturn.getCol() == 9){
            Log.d("GUI", "Touch outside of legal fields");
            return null;
        }

        return squareReturn;
    }

    /**
     * Helper function to switch square location between logic and graphic.
     * -> logic has col 0 switched with col 9
     * @param logic
     * @return
     */
    private ShogiSquare switchLogicToGraphic(ShogiSquare logic){
        if (logic == null) Log.d("GUI", "null object passed into switchLogicToGraphic");

        ShogiSquare graphic = new ShogiSquare(logic);

        if (graphic.getCol() == 9) graphic.setCol(0);
        else if (graphic.getCol() != 10) graphic.setCol(graphic.getCol() + 1);

        return graphic;
    }

    private void drawCricle(Canvas canvas, int color, float left, float top){
        Paint toPaint = new Paint();

        /*
         External Citation:
         ChatGPT: find how to create a gradual gradient
         November 12th
         */
        RadialGradient gradientSelected = new RadialGradient(
                left, top, radius,
                color,  //  color at the center
                color-0xFF000000,  // Transparent at the outer edge
                Shader.TileMode.CLAMP
        );

        // Apply the gradient to the paint
        toPaint.setShader(gradientSelected);

        canvas.drawCircle(left, top, radius, toPaint);

    }

    /**
     * Initialize BitMap ArrayList in the same order as the ArrayList of the pieces
     */
    private void initBitmap() {

        scaledBitmaps.clear();
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
    /**
     * rotates bitmap 180 degrees to be used as a "enemy" piece
     * @param source
      */
    public static Bitmap flippedBitmap(Bitmap source)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(180);
        Bitmap flipped = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        return flipped;
    }

    /**
     * returns the right row on where a captured piece has to be drawn
     * @param piece
     * @return
     */
    private int drawCaptured(ShogiPiece piece){
        int row;
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
        return row;
    }

    /**
     * checks if the Bitmap of a piece has to its promoted version
     * @param prom
     * @param piece
     * @param arrayPosition
     */
    private void checkPromoteBitmap(Boolean prom, ShogiPiece piece, int arrayPosition){
        switch (piece.getType()) {
            case Rook:
                scaledBitmaps.set(arrayPosition, (prom)? prom_rook : rook);
                break;
            case Bishop:
                scaledBitmaps.set(arrayPosition, (prom)? prom_bishop : bishop);
                break;
            case GoldGeneral: // no promotion possible
                scaledBitmaps.set(arrayPosition, (prom)? goldGen : goldGen);
                break;
            case SilverGeneral:
                scaledBitmaps.set(arrayPosition, (prom)? prom_silver : silverGen);
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
            case King:
                scaledBitmaps.set(arrayPosition, kingLower);
            default:
                Log.d("GUI", "No type for promotion");
        }
    }

    /**
     * flips a piece if it is the opponents piece (player 1 in this case)
     * @param owner
     * @param arrayPosition
     */
    private void checkOwnerBitmap(int owner, int arrayPosition){
        if(owner == 1){
            scaledBitmaps.set(arrayPosition, flippedBitmap(scaledBitmaps.get(arrayPosition)));
        }
    }

    // deprecated function -> was overused which slowed down the GUI a lot
/*  private void scaleBitmaps() {
        for (int i = 0; i < scaledBitmaps.size(); i++) {
            scaledBitmaps.set(i, scaleBitmap(scaledBitmaps.get(i)));
        }
    } */

    /**
     * simplifies the scaling of a singular Bitmap
     * @param toScale
     * @return
     */
    private Bitmap scaleBitmap(Bitmap toScale){
        return Bitmap.createScaledBitmap(toScale, (int) cellDimensions, (int) cellDimensions, true);
    }
}

