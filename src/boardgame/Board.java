/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgame;

/**
 *
 * @author itzfeltrin
 */
public class Board {
    
    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
    
    public Piece piece(int row, int column){
        return this.pieces[row][column];
    }
    
    public Piece piece(Position position){
        return this.pieces[position.getRow()][position.getColumn()];
    }
    
    public void placePiece(Piece piece, Position position){
        this.pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }
}
