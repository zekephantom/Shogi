package edu.up.cs301.Shogi;

import java.util.Objects;

/**
 * This class represents a square on the Shogi board
 * It just holds a x and y for the row and col respectively
 *
 * @author Ezekiel Rafanan
 * @author Jona Bodirsky
 * @author Arnaj Sandhu
 * @author Makengo Lokombo
 * @author James Pham
 *
 * @version November 2024
 */

public class ShogiSquare {
	private int row;
	private int col;

	public ShogiSquare (int row, int col) {
		this.row = row;
		this.col = col;
	}

	public ShogiSquare (ShogiSquare copy) {
		this.row = copy.getRow();
		this.col = copy.getCol();
	}

	@Override
	public ShogiSquare clone () {
		return new ShogiSquare(this.row, this.col);
	}

	/**
	 * Adds the position of another ShogiSquare to this one
	 *
	 * @param toAdd the ShogiSquare to add
	 */
	public void add(ShogiSquare toAdd) {
		this.row += toAdd.getRow();
		this.col += toAdd.getCol();
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public boolean equals(ShogiSquare other) {
		return getRow() == other.getRow() && getCol() == other.getCol();
	}

	@Override
	public String toString() {
		return "" + row + "," + col;
	}
}