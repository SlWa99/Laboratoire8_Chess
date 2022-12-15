package engine.util;
import chess.ChessView;
import engine.Piece;

abstract public class PromotionChoice implements ChessView.UserChoice {
    // region Methods
    public abstract Piece create();
    // endregion
}
