package tests.adapter;


public class BFTestAdapterExtended2 extends BFTestAdapterExtended1 {

    /* ***************************************************
     * ****** Initialisierung der Mystery Objekte ********
     * *************************************************** */

    /**
     * Gebe die Anzahl an Mystery Objekten zurück, die auf dem Feld sind.
     *
     * @return Anzahl der Mytsery objekten
     */
    public int getNumberOfMysteryObjects() {
        return -1;
    }

    /**
     * Spinge zum nächsten Spieler.
     */
    public void skipTurn() {

    }

    /**
     * Erzwinge das Erscheinen eines Mystery Objektes.
     *
     * @param index An welchem Feld Index das Objekt erscheinen soll.
     */
    public void spawnMystery(int index) {
    }

    /**
     * Bewege eine Figure zu einer gewissen Position.
     *
     * @param playerID ID des Spielers
     * @param from     ID des Feldes von welchem getauscht wird
     * @param to       ID des Feldes zu welchem getauscht wird
     */
    public void moveTo(int playerID, int from, int to) {
    }


    /* ***************************************************
     * *********** Test der simplen Specials *************
     * *************************************************** */

    /**
     * Erzwinge die Acivierung eines simplen Special Objektes.
     *
     * @param type     Typ des Specials
     * @param playerID ID des Spielers
     * @param figureID ID der Figur
     */
    public void forceSimpleSpecialActivation(String type, int playerID, int figureID) {
        switch (type) {
            case "dead" -> {
                //TODO
            }
            case "moveOut" -> {
                //TODO
            }
            case "growing" -> {
                //TODO
            }
        }
    }

    /* ***************************************************
     * ************ Test des CE Abschnittes **************
     * *************************************************** */

    /**
     * Initialisiere das Glücksrad.
     */
    public void setupWheelOfFortune() {
    }

    /**
     * Setze die initialkraft für das Glücksrad.
     *
     * @param initialForce die Initialkraft
     */
    public void setInitialForce(int initialForce) {

    }

    /**
     * Gebe den nächsten Gradwert zurück.
     *
     * @param degreeT1 Gradwert zur Zeit T1.
     * @param degreeT0 Gradwert zur Zeit T0.
     * @return neuen Gradwert
     */
    public double nextDegree(double degreeT1, double degreeT0) {
        return -1d;
    }

    /**
     * Setze den Reibungskonstanten für das Glücksrad.
     *
     * @param constant der Reibungskonstant
     */
    public void setFrictionConstant(int constant) {

    }
}
