/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

/**
 *
 * @author itzfeltrin
 */
public class ChessMatch {
    
    private Board board;
    
    public ChessMatch(){
        this.board = new Board(8, 8);
        initialSetup();
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
    
    private void initialSetup(){
        board.placePiece(new Rook(this.board, Color.WHITE), new Position(2, 1));
        board.placePiece(new King(this.board, Color.BLACK), new Position(0, 4));
        board.placePiece(new King(this.board, Color.WHITE), new Position(7, 4));
    }
    
}