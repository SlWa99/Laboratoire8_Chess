/**
 *  -----------------------------------------------------------------------------------
 * @Fichier      : Main.java
 * @Labo         : Laboratoire 8 : Chess
 * @Authors      : Slimani Walid & Baume Oscar
 * @Date         : 07.01.2023
 *
 * @Description  : Ce programme permet à deux personnes de s'affronter au jeu des échecs.
 *                 Une interface graphique est présente. Elle permet de représenter un
 *                 échiquier ainsi que le matériel nécessaire pour jouer une partie.
 *
 * @Remarque     : Les matches nuls par Pat ou par impossibilité de mater ne sont pas
 *                 implémenter.
 * @Modification : / Aucune modification
 *  -----------------------------------------------------------------------------------
 **/

import chess.ChessController;
import chess.ChessView;
import chess.views.gui.GUIView;

public class Main {
    public static void main(String[] args) {
        // 1. Création du contrôleur pour gérer le jeu d’échec
        ChessController controller = new engine.ChessController(); // Instancier un ChessController

        // 2. Création de la vue
        ChessView view = new GUIView(controller); // mode GUI
        // = new ConsoleView(controller); // mode Console

        // 3. Lancement du programme.
        controller.start(view);
    }
}