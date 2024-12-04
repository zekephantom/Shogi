package edu.up.cs301.Shogi;

import java.util.ArrayList;

/**
 * External Citation
 * Date: 3 December 2024
 * Problem: Find a simple way on how to use the same variable inside
 * ShogiHumanPlayer as typecasting was unsuccessful
 * Resources: ChatGPT
 * Solution: recommend the use of an interface which is implemented below
 */

public interface ShogiGUIBase {
    void setShogiState(ShogiState state);
    void setSelected(ShogiSquare selected);
    void setPossibleMoves(ArrayList<ShogiSquare> possible);
    void setPriorMoveSquares(ShogiSquare orig, ShogiSquare target);
    ShogiSquare gridSelection(float x, float y);
    void setLanguage(boolean isEng);
}
