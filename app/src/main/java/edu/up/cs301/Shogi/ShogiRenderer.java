package edu.up.cs301.Shogi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.List;

import edu.up.cs301.shogi.R;

/**
 * ShogiRenderer renders the board and pieces based on the state of ShogiBoard.
 */
public class ShogiRenderer extends View {
    private ShogiBoard shogiBoard;
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

    public ShogiRenderer(Context context, ShogiBoard board) {
        super(context);
        this.shogiBoard = board;
        this.paint = new Paint();
        loadBitmaps(context); // Load bitmaps during initialization
    }

    public void loadBitmaps(Context context) {
        kingLower = BitmapFactory.decodeResource(context.getResources(), R.drawable.kinglower);
        // kingUpper = BitmapFactory.decodeResource(context.getResources(), R.drawable.kingupper); // If needed
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
        List<ShogiPiece> pieces = shogiBoard.getShogiPieces();
        int cellSize = canvas.getWidth() / 9;

        for (ShogiPiece piece : pieces) {
            Bitmap bitmapToDraw = null;

            // Determine the bitmap to draw based on piece type and promotion status
            switch (piece.getType()) {
                case KING:
                    bitmapToDraw = kingLower; // Adjust if you have different kings for each player
                    break;
                case ROOK:
                    bitmapToDraw = piece.isPromoted() ? prom_rook : rook;
                    break;
                case BISHOP:
                    bitmapToDraw = piece.isPromoted() ? prom_bishop : bishop;
                    break;
                case GOLD_GENERAL:
                    bitmapToDraw = goldGen;
                    break;
                case SILVER_GENERAL:
                    bitmapToDraw = piece.isPromoted() ? prom_silver : silverGen;
                    break;
                case KNIGHT:
                    bitmapToDraw = piece.isPromoted() ? prom_knight : knight;
                    break;
                case LANCE:
                    bitmapToDraw = piece.isPromoted() ? prom_lance : lance;
                    break;
                case PAWN:
                    bitmapToDraw = piece.isPromoted() ? prom_pawn : pawn;
                    break;
                default:
                    // Handle any unknown piece types
                    break;
            }

            if (bitmapToDraw != null) {
                // Calculate the position to draw the bitmap
                int col = piece.getCol();
                int row = piece.getRow();

                // Convert board coordinates to pixel coordinates
                float x = col * cellSize;
                float y = row * cellSize;

                // Rotate pieces for Player 2
                if (piece.getOwner() == PlayerType.PLAYER2) {
                    canvas.save(); // Save the current canvas state

                    /**
                     External Citation
                     Date: 2024-11-07
                     Problem: Needed to rotate Player 2's pieces to face the opposite direction.
                     Resource: https://developer.android.com/reference/android/graphics/Canvas#rotate(float,%20float,%20float)
                     Solution: Used Canvas.rotate() to rotate the canvas around the center of the piece.
                     */
                    canvas.rotate(180, x + cellSize / 2, y + cellSize / 2);
                    canvas.drawBitmap(bitmapToDraw, x, y, paint);
                    canvas.restore(); // Restore the canvas state
                } else {
                    // Draw the bitmap without rotation
                    canvas.drawBitmap(bitmapToDraw, x, y, paint);
                }
            }
        }
    }
}
