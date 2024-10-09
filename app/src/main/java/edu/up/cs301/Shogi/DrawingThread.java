package edu.up.cs301.Shogi;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * External Citation
 *   Date: 16 September 2024
 *   Problem: Needed a way to draw a 9x9 grid inside the Surface View feature, so I asked AI for help on how to implement the grid;
 *           Recommended to use the Thread class and synchronized() block for future developments
 *   Resource: ChatGPT
 *   Solution: I used the example code from this answer. Used synchronized() block to ensure the smoothness of drawing the 9x9 board.
 */
public class DrawingThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private boolean running = false;
    private boolean needsRedraw = false;
    private final Object lock = new Object();

    private List<ShogiPiece> shogiPieces;
    private List<ShogiPiece> capturedPieces;

    private List<Bitmap> scaledShogiBitmaps;
    private List<Bitmap> scaledCapturedBitmaps;

    private float cellWidth;
    private float cellHeight;
    private float cellDimensions;
    private float fieldDimensions;
    private float capturedFieldRadius;

    // Paint objects for drawing
    private Paint paintBlack;
    private Paint paintBackground;
    private Paint paintCapturedField;

    public DrawingThread(SurfaceHolder holder, List<ShogiPiece> shogiPieces, List<ShogiPiece> capturedPieces) {
        this.surfaceHolder = holder;
        // Use synchronized lists for thread safety
        this.shogiPieces = Collections.synchronizedList(shogiPieces);
        this.capturedPieces = Collections.synchronizedList(capturedPieces);

        this.scaledShogiBitmaps = new ArrayList<>();
        this.scaledCapturedBitmaps = new ArrayList<>();

        // Initialize Paint objects
        paintBlack = new Paint();
        paintBlack.setColor(Color.BLACK);
        paintBlack.setStrokeWidth(4);
        paintBlack.setStyle(Paint.Style.STROKE);

        paintBackground = new Paint();
        paintBackground.setColor(0xFF926211); // Example color

        paintCapturedField = new Paint();
        paintCapturedField.setColor(0xa3d3d3d3); // Example color
    }

    /**
     * Sets the running flag and starts the thread if necessary.
     *
     * @param isRunning true to start the thread, false to stop it
     */
    public void setRunning(boolean isRunning) {
        synchronized (lock) {
            this.running = isRunning;
            if (running) {
                lock.notify(); // Wake up the thread if it's waiting
            }
        }
    }

    /**
     * Requests a redraw of the canvas.
     */
    public void requestRedraw() {
        synchronized (lock) {
            needsRedraw = true;
            lock.notify(); // Notify the thread to redraw
        }
    }

    /**
     * Retrieves the width of a single cell.
     *
     * @return the cell width in pixels
     */
    public float getCellWidth() {
        return cellWidth;
    }

    /**
     * Retrieves the height of a single cell.
     *
     * @return the cell height in pixels
     */
    public float getCellHeight() {
        return cellHeight;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                if (!running) {
                    break; // Exit the loop if not running
                }
                if (!needsRedraw) {
                    try {
                        lock.wait(); // Wait until notified
                    } catch (InterruptedException e) {
                        // Handle interruption
                        continue;
                    }
                    continue;
                }
                needsRedraw = false;
            }

            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    synchronized (surfaceHolder) {
                        drawGrid(canvas);
                    }
                }
            } catch (Exception e) {
                // Log or handle exceptions during drawing
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        // Clean up resources if necessary
        recycleBitmaps();
    }

    /**
     * Draws the Shogi board grid, promotion zones, and captured pieces fields.
     *
     * @param canvas the Canvas on which to draw
     */
    private void drawGrid(Canvas canvas) {
        // Clear the canvas with a white background
        canvas.drawColor(Color.WHITE);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Calculate cell size based on canvas dimensions
        cellWidth = width / 11f;   // 9 cells for the board + margins/captured areas
        cellHeight = height / 9f;  // 9 rows for the board

        // Determine the minimum dimension to keep cells square
        cellDimensions = Math.min(cellWidth, cellHeight);

        // Define dimensions for board and captured fields
        fieldDimensions = cellDimensions * 9;
        capturedFieldRadius = 0.4f * cellDimensions;

        // Draw the outer border
        canvas.drawRect(0, 0, width, height, paintBlack);

        // Draw the board background
        canvas.drawRect(cellDimensions, 0, cellDimensions + fieldDimensions, fieldDimensions, paintBackground);

        // Draw vertical lines
        for (int i = 1; i <= 10; i++) { // 1 to 10 to include captured areas
            float x = i * cellDimensions;
            canvas.drawLine(x, 0, x, fieldDimensions, paintBlack);
        }

        // Draw horizontal lines
        for (int i = 0; i <= 9; i++) { // 0 to 9 for 9 rows
            float y = i * cellDimensions;
            canvas.drawLine(cellDimensions, y, cellDimensions + fieldDimensions, y, paintBlack);
        }

        // Draw promotion zone indicators (small circles)
        float promoRadius = cellDimensions / 13f;
        canvas.drawCircle(cellDimensions * 4, cellDimensions * 3, promoRadius, paintBlack);
        canvas.drawCircle(cellDimensions * 7, cellDimensions * 3, promoRadius, paintBlack);
        canvas.drawCircle(cellDimensions * 4, cellDimensions * 6, promoRadius, paintBlack);
        canvas.drawCircle(cellDimensions * 7, cellDimensions * 6, promoRadius, paintBlack);

        // Draw fields for captured pieces
        drawCapturedFields(canvas);

        // Scale bitmaps if necessary
        scaleBitmapsIfNeeded();

        // Draw all active Shogi pieces
        drawActivePieces(canvas);

        // Draw all captured Shogi pieces
        drawCapturedPieces(canvas);
    }

    /**
     * Draws the fields designated for captured pieces.
     *
     * @param canvas the Canvas on which to draw
     */
    private void drawCapturedFields(Canvas canvas) {
        // Player 2's captured pieces field (left side)
        for (int i = 0; i < 7; i++) {
            float x = 0.5f * cellDimensions;
            float y = 0.5f * cellDimensions + i * cellDimensions;
            canvas.drawCircle(x, y, capturedFieldRadius, paintCapturedField);
        }

        // Player 1's captured pieces field (right side)
        for (int i = 2; i < 9; i++) {
            float x = 10.5f * cellDimensions;
            float y = 0.5f * cellDimensions + i * cellDimensions;
            canvas.drawCircle(x, y, capturedFieldRadius, paintCapturedField);
        }
    }

    /**
     * Scales the bitmaps of active and captured pieces if necessary.
     */
    private void scaleBitmapsIfNeeded() {
        boolean needsScaling = false;

        // Check if scaling is needed for active pieces
        if (scaledShogiBitmaps.isEmpty() || (scaledShogiBitmaps.get(0).getWidth() != (int) cellDimensions)) {
            needsScaling = true;
        }

        // Check if scaling is needed for captured pieces
        if (scaledCapturedBitmaps.isEmpty() || (scaledCapturedBitmaps.get(0).getWidth() != (int) cellDimensions)) {
            needsScaling = true;
        }

        if (needsScaling) {
            // Clear existing scaled bitmaps
            scaledShogiBitmaps.clear();
            scaledCapturedBitmaps.clear();

            // Scale active Shogi piece bitmaps
            synchronized (shogiPieces) {
                for (ShogiPiece piece : shogiPieces) {
                    if (piece.getBitmap() != null) {
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(piece.getBitmap(), (int) cellDimensions, (int) cellDimensions, true);
                        scaledShogiBitmaps.add(scaledBitmap);
                    } else {
                        // Handle missing bitmap
                        scaledShogiBitmaps.add(null);
                    }
                }
            }

            // Scale captured Shogi piece bitmaps
            synchronized (capturedPieces) {
                for (ShogiPiece piece : capturedPieces) {
                    if (piece.getBitmap() != null) {
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(piece.getBitmap(), (int) cellDimensions, (int) cellDimensions, true);
                        scaledCapturedBitmaps.add(scaledBitmap);
                    } else {
                        // Handle missing bitmap
                        scaledCapturedBitmaps.add(null);
                    }
                }
            }
        }
    }

    /**
     * Draws all active Shogi pieces onto the canvas.
     *
     * @param canvas the Canvas on which to draw
     */
    private void drawActivePieces(Canvas canvas) {
        synchronized (shogiPieces) {
            for (int i = 0; i < shogiPieces.size(); i++) {
                ShogiPiece piece = shogiPieces.get(i);
                Bitmap scaledBitmap = scaledShogiBitmaps.get(i);

                if (scaledBitmap == null) continue; // Skip if bitmap is missing

                // Ensure the position is within bounds
                int row = Math.max(0, Math.min(piece.getRow(), 8));
                int col = Math.max(0, Math.min(piece.getCol(), 8));

                // Calculate the position to draw the Bitmap
                float left = col * cellDimensions;
                float top = row * cellDimensions;

                // Draw the Bitmap on the canvas with an offset for the board margin
                canvas.drawBitmap(scaledBitmap, cellDimensions + left, top, null);
            }
        }
    }

    /**
     * Draws all captured Shogi pieces onto the canvas.
     *
     * @param canvas the Canvas on which to draw
     */
    private void drawCapturedPieces(Canvas canvas) {
        synchronized (capturedPieces) {
            for (int i = 0; i < capturedPieces.size(); i++) {
                ShogiPiece piece = capturedPieces.get(i);
                Bitmap scaledBitmap = scaledCapturedBitmaps.get(i);

                if (scaledBitmap == null) continue; // Skip if bitmap is missing

                // Calculate the position to draw the Bitmap
                float left;
                float top = piece.getRow() * cellDimensions;

                // Determine side based on owner (assuming owner 1 is Player 1, owner 2 is Player 2)
                if (piece.getOwner() == 1) { // Player 1's captured pieces
                    left = 10f * cellDimensions;
                } else { // Player 2's captured pieces
                    left = 0f;
                }

                // Draw the Bitmap on the canvas
                canvas.drawBitmap(scaledBitmap, left, top, null);
            }
        }
    }

    /**
     * Recycles all scaled bitmaps to free memory.
     */
    private void recycleBitmaps() {
        // Recycle active Shogi piece bitmaps
        for (Bitmap bitmap : scaledShogiBitmaps) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        scaledShogiBitmaps.clear();

        // Recycle captured Shogi piece bitmaps
        for (Bitmap bitmap : scaledCapturedBitmaps) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        scaledCapturedBitmaps.clear();
    }
}
