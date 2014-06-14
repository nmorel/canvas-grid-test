package zer.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Random;

import static zer.client.Constants.*;

/**
 * @author Nicolas Morel.
 */
public class Cell extends AbstractCell {
    private boolean selected;
    private boolean stripes = Random.nextBoolean();
    private String background = BG_COLORS[Random.nextInt(BG_COLORS.length)];
    private boolean square = Random.nextBoolean();
    private int num = Random.nextInt(301);

    public Cell(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void draw(Context2d ctx) {
        {
            // clearing the square
            ctx.beginPath();
            ctx.clearRect(getXStart(), getYStart(), SQUARE_WIDTH - SEP_WIDTH, SQUARE_HEIGHT - SEP_WIDTH);
            ctx.closePath();
            ctx.fill();
        }

        {
            // applying background color
            ctx.beginPath();
            ctx.setFillStyle(background);
            ctx.fillRect(getXStart(), getYStart(), SQUARE_WIDTH - SEP_WIDTH, SQUARE_HEIGHT - SEP_WIDTH);
            ctx.closePath();
            ctx.fill();
        }

        {
            // writing some text
            ctx.beginPath();
            ctx.setTextAlign(Context2d.TextAlign.CENTER);
            ctx.setTextBaseline(Context2d.TextBaseline.MIDDLE);
            ctx.setFillStyle("black");
            ctx.fillText(Integer.toString(num), getXStart() + (SQUARE_WIDTH / 2), getYStart() + (SQUARE_HEIGHT / 2));
            ctx.closePath();
            ctx.fill();
        }

        if (square) {
            // drawing a square on the middle
            ctx.beginPath();
            ctx.setFillStyle("#663300");
            ctx.fillRect(getXStart() + (SQUARE_WIDTH / 2) - 5, getYStart() + (SQUARE_HEIGHT / 2) - 5, 10, 10);
            ctx.closePath();
            ctx.fill();
        }

        if (stripes) {
            // drawing stripes
            ctx.beginPath();
            ctx.setStrokeStyle("orange");
            ctx.setLineWidth(1);

            int lines = 5;
            for (int i = 1; i < lines; i++) {

                ctx.moveTo(getXStart() + ((SQUARE_WIDTH / lines) * i), getYStart() + 1);
                ctx.lineTo(getXEnd() - 1, getYEnd() - ((SQUARE_HEIGHT / lines) * i));

                ctx.moveTo(getXStart() + 1, getYStart() + ((SQUARE_HEIGHT / lines) * i));
                ctx.lineTo(getXEnd() - ((SQUARE_WIDTH / lines) * i), getYEnd() - 1);

            }

            ctx.moveTo(getXStart() + 1, getYStart() + 1);
            ctx.lineTo(getXEnd() - 1, getYEnd() - 1);

            ctx.closePath();

            ctx.stroke();
        }

        if (selected) {
            // drawing a large border
            ctx.beginPath();
            ctx.setStrokeStyle("blue");
            ctx.setLineWidth(2);
            ctx.moveTo(getXStart() + 1, getYStart() + 1);
            ctx.lineTo(getXEnd() - 2, getYStart() + 1);
            ctx.lineTo(getXEnd() - 2, getYEnd() - 2);
            ctx.lineTo(getXStart() + 1, getYEnd() - 2);
            ctx.lineTo(getXStart() + 1, getYStart() + 1);
            ctx.closePath();
            ctx.stroke();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

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
        return "Cell{col=" + col + ", row=" + row + '}';
    }


}
