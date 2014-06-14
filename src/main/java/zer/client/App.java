package zer.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import static zer.client.Constants.*;


public class App implements EntryPoint {

    private static final Logger logger = Logger.getLogger("Canvas");

    private static AppUiBinder binder = GWT.create(AppUiBinder.class);

    interface AppUiBinder extends UiBinder<HTMLPanel, App> {
    }

    private static interface CellFunction {
        void execute(AbstractCell cell);
    }

    private final AbstractCell[][] cells = new AbstractCell[NB_ROW][NB_COL];
    private AbstractCell lastCellOver;

    @UiField
    DivElement canvasContainer;

    @UiField
    Canvas mainCanvas;

    @UiField
    Canvas hoverCanvas;

    @Override
    public void onModuleLoad() {

        Widget root = binder.createAndBindUi(this);
        canvasContainer.getStyle().setWidth(CANVAS_WIDTH, Style.Unit.PX);
        canvasContainer.getStyle().setHeight(CANVAS_HEIGHT, Style.Unit.PX);
        RootPanel.get().add(root);

        for (int i = 0; i < NB_ROW; i++) {
            for (int j = 0; j < NB_COL; j++) {
                if (Random.nextBoolean()) {
                    cells[i][j] = new EmptyCell(i, j);
                } else {
                    cells[i][j] = new Cell(i, j);
                }
            }
        }

        mainCanvas.setCoordinateSpaceWidth(CANVAS_WIDTH);
        mainCanvas.setCoordinateSpaceHeight(CANVAS_HEIGHT);
        hoverCanvas.setCoordinateSpaceWidth(CANVAS_WIDTH);
        hoverCanvas.setCoordinateSpaceHeight(CANVAS_HEIGHT);

        final Context2d ctx = mainCanvas.getContext2d();

        ctx.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        ctx.setStrokeStyle("black");
        ctx.setLineWidth(SEP_WIDTH);
        ctx.beginPath();

        int x;
        int y;
        for (int i = 1; i <= NB_COL; i++) {

            x = i * SQUARE_WIDTH + i * SEP_WIDTH;
            y = 0;
            ctx.moveTo(x, y);
            ctx.lineTo(x, y + CANVAS_HEIGHT);

        }

        for (int j = 1; j <= NB_ROW; j++) {

            x = 0;
            y = j * SQUARE_HEIGHT + j * SEP_WIDTH;

            ctx.moveTo(x, y);
            ctx.lineTo(x + CANVAS_WIDTH, y);

        }

        ctx.closePath();
        ctx.stroke();

        executeOnAllCell(new CellFunction() {
            @Override
            public void execute(AbstractCell cell) {
                cell.draw(ctx);
            }
        });

        hoverCanvas.addMouseMoveHandler(new MouseMoveHandler() {
            @Override
            public void onMouseMove(MouseMoveEvent event) {
                onCellOver(findCellFromMouseEvent(event));
            }
        });

        hoverCanvas.addDragOverHandler(new DragOverHandler() {
            @Override
            public void onDragOver(DragOverEvent event) {
                onCellOver(findCellFromNativeEvent(event.getNativeEvent()));
            }
        });

        hoverCanvas.addMouseOutHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(MouseOutEvent event) {
                Context2d ctx = hoverCanvas.getContext2d();
                ctx.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
                ctx.fill();
                lastCellOver = null;
            }
        });

        hoverCanvas.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onCellClicked(findCellFromMouseEvent(event), event.isControlKeyDown());
            }
        });
    }

    private void onCellClicked(AbstractCell clickedCell, boolean append) {
        logger.info("Cell clicked : " + clickedCell);
        final Set<AbstractCell> cellsToRedraw = new HashSet<>();

        boolean oldSelected = clickedCell.isSelected();

        // si l'on a fait un click simple, il faut tout désélectionner
        if (!append) {
            executeOnAllCell(new CellFunction() {
                @Override
                public void execute(AbstractCell cell) {
                    if (cell.isSelected()) {
                        cell.setSelected(false);
                        cellsToRedraw.add(cell);
                    }
                }
            });
        }

        if (!append && cellsToRedraw.size() > 1) {
            clickedCell.setSelected(true);
        } else {
            clickedCell.setSelected(!oldSelected);
        }
        cellsToRedraw.add(clickedCell);

        Context2d ctx = mainCanvas.getContext2d();
        for (AbstractCell cellToRedraw : cellsToRedraw) {
            cellToRedraw.draw(ctx);
        }
    }

    private AbstractCell findCellFromMouseEvent(MouseEvent<?> event) {
        int row = (event.getY() / (SQUARE_HEIGHT + SEP_WIDTH));
        int col = (event.getX() / (SQUARE_WIDTH + SEP_WIDTH));
        return cells[row][col];
    }

    private AbstractCell findCellFromNativeEvent(NativeEvent event) {
        int row = ((event.getClientY() + Window.getScrollTop() - canvasContainer.getOffsetTop()) / (SQUARE_HEIGHT + SEP_WIDTH));
        int col = ((event.getClientX() + Window.getScrollLeft() - canvasContainer.getOffsetLeft()) / (SQUARE_WIDTH + SEP_WIDTH));
        return cells[row][col];
    }

    private void onCellOver(AbstractCell cell) {
        if (Objects.equals(cell, lastCellOver)) {
            return;
        }
        lastCellOver = cell;

        logger.info(cell.getRow() + ":" + cell.getColumn());
        if (cell.isEmpty()) {
            hoverCanvas.getElement().getStyle().clearCursor();
        } else {
            hoverCanvas.getElement().getStyle().setCursor(Style.Cursor.POINTER);
        }

        Context2d ctx = hoverCanvas.getContext2d();

        ctx.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        if (!cell.isEmpty()) {
            ctx.setFillStyle("yellow");
            ctx.setGlobalAlpha(0.5);

            ctx.beginPath();
            ctx.fillRect(cell.getXStart(), 0, SQUARE_WIDTH - SEP_WIDTH, cell.getYStart());
            ctx.fillRect(0, cell.getYStart(), cell.getXStart(), SQUARE_HEIGHT - SEP_WIDTH);
            ctx.fillRect(cell.getXStart() - SEP_WIDTH, cell.getYStart() - SEP_WIDTH, SQUARE_WIDTH, SQUARE_HEIGHT);
            ctx.closePath();
        }

        ctx.fill();
    }

    @UiFactory
    Canvas createCanvas() {
        return Canvas.createIfSupported();
    }

    private void executeOnAllCell(CellFunction fct) {
        for (int i = 0; i < NB_ROW; i++) {
            for (int j = 0; j < NB_COL; j++) {
                fct.execute(cells[i][j]);
            }
        }
    }

}
