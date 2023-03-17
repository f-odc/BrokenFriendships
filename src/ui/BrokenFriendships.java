package ui;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


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


        // Fuehren Sie hier (falls noetig), weitere Initialisierungen ein
        //TODO Initialisierungen

    	// Setze dieses StateBasedGame in einen App Container (oder Fenster)
        AppGameContainer app = new AppGameContainer(new BrokenFriendships(false));

        //Starte das Spiel
        app.start();
    }

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {

        // Fuege dem StateBasedGame die State's hinzu
        // TODO State's hinzu fuegen, z.B. this.addState(new MainMenuState(MAINMENUSTATE));

        // Fuege dem StateBasedEntityManager die State's hinzu
        // TODO State's hinzu fuegen, z.B. StateBasedEntityManager.getInstance().addState(MAINMENUSTATE);
	}
}