package zer.client;

/**
 * @author Nicolas Morel.
 */
public final class Constants {

    public static final int SQUARE_WIDTH = 40;
    public static final int SQUARE_HEIGHT = 30;
    public static final int SEP_WIDTH = 1;
    public static final int NB_ROW = 1000;
    public static final int NB_COL = 60;
    public static final int CANVAS_WIDTH = (NB_COL * SQUARE_WIDTH) + ((NB_COL - 1) * SEP_WIDTH);
    public static final int CANVAS_HEIGHT = (NB_ROW * SQUARE_HEIGHT) + ((NB_ROW - 1) * SEP_WIDTH);
    public static final String[] BG_COLORS = new String[]{"#00CC66", "#C2C2B2"};

    private Constants() {
    }
}
