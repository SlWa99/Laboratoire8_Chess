package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.util.Coord;
import engine.util.PromotionChoice;

import java.util.LinkedList;
import java.util.List;

public class ChessController implements chess.ChessController {
    private final int SIZE = 8;
    // indique sur quelle colonne il y a eu un départ de 2 cases pour un pion
    private int pawnJumpStart = -1;
    private ChessView view;
    private PlayerColor turn;
    private Piece[][] board;
    private boolean checkMate;

    /**
     * Méthode qui initialise la vue et démarre une nouvelle partie
     * @param view la vue à utiliser
     */
    @Override
    public void start(ChessView view) {
        this.view = view;
        view.startView();
        newGame();
    }

    /**
     * Méthode qui gère les mouvements
     * @param fromX position x de départ
     * @param fromY position y de départ
     * @param toX position x de déstination
     * @param toY position y de déstination
     * @return boolean qui indique si un mouvement a été éffectué
     */
    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        if (checkMate) {
            view.displayMessage("Game finished, please start again");
            return false;
        }

        // si il n'y a pas de piece sur cette case. on ne pourra pas se déplacer
        if (!acceptMove(fromX, fromY, toX, toY)) {
            return false;
        }
        List<List<Coord>> moves = refactorListMove(getMoves(fromX, fromY, toX, toY));
        if (!findCoordInListMove(moves, toX, toY)) return false;
        // si gestion castle return false ça veut dire qu'on fait pas un roque (pas de déplacement)
        if (!gestionCastle(fromX, fromY, toX, toY)) {
            gestionEnPassant(fromX, fromY, toX, toY);
            movePiece(fromX, fromY, toX, toY);
            gestionPromotion(toX, toY);
        }

        // On vérifie qu'on ne se mette pas en échec tout seul
        if (checkSelfMat()) {
            view.displayMessage("Prohibited movement !");
            movePiece(toX, toY, fromX, fromY);
            return false;
        }

        // On regarde s'il y a échec et remplie une liste de toutes les pièces qui mettent échec
        LinkedList<Coord> attackers = new LinkedList<>();
        LinkedList<Coord> defenders = new LinkedList<>();
        if (checkMat(attackers)) {
            view.displayMessage("The " + (turn == PlayerColor.WHITE ? "BLACK" : "WHITE") + " king is in check !");

            canProtectHisKingByEating(attackers, defenders);
            canCoverHisKing(attackers, defenders);
            canMoveHisKing(attackers);
            canStrengthenAttack(attackers);

            if (defenders.size() < attackers.size()) {
                checkMate = true;
                view.displayMessage("Checkmate, " + (turn != PlayerColor.WHITE ? "BLACK" : "WHITE") + " wins !");
                return true;
            }

//            if (!canProtectHisKingByEating(attackers, defenders) && !canCoverHisKing(attackers, defenders) &&
//                    !canMoveHisKing(attackers) && defenders.size() < attackers.size()) {
//                checkMate = true;
//                view.displayMessage("Checkmate, " + (turn == PlayerColor.WHITE ? "BLACK" : "WHITE") + " wins !");
//                return true;
//            }
        }
        changeTurn();
        return true;
    }

    /**
     * Méthode qui va chercher un coordonnée dans une liste de liste de coordonnées
     * @param listMove liste de liste dans laquelle on cherche la coord (toX,toY)
     * @param toX postion x qu'on cherche
     * @param toY postion y qu'on cherche
     * @return
     */
    private boolean findCoordInListMove(List<List<Coord>> listMove, int toX, int toY) {
        Coord find = new Coord(toX, toY);
        for (List<Coord> list : listMove) {
            for (Coord c : list) {
                if (find.isEqual(c)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Méthiode qui va enlever les mouvements illicites
     * @param listMove la liste de mouvement que l'on veut remanier
     * @return la nouvelle liste sans les mouvements illicites
     */
    private List<List<Coord>> refactorListMove(List<List<Coord>> listMove) {
        List<List<Coord>> refactorListMove = new LinkedList<>();
        List<Coord> refactoredVect;
        for (List<Coord> vect : listMove) {
            refactoredVect = new LinkedList<>();
            for (Coord c : vect) {
                if (board[c.getX()][c.getY()] != null) {
                    if (board[c.getX()][c.getY()].getColor() != turn) {
                        refactoredVect.add(c);
                        if (vect.indexOf(c) < vect.size() && board[c.getX()][c.getY()].type != PieceType.KNIGHT) {
                            break;
                        }
                    } else if (board[c.getX()][c.getY()].type == PieceType.KING) {
                        refactoredVect.add(c);
                    } else {
                        break;
                    }
                } else {
                    refactoredVect.add(c);
                }
            }
            if (!refactoredVect.isEmpty()) {
                refactorListMove.add(refactoredVect);
            }
        }
        return refactorListMove;
    }

    /**
     * Méthode qui indique si un mouvement est correct,
     * si il y a une pièce à la coordonnées (fromX,fromY)
     * si la pièce à la bonne couleur
     * si cette pièce peut se déplacer vers (toX,toY)
     * si la destination n'est pas occupé ou est occupé par une pièce adverse
     * @param fromX position x de départ
     * @param fromY position y de départ
     * @param toX position x de déstination
     * @param toY position y de déstination
     * @return si le mouvement est accepté
     */
    private boolean acceptMove(int fromX, int fromY, int toX, int toY) {
        if (board[fromX][fromY] == null) return false;
        // si on essaie de deplacer une pièce de la mauvaise couleur
        if (board[fromX][fromY].getColor() != turn) return false;
        // si on ne peut pas se déplacer à la destination on indique cela
        if (!board[fromX][fromY].acceptedMove(toX, toY)) return false;
        // si on essaie de se déplacer sur une pièce de la même couleur que nous
        return board[toX][toY] == null || board[toX][toY].getColor() != turn;
    }

    /**
     * Méthode qui retourne la liste de mouvements correct pour une pièce donné
     * @param fromX position x de départ
     * @param fromY position y de départ
     * @param toX position x de déstination
     * @param toY position y de déstination
     * @return la liste de mouvements de la pièce qui se trouve à (fromX,fromY)
     */
    private List<List<Coord>> getMoves(int fromX, int fromY, int toX, int toY) {
        if (board[toX][toY] == null) {
            if (board[fromX][fromY].getType() == PieceType.PAWN && toX == pawnJumpStart && fromY == (turn == PlayerColor.WHITE ? 4 : 3)) {
                return board[fromX][fromY].listEatingMove();
            } else {
                return board[fromX][fromY].listMove();
            }
        } else {
            return board[fromX][fromY].listEatingMove();
        }
    }

    /**
     * Méthode qui gère la prise en passant
     * @param fromX position x de départ
     * @param fromY position y de départ
     * @param toX position x de déstination
     * @param toY position y de déstination
     */
    private void gestionEnPassant(int fromX, int fromY, int toX, int toY) {
        // check enpassant
        if (board[fromX][fromY].getType() == PieceType.PAWN && toX == pawnJumpStart && fromY == (turn == PlayerColor.WHITE ? 4 : 3)) {
            view.removePiece(pawnJumpStart, (turn == PlayerColor.WHITE ? 4 : 3));
            board[pawnJumpStart][(turn == PlayerColor.WHITE ? 4 : 3)] = null;
        }
        if (board[fromX][fromY].getType() == PieceType.PAWN && Math.abs(fromY - toY) == 2) {
            pawnJumpStart = fromX;
        } else {
            pawnJumpStart = -1;
        }
    }

    /**
     * Méthode qui gère le grand et petit roque
     * @param fromX position x de départ
     * @param fromY position y de départ
     * @param toX position x de déstination
     * @param toY position y de déstination
     * @return si on effectue un roque
     */
    private boolean gestionCastle(int fromX, int fromY, int toX, int toY) {
        // si on est en échec
        if(checkSelfMat()){
            // on ne peut pas effectué de roque
            return false;
        }
        // check si on a un roi et une tour de la même couleur et qui n'ont pas bougé
        if (board[fromX][fromY].getType() != PieceType.KING
                || board[toX][toY] != null
                || board[fromX][fromY].hasMoved
                || fromY != toY
                || !(toX == 1 || toX == 6)) return false;
        int castleX = (toX == Math.min(fromX, toX) ? 0 : 7);
        if (!(board[castleX][fromY] != null
                && board[castleX][fromY].getType() == PieceType.ROOK
                && board[castleX][fromY].getColor() == board[fromX][fromY].getColor()
                && !board[castleX][fromY].hasMoved)) return false;
        int left = Math.min(fromX, castleX);
        int right = Math.max(fromX, castleX);
        // vérif si la voie est libre entre le roi et la tour
        for (int i = left + 1; i < right; ++i) {
            if (board[i][fromY] != null) return false;
        }
        // on peut effectuer le rock
        // bouge le roi
        movePiece(fromX, fromY, toX, toY);
        // bouge la tour
        movePiece(castleX, fromY, toX + ((fromX - toX) / 2), toY);
        return true;
    }

    /**
     * Méthode qui change le tour
     */
    private void changeTurn() {
        turn = (turn == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE);
    }

    /**
     * Méthode qui gère le deplacement dans la view et dans le tableau
     * @param fromX position x de départ
     * @param fromY position y de départ
     * @param toX position x de déstination
     * @param toY position y de déstination
     */
    private void movePiece(int fromX, int fromY, int toX, int toY) {
        // déplacer la pièce au bon endroit
        board[fromX][fromY].move(toX, toY);
        board[toX][toY] = board[fromX][fromY];
        board[fromX][fromY] = null;
        view.removePiece(fromX, fromY);
        view.putPiece(board[toX][toY].getType(), board[toX][toY].getColor(), toX, toY);
    }

    /**
     * Méthode qui initialse une nouvelle partie
     */
    @Override
    public void newGame() {
        cleanGUI();
        checkMate = false;
        board = new Piece[SIZE][SIZE];
        turn = PlayerColor.WHITE;
        setBoard();
        setGUI();
    }

    /**
     * Méthode qui initialise le tableau de jeu
     */
    private void setBoard() {
        if (board == null) return;
        PlayerColor c = PlayerColor.WHITE;
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                board[i][j] = null;
            }
        }
        for (int i = 0; i < 2; ++i) {
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

    /**
     * Méthode qui nettoye la view
     */
    private void cleanGUI() {
        if (view == null) return;
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                view.removePiece(i, j);
            }
        }
    }

    /**
     * Méthode qui initialise la view avec les pièces
     */
    private void setGUI() {
        if (view == null || board == null) return;
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] == null) continue;
                view.putPiece(board[i][j].getType(), board[i][j].getColor(), i, j);
            }
        }
    }

    /**
     * Méthode qui gère la promotion de pion
     * @param toX position x de déstination
     * @param toY position y de déstination
     */
    private void gestionPromotion(int toX, int toY) {
        if (board[toX][toY].getType() == PieceType.PAWN) {
            if (turn == PlayerColor.WHITE && toY == SIZE - 1 || turn == PlayerColor.BLACK && toY == 0) {
                view.removePiece(toX, toY);
            } else return;
        } else return;

        PromotionChoice[] options = {
                new PromotionChoice() {
                    @Override
                    public String textValue() {
                        return "Queen";
                    }

                    @Override
                    public Piece create() {
                        return new Queen(new Coord(toX, toY), turn);
                    }
                },
                new PromotionChoice() {
                    @Override
                    public String textValue() {
                        return "Rook";
                    }

                    @Override
                    public Piece create() {
                        return new Rook(new Coord(toX, toY), turn);
                    }
                },
                new PromotionChoice() {
                    @Override
                    public String textValue() {
                        return "Bishop";
                    }

                    @Override
                    public Piece create() {
                        return new Bishop(new Coord(toX, toY), turn);
                    }
                },
                new PromotionChoice() {
                    @Override
                    public String textValue() {
                        return "Knight";
                    }

                    @Override
                    public Piece create() {
                        return new Knight(new Coord(toX, toY), turn);
                    }
                }
        };

        PromotionChoice result = view.askUser("Promotion", "Choose a piece", options);
        Piece p = result.create();
        board[toX][toY] = p;
        view.putPiece(p.getType(), p.getColor(), toX, toY);
    }

    /**
     * Méthode qui vérifie si on met en echec le roi adverse
     * @param attackers liste des coups de nos pièces
     * @return si il y a echec
     */
    private boolean checkMat(LinkedList<Coord> attackers) {
        // On cherche la position du roi adverse
        List<Coord> kingPos = findKings();
        Coord enemyKingPos = turn == PlayerColor.WHITE ? kingPos.get(1) : kingPos.get(0);

        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] != null && board[i][j].color == turn) {
                    // On récupère la liste des positions ou on peut manger une pièce adverse
                    List<List<Coord>> moves = board[i][j].listEatingMove();
                    List<List<Coord>> eatingMoves = refactorListMove(moves);

                    // On regarde si une pièce peut manger le roi adverse
                    if (findCoordInListMove(eatingMoves, enemyKingPos.getX(), enemyKingPos.getY())) {
                        attackers.add(board[i][j].coord);
                    }
                }
            }
        }
        return attackers.size() != 0;
    }

    /**
     * Méthode qui vérifie si l'adversaire nous met en echec
     * @return si on est en echec
     */
    private boolean checkSelfMat() {
        // On cherche la position de son roi
        List<Coord> kingPos = findKings();
        Coord allyKingPos = turn != PlayerColor.WHITE ? kingPos.get(1) : kingPos.get(0);

        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] != null && board[i][j].color != turn) {
                    // On récupère la liste des positions ou l'adversaire peut nous manger une pièce
                    List<List<Coord>> moves = board[i][j].listEatingMove();
                    List<List<Coord>> eatingMoves = refactorListMove(moves);

                    // On regarde si une pièce peut manger notre roi
                    if (findCoordInListMove(eatingMoves, allyKingPos.getX(), allyKingPos.getY())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Méthode qui retourne la position des rois
     * @return at(0) : roi blanc, at(1) : roi noir
     */
    private List<Coord> findKings() {   // list[0] = white, liste[1] = black
        List<Coord> kingsPos = new LinkedList<>();

        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] != null && board[i][j].type == PieceType.KING) {
                    if (board[i][j].color == PlayerColor.WHITE) {
                        if (kingsPos.size() == 2) {
                            kingsPos.add(0, board[i][j].coord);
                            kingsPos.remove(1);
                        } else {
                            kingsPos.add(0, board[i][j].coord);
                        }

                    } else if (board[i][j].color == PlayerColor.BLACK && kingsPos.size() == 0) {
                        Coord temp = new Coord(4, 0);
                        kingsPos.add(0, temp);  // Position par défaut du roi blanc (pour éviter de trier la liste la fin et permet d'avoir toujours le roi blanc à l'index 0. Et permet d'éviter une exception)
                        kingsPos.add(1, board[i][j].coord);
                        continue;
                    } else {
                        kingsPos.add(1, board[i][j].coord);
                    }
                    if (kingsPos.size() == 2) {
                        return kingsPos;
                    }
                }
            }
        }
        return null;
    }

    private boolean canProtectHisKingByEating(LinkedList<Coord> attackers, LinkedList<Coord> defenders) {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] != null && board[i][j].color != turn) {
                    List<List<Coord>> allyMoves = board[i][j].listEatingMove();
                    List<List<Coord>> defensiveMoveByEating = defensiveMoveByEating(allyMoves);

                    for (Coord c : attackers) {
                        if (findCoordInListMove(defensiveMoveByEating, c.getX(), c.getY())) {
                            if (!defenders.contains(board[i][j].coord)) {
                                defenders.add(board[i][j].coord);
                            }
                        }
                    }
                }
            }
        }
        return defenders.size() != 0;
    }

    private List<List<Coord>> defensiveMoveByEating(List<List<Coord>> listMove) {
        List<List<Coord>> refactorListMove = new LinkedList<>();
        List<Coord> refactoredVect;
        for (List<Coord> vect : listMove) {
            refactoredVect = new LinkedList<>();
            for (Coord c : vect) {
                if (board[c.getX()][c.getY()] != null) {
                    if (board[c.getX()][c.getY()].getColor() == turn) {
                        refactoredVect.add(c);
                        break;
                    }
                    if (vect.indexOf(c) < vect.size() && board[c.getX()][c.getY()].type != PieceType.KNIGHT) {
                        break;
                    }
                }
            }
            if (!refactoredVect.isEmpty()) {
                refactorListMove.add(refactoredVect);
            }
        }
        return refactorListMove;
    }

    private boolean canCoverHisKing(LinkedList<Coord> attackers, LinkedList<Coord> defenders) {
        // On cherche la position du roi allié qui est attaqué
        List<Coord> kingPos = findKings();
        Coord allyKingPos = turn == PlayerColor.WHITE ? kingPos.get(1) : kingPos.get(0);

        List<Coord> listWhichContainsKingPos = new LinkedList<>();

        // On génére toute les positions des attaquants du roi
        for (Coord c : attackers) {
            List<List<Coord>> moves = board[c.getX()][c.getY()].listEatingMove();
            List<List<Coord>> eatingMove = refactorListMove(moves);

            // On cherche le vecteur qui va de la pièce attaquante au roi
            for (List<Coord> l : eatingMove) {
                for (Coord ck : l) {
                    if (ck.isEqual(allyKingPos)) {
                        listWhichContainsKingPos = l;
                        listWhichContainsKingPos.remove(listWhichContainsKingPos.size() - 1);
                        break;
                    }
                }
                if (listWhichContainsKingPos.size() != 0) {
                    break;
                }
            }

            // Pour toute les pièces alliés, on regarde si une pièce peut s'interrposer entre le roi et l'attaquant
            for (int i = 0; i < SIZE; ++i) {
                for (int j = 0; j < SIZE; ++j) {
                    if (board[i][j] != null && board[i][j].color != turn && board[i][j].type != PieceType.KING) {
                        List<List<Coord>> allyMoves = board[i][j].listEatingMove();
                        List<List<Coord>> defensiveMoveByMoving = defensiveMoveByCover(allyMoves); //mskn

                        if (defensiveMoveByMoving.size() == 0) {
                            continue;
                        }

                        for (Coord pos : listWhichContainsKingPos) {
                            if (findCoordInListMove(defensiveMoveByMoving, pos.getX(), pos.getY())) {
                                if (!defenders.contains(board[i][j].coord)) {
                                    defenders.add(board[i][j].coord);
                                }
                            }
                        }
                    }
                }
            }
        }
        return defenders.size() != 0;
    }

    private List<List<Coord>> defensiveMoveByCover(List<List<Coord>> listMove) {
        List<List<Coord>> refactorListMove = new LinkedList<>();
        List<Coord> refactoredVect;
        for (List<Coord> vect : listMove) {
            refactoredVect = new LinkedList<>();
            for (Coord c : vect) {
                if (board[c.getX()][c.getY()] == null) {
                    refactoredVect.add(c);
                }
                break;
            }
            if (!refactoredVect.isEmpty()) {
                refactorListMove.add(refactoredVect);
            }
        }
        return refactorListMove;
    }

    private boolean canMoveHisKing(LinkedList<Coord> attackers) {
        // On cherche la position du roi allié qui est attaqué
        List<Coord> kingPos = findKings();
        Coord allyKingPos = turn == PlayerColor.WHITE ? kingPos.get(1) : kingPos.get(0);

        // Génère les déplacements possibles du roi attaqué
        List<List<Coord>> allyMoves = board[allyKingPos.getX()][allyKingPos.getY()].listEatingMove();
        List<List<Coord>> defensiveMoveByMoving = defensiveMoveByCover(allyMoves);

        List<Coord> listWhichContainsKingPos = new LinkedList<>();

        // On génére toute les positions des attaquants du roi
        for (Coord c : attackers) {
            List<List<Coord>> moves = board[c.getX()][c.getY()].listEatingMove();
            List<List<Coord>> eatingMove = refactorListMove(moves);

            // On cherche le vecteur qui va de la pièce attaquante au roi
            for (List<Coord> l : eatingMove) {
                for (Coord ck : l) {
                    if (ck.isEqual(allyKingPos)) {
                        listWhichContainsKingPos = l;
                        listWhichContainsKingPos.remove(listWhichContainsKingPos.size() - 1);
                        break;
                    }
                }
                if (listWhichContainsKingPos.size() != 0) {
                    break;
                }
            }

            // On enlève les dépalcements du roi qui sont dans le vecteur d'attaque de l'attaquant
            for (List<Coord> l : defensiveMoveByMoving) {
                for (Coord kingMove : l) {
                    for (Coord enemyMove : listWhichContainsKingPos) {
                        if (enemyMove.isEqual(kingMove)) {
                            l.remove(kingMove);

                            if (l.size() == 0) {
                                defensiveMoveByMoving.remove(l);
                            }
                        }
                    }
                }
            }
        }
        return defensiveMoveByMoving.size() > 0;    // Si le roi ne peut pas se déplacer, il est maté
    }

    private boolean canStrengthenAttack(LinkedList<Coord> attackers) {
        int nbrAttackers = attackers.size();
        LinkedList<Coord> tmp = new LinkedList<>();
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] != null && board[i][j].color == turn) {
                    List<List<Coord>> allyMoves = board[i][j].listEatingMove();
                    List<List<Coord>> supportAttackMoves = supportAttackMove(allyMoves);
                    for (Coord c : attackers) {
                        for(List<Coord> l : supportAttackMoves) {
                            for(Coord s : l) {
                                if (s.isEqual(c)) {
                                    tmp.add(s);
                                }
                            }
                        }
                    }
                }
            }
        }
        attackers.addAll(tmp);
        return attackers.size() - nbrAttackers != 0;
    }

    private List<List<Coord>> supportAttackMove(List<List<Coord>> listMove) {
        List<List<Coord>> refactorListMove = new LinkedList<>();
        List<Coord> refactoredVect;
        for (List<Coord> vect : listMove) {
            refactoredVect = new LinkedList<>();
            for (Coord c : vect) {
                if (board[c.getX()][c.getY()] != null) {
                    if (board[c.getX()][c.getY()].getColor() == turn) {
                        refactoredVect.add(c);
                    }
                }
            }
            refactorListMove.add(refactoredVect);
        }
        return refactorListMove;
    }
}