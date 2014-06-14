package zer.client;

import com.google.gwt.canvas.dom.client.Context2d;

import static zer.client.Constants.*;

/**
 * @author Nicolas Morel.
 */
public abstract class AbstractCell {
    protected final int row;
    protected final int col;

    public AbstractCell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return col;
    }

    public int getXStart() {
        return col * (SQUARE_WIDTH + SEP_WIDTH) + SEP_WIDTH;
    }

    public int getXEnd() {
        return getXStart() + SQUARE_WIDTH;
    }

    public int getYStart() {
        return row * (SQUARE_HEIGHT + SEP_WIDTH) + SEP_WIDTH;
    }

    public int getYEnd() {
        return getYStart() + SQUARE_HEIGHT;
    }

    public abstract boolean isEmpty();

    public abstract boolean isSelected();

    public abstract void setSelected(boolean selected);

    public abstract void draw(Context2d ctx);
}
