package ui;

import model.global;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.StateBasedEntityManager;

import java.awt.*;

/**
 * @author Timo Bähr
 *
 * Diese Klasse startet das Spiel "Drop of Water". Es enthaelt
 * zwei State's für das Menue und das eigentliche Spiel.
 */
public class Launch extends StateBasedGame {
    
    public Launch()
    {
        super("Broken friendships");
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
        AppGameContainer app = new AppGameContainer(new Launch());
 
        // Lege die Einstellungen des Fensters fest und starte das Fenster
        app.setDisplayMode(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, false);
        app.start();
    }

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		
		// Fuege dem StateBasedGame die States hinzu 
		// (der zuerst hinzugefuegte State wird als erster State gestartet)
        GameplayState gameplayState = new GameplayState();
		addState(new MainMenuState());
        addState(gameplayState);

        
        // Fuege dem StateBasedEntityManager die States hinzu
        StateBasedEntityManager.getInstance().addState(global.MAINMENU_STATE);
        StateBasedEntityManager.getInstance().addState(global.GAMEPLAY_STATE);
		
	}
}