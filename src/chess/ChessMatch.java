/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import boardgame.Board;

/**
 *
 * @author itzfeltrin
 */
public class ChessMatch {
    
    private Board board;
    
    public ChessMatch(){
        this.board = new Board(8, 8);
    }
    
    public ChessPiece[][] getPieces(){
        ChessPiece[][] mat = new ChessPiece[this.board.getRows()][this.board.getColumns()];
        for(int i = 0; i < this.board.getRows(); i++){
            for(int j = 0; j < this.board.getColumns(); j++){
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }
    
}
