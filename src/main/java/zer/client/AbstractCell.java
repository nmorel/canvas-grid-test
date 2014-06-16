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
        return col * (getSquareWidth() + getSepWidth()) + getSepWidth();
    }

    public int getXEnd() {
        return getXStart() + getSquareWidth();
    }

    public int getYStart() {
        return row * (getSquareHeight() + getSepWidth()) + getSepWidth();
    }

    public int getYEnd() {
        return getYStart() + getSquareHeight();
    }

    public abstract boolean isEmpty();

    public abstract boolean isSelected();

    public abstract void setSelected(boolean selected);

    public abstract void draw(Context2d ctx);
}
