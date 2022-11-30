package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

public class ChessController implements chess.ChessController {
    @Override
    public void start(ChessView view) {
        view.startView();

        view.putPiece(PieceType.ROOK, PlayerColor.WHITE, 0, 1);
        view.putPiece(PieceType.ROOK, PlayerColor.WHITE, 1, 1);

    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        return false;
    }

    @Override
    public void newGame() {

    }
}
