/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

/**
 *
 * @author itzfeltrin
 */
public abstract class ChessPiece extends Piece {

    private Color color;
    
    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
    
    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }
    
    protected boolean isThereOponentPiece(Position pos){
        ChessPiece p = (ChessPiece) getBoard().piece(pos);
        return p != null && p.getColor() != color;
    }
}
