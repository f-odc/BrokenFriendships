package ui;

import model.global;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.StateBasedEntityManager;

/**
 * @author Timo Bähr
 *
 * Diese Klasse startet das Spiel "Drop of Water". Es enthaelt
 * zwei State's für das Menue und das eigentliche Spiel.
 */
public class BrokenFriendships extends StateBasedGame {

    public static boolean debug;
    
    public BrokenFriendships(boolean debugging)
    {
        super("Broken friendships");
        debug = debugging;
    }
 
    public static void main(String[] args) throws SlickException
    {
    	// Setze den library Pfad abhaengig vom Betriebssystem
    	if (System.getProperty("os.name").toLowerCase().contains("windows")) {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/windows");
	    } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/macosx");
    	} else {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/" +System.getProperty("os.name").toLowerCase());
    	}

        global.entityManager = StateBasedEntityManager.getInstance();
    	
    	// Setze dieses StateBasedGame in einen App Container (oder Fenster)
        AppGameContainer app = new AppGameContainer(new BrokenFriendships(false));
 
        // Lege die Einstellungen des Fensters fest und starte das Fenster
        app.setDisplayMode(global.X_DIMENSIONS, global.Y_DIMENSIONS, true);
        app.start();
    }

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		
		// Fuege dem StateBasedGame die States hinzu 
		// (der zuerst hinzugefuegte State wird als erster State gestartet)
        GameplayState gameplayState = new GameplayState();
		addState(new MainMenuState());
        addState(gameplayState);
        addState(new OnPauseMenuState(global.PAUSE_STATE));

        
        // Fuege dem StateBasedEntityManager die States hinzu
        StateBasedEntityManager.getInstance().addState(global.MAINMENU_STATE);
        StateBasedEntityManager.getInstance().addState(global.GAMEPLAY_STATE);
        StateBasedEntityManager.getInstance().addState(global.PAUSE_STATE);
		
	}
}