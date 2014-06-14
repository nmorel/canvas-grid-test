package zer.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.widget.client.TextButton;

public class App implements EntryPoint {

    @Override
    public void onModuleLoad() {

        final Canvas canvas = Canvas.createIfSupported();
        canvas.setSize("600px", "600px");
        canvas.setCoordinateSpaceHeight(600);
        canvas.setCoordinateSpaceWidth(600);
        RootPanel.get().add(canvas);

        Button button = new Button("Toto");
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                Context2d mapGridCanvas = canvas.getContext2d();

                mapGridCanvas.clearRect(0, 0, 800, 400);
                mapGridCanvas.setGlobalAlpha(1);
                mapGridCanvas.setStrokeStyle("black");
                mapGridCanvas.setLineWidth(1);
                mapGridCanvas.beginPath();

                int x;
                int y;
                for(int i = 0;i < Math.round(800/40); i++){

                    x = i*40;
                    y = 0;
                    mapGridCanvas.moveTo(x, y);
                    mapGridCanvas.lineTo(x, y + 400);

                }

                for(int j = 0; j < Math.round(400/40); j++){

                    x = 0;
                    y = j*40;

                    mapGridCanvas.moveTo(x,y);
                    mapGridCanvas.lineTo(x+800,y);


                }

                mapGridCanvas.closePath();
                mapGridCanvas.stroke();
            }
        });
        RootPanel.get().add(button);
    }

}
