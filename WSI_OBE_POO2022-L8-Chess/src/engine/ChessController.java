package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;

import java.util.LinkedList;
import java.util.List;

public class ChessController implements chess.ChessController {
    private final int SIZE = 8;
    // indique sur quelle colonne il y a eu un départ de 2 cases pour un pion
    private int pawnJumpStart = -1;
    private ChessView view;
    private PlayerColor turn;
    private Piece[][] board;
    @Override
    public void start(ChessView view) {
        this.view = view;
        view.startView();
        board = new Piece[SIZE][SIZE];
        newGame();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        //TODO
        // Verif echec et/ou mat
        // Si en echec forcé la protection
        // Rock

        // si il n'y a pas de piece sur cette case. on ne pourra pas se déplacer
        if(board[fromX][fromY] == null) return false;
        // si on essaie de deplacer une pièce de la mauvaise couleur
        if(board[fromX][fromY].getColor() != turn) return false;
        // si on ne peut pas se déplacer à la destination on indique cela
        if(!board[fromX][fromY].acceptedMove(toX,toY)) return false;
        List<List<Coord>> moves;
        if(board[toX][toY] == null){
            if(board[fromX][fromY].getType() == PieceType.PAWN && toX == pawnJumpStart && fromY == (turn == PlayerColor.WHITE?4:3)){
                moves = board[fromX][fromY].listEatingMove();
            } else {
                moves = board[fromX][fromY].listMove();
            }
        } else {
            // si on essaie de se déplacer sur une pièce de la même couleur que nous
            if(board[toX][toY].getColor() == turn) return false;
            moves = board[fromX][fromY].listEatingMove();
        }

        moves = refactorListMove(moves);
        if(!findCoordInListMove(moves,toX,toY)) return false;
        // check enpassant
        if(board[fromX][fromY].getType() == PieceType.PAWN && toX == pawnJumpStart && fromY == (turn == PlayerColor.WHITE?4:3)){
            view.removePiece(pawnJumpStart,(turn == PlayerColor.WHITE?4:3));
        }
        if(board[fromX][fromY].getType() == PieceType.PAWN && Math.abs(fromY - toY) == 2){
            pawnJumpStart = fromX;
        } else {
            pawnJumpStart = -1;
        }
        // déplacer la pièce au bon endroit
        board[fromX][fromY].move(toX,toY);
        board[toX][toY] = board[fromX][fromY];
        board[fromX][fromY] = null;
        view.removePiece(fromX,fromY);
        view.putPiece(board[toX][toY].getType(),board[toX][toY].getColor(),toX,toY);
        turn = (turn == PlayerColor.WHITE?PlayerColor.BLACK:PlayerColor.WHITE);
        view.displayMessage("Turn : " + (turn== PlayerColor.WHITE ?"WHITE":"BLACK"));
        return true;
    }

    public boolean findCoordInListMove(List<List<Coord>> listMove,int toX,int toY){
        Coord find = new Coord(toX,toY);
        for(List<Coord> list : listMove){
            for(Coord c : list){
                if(find.isEqual(c)){
                    return true;
                }
            }
        }
        return false;
    }
    public List<List<Coord>> refactorListMove(List<List<Coord>> listMove){
        List<List<Coord>> refactorListMove = new LinkedList<>();
        List<Coord> refactoredVect;
        for(List<Coord> vect : listMove){
            refactoredVect = new LinkedList<>();
            for(Coord c : vect){
                if(board[c.getX()][c.getY()] != null){
                    if(board[c.getX()][c.getY()].getColor() != turn){
                        refactoredVect.add(c);
                    } else {
                        break;
                    }
                } else {
                    refactoredVect.add(c);
                }
            }
            refactorListMove.add(refactoredVect);
        }
        return refactorListMove;
    }
    @Override
    public void newGame(){
        cleanGUI();
        board = new Piece[SIZE][SIZE];
        turn = PlayerColor.WHITE;
        setBoard();
        setGUI();
    }
    public void setBoard(){
        if(board == null) return;
        PlayerColor c = PlayerColor.WHITE;
        for(int i = 0;i < SIZE; ++i){
            for(int j = 0; j < SIZE; ++j){
                board[i][j] = null;
            }
        }
        for(int i = 0; i < 2; ++i) {
            board[0][(i * 7)] = new Rook(new Coord(0, i * 7), c);
            board[1][(i * 7)] = new Knight(new Coord(1, i * 7), c);
            board[2][(i * 7)] = new Bishop(new Coord(2, i * 7), c);
            board[3][(i * 7)] = new Queen(new Coord(3, i * 7), c);
            board[4][(i * 7)] = new King(new Coord(4, i * 7), c);
            board[5][(i * 7)] = new Bishop(new Coord(5, i * 7), c);
            board[6][(i * 7)] = new Knight(new Coord(6, i * 7), c);
            board[7][(i * 7)] = new Rook(new Coord(7, i * 7), c);
            for (int j = 0; j < SIZE; ++j) {
                board[j][1 + 5 * i] = new Pawn(new Coord(j, 1 + i * 5), c);
            }
            c = PlayerColor.BLACK;
        }
    }
    public void cleanGUI(){
        if(view == null) return;
        for(int i = 0;i < SIZE; ++i){
            for(int j = 0; j < SIZE; ++j){
                view.removePiece(i,j);
            }
        }
    }
    public void setGUI(){
        if(view == null || board == null) return;
        for(int i = 0;i < SIZE; ++i){
            for(int j = 0; j < SIZE; ++j){
                if(board[i][j] == null) continue;
                view.putPiece(board[i][j].getType(),board[i][j].getColor(),i,j);
            }
        }
    }

//    @Override
//    public <T extends ChessView.UserChoice> T askUser(String title, String question, T... possibilities) {
//    return null;
//    }
//
//    <T extends ChessView.UserChoice> T askUser (String title, String question, T ... possibilities);
//
//
//    private void promotion(int fromX, int fromY, int toX, int toY) {
//        if (turn == PlayerColor.WHITE && toY == SIZE) {
//           askUser("Promotion", "Take a piece", "Queen", "Rook", "Bishop", "Knight");
//
//        }
//    }
}
