/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import boardgame.Position;

/**
 *
 * @author itzfeltrin
 */
public class ChessPosition {
    private char column;
    private int row;
    
    public ChessPosition(char column, int row){
        if(column < 'a' || column > 'h' || row < 1 || row > 8) {
            throw new ChessException(("Error instantiating ChessPosition. Valid values are from a1 to h8."));
        }
        this.column = column;
        this.row = row;
    }

    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
    
    protected Position toPosition(){
        return new Position(8 - this.row, this.column - 'a');
    }
    
    protected static ChessPosition fromPosition(Position position){
        return new ChessPosition((char)('a' + position.getColumn()), 8 - position.getRow());
    }
    
    @Override
    public String toString(){
        return "" + column + row;
    }
}
