package model.game;

import model.board.BoardManager;
import model.enums.Phase;
import model.game.logic.GameLogic;
import model.global;
import model.player.PlayerManager;

import java.util.List;

/**
 * Manage all game related features
 */
public class GameManager {

    /**
     * initializes the baord and the figures
     */
    public static void setup() {
        // setup board
        BoardManager.setup();
        // setup player
        PlayerManager.setup();
        // setup specials array
        global.specialsMap.add(List.of("model.board.objects.specials.BedSpecial", "assets/MysterySelection/bed.png"));
        global.specialsMap.add(List.of("model.board.objects.specials.BombSpecial", "assets/MysterySelection/bomb.png"));
        global.specialsMap.add(List.of("model.board.objects.specials.DeadSpecial", "assets/MysterySelection/dead.png"));
        global.specialsMap.add(List.of("model.board.objects.specials.GrowingSpecial", "assets/MysterySelection/growing.png"));
        global.specialsMap.add(List.of("model.board.objects.specials.MoveFourSpecial", "assets/MysterySelection/moveFour.png"));
        global.specialsMap.add(List.of("model.board.objects.specials.MoveOutSpecial", "assets/MysterySelection/moveOutHome.png"));
        global.specialsMap.add(List.of("model.board.objects.specials.PlusToThreeSpecial", "assets/MysterySelection/plusToThree.png"));
        global.specialsMap.add(List.of("model.board.objects.specials.SwitchSpecial", "assets/MysterySelection/switch.png"));
    }

    /**
     * starts the game
     */
    public static void start() {
        //set first players turn
        global.activePlayer = 0;
        global.rounds = 0;
        global.phase = Phase.DICE_PHASE;
        global.BOARD.getDice().setPosition(global.activePlayer);
        GameLogic.dice = global.BOARD.getDice();
    }
}
