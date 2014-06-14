package zer.client;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * @author Nicolas Morel.
 */
public class EmptyCell extends AbstractCell {

    public EmptyCell(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public void setSelected(boolean selected) {
        // no-op
    }

    @Override
    public void draw(Context2d ctx) {
        // no-op
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmptyCell cell = (EmptyCell) o;

        if (col != cell.col) return false;
        if (row != cell.row) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        return result;
    }

    @Override
    public String toString() {
        return "EmptyCell{col=" + col + ", row=" + row + '}';
    }
}
