package zer.client;

/**
 * @author Nicolas Morel.
 */
public final class Constants {

    private static final int SQUARE_WIDTH = 40;
    private static final int SQUARE_HEIGHT = 30;
    private static final int SEP_WIDTH = 1;
    private static int NB_ROW = 100;
    private static int NB_COL = 60;
    private static final String[] BG_COLORS = new String[]{"#00CC66", "#C2C2B2"};

    public static int getNbRow() {
        return NB_ROW;
    }

    public static void setNbRow(int nbRow) {
        NB_ROW = nbRow;
    }

    public static int getNbColumn() {
        return NB_COL;
    }

    public static void setNbColumn(int nbColumn) {
        NB_COL = nbColumn;
    }

    public static int getSquareWidth() {
        return SQUARE_WIDTH;
    }

    public static int getSquareHeight() {
        return SQUARE_HEIGHT;
    }

    public static int getSepWidth() {
        return SEP_WIDTH;
    }

    public static int getCanvasWidth() {
        return (getNbColumn() * getSquareWidth()) + ((getNbColumn() - 1) * getSepWidth());
    }

    public static int getCanvasHeight() {
        return (getNbRow() * getSquareHeight()) + ((getNbRow() - 1) * getSepWidth());
    }

    public static String[] getBackgroundColors() {
        return BG_COLORS;
    }

    private Constants() {
    }
}
