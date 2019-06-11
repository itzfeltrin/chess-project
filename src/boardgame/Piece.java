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
public abstract class Piece {
    protected Position position;
    private Board board;

    public Piece(Board board) {
        this.board = board;
        this.position = null;                
    }

    protected Board getBoard() {
        return this.board;
    }

    public abstract boolean[][] possibleMoves();
    
    public boolean possibleMove(Position pos){
        return possibleMoves()[pos.getRow()][pos.getColumn()];
    }
    
    public boolean isThereAnyPossibleMove(){
        boolean[][] mat = possibleMoves();
        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat.length; j++){
                if(mat[i][j]){
                    return true;
                }                
            }
        }
        return false;
    }
        
}
