package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();
    
    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }
    
    public int getTurn(){
        return this.turn;
    }
    
    public Color getCurrentPlayer(){
        return this.currentPlayer;
    }
    
    public boolean getCheck() {
        return this.check;
    }

    public boolean getCheckMate() {
        return this.checkMate;
    }
    
    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }
    
    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }
    
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        
        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }
        
        check = (testCheck(opponent(currentPlayer))) ? true : false;
        
        if (testCheckMate(opponent(currentPlayer))) {
            this.checkMate = true;
        }
        else {
            nextTurn();
        }
        
        return (ChessPiece) capturedPiece;
    }
    
    private Piece makeMove(Position source, Position target){
        ChessPiece p = (ChessPiece)board.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);
        if(capturedPiece != null){
            this.piecesOnTheBoard.remove(capturedPiece);
            this.capturedPieces.add(capturedPiece);
        }
        return capturedPiece;
    }
    
    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece)board.removePiece(target);
        p.decreaseMoveCount();
        board.placePiece(p, source);
        
        if(capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }
    
    private void validateSourcePosition(Position pos){
        if(!board.thereIsAPiece(pos)){
            throw new ChessException("There is no piece on source position.");
        }
        if(this.currentPlayer != ((ChessPiece)board.piece(pos)).getColor()){
            throw new ChessException("The chosen piece is not yours!");
        }
        if(!board.piece(pos).isThereAnyPossibleMove()){
            throw new ChessException("There is no possible moves for the chosen piece.");
        }
    }
    
    private void validateTargetPosition(Position source, Position target){
        if(!board.piece(source).possibleMove(target)){
            throw new ChessException("The chosen piece can't move to target position.");
        }
    }
    
    private void nextTurn(){
        turn++;
        this.currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }
    
    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color) {
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for(Piece p : list){
            if(p instanceof King) {
                return (ChessPiece)p;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }
    
    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }        
        return false;
    }
    
    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }        
        List<Piece> list = this.piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list) {
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece)p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source,target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!testCheck) {
                            return false;
                        }                        
                    }
                }
            }
        }
        return true;
    }
    
    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        this.piecesOnTheBoard.add(piece);
    }

    private void initialSetup() {
        placeNewPiece('h', 7, new Rook(this.board, Color.WHITE));
        placeNewPiece('d', 1, new Rook(this.board, Color.WHITE));
        placeNewPiece('e', 1, new King(this.board, Color.WHITE));
        
        placeNewPiece('b', 8, new Rook(this.board, Color.BLACK));
        placeNewPiece('a', 8, new King(this.board, Color.BLACK));
    }
}
