package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

public class ChessController implements chess.ChessController {
    private ChessView view;
    private PlayerColor turn;
    @Override
    public void start(ChessView view) {
        this.view = view;
        view.startView();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        return false;
    }

    @Override
    public void newGame() {
        view.putPiece(PieceType.ROOK, PlayerColor.WHITE, 0, 0);
        view.putPiece(PieceType.BISHOP, PlayerColor.WHITE, 1,0);
        view.putPiece(PieceType.KNIGHT, PlayerColor.WHITE, 2,0);
        view.putPiece(PieceType.QUEEN, PlayerColor.WHITE, 3,0);
        view.putPiece(PieceType.KING, PlayerColor.WHITE, 4,0);
        view.putPiece(PieceType.KNIGHT, PlayerColor.WHITE, 5,0);
        view.putPiece(PieceType.BISHOP, PlayerColor.WHITE, 6,0);
        view.putPiece(PieceType.ROOK, PlayerColor.WHITE, 7,0);
        PlayerColor c = PlayerColor.WHITE;

        for(int i = 0; i < 2; ++i){
            for(int j = 0; j < 8;++j){
                view.putPiece(PieceType.PAWN,c,j,1 + 5*i);
            }
            c = PlayerColor.BLACK;
        }

        view.putPiece(PieceType.ROOK, PlayerColor.BLACK, 0, 7);
        view.putPiece(PieceType.BISHOP, PlayerColor.BLACK, 1,7);
        view.putPiece(PieceType.KNIGHT, PlayerColor.BLACK, 2,7);
        view.putPiece(PieceType.QUEEN, PlayerColor.BLACK, 3,7);
        view.putPiece(PieceType.KING, PlayerColor.BLACK, 4,7);
        view.putPiece(PieceType.KNIGHT, PlayerColor.BLACK, 5,7);
        view.putPiece(PieceType.BISHOP, PlayerColor.BLACK, 6,7);
        view.putPiece(PieceType.ROOK, PlayerColor.BLACK, 7,7);
    }
}
