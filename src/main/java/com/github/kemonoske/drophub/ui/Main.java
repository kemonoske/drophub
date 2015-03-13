package com.github.kemonoske.drophub.ui;

import com.github.kemonoske.drophub.core.HubClient;
import com.github.kemonoske.drophub.core.HubServer;
import com.github.kemonoske.drophub.core.Parcel;
import com.github.kemonoske.drophub.ui.components.UndecoratedStage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    HubClient client;
    TrayManager trayManager = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        UndecoratedStage stage = new UndecoratedStage(root);
        stage.setWindowTitle("DropHub");
        stage.show();
        root.setOnDragOver(fileDragHandler);
        root.setOnDragDropped(fileDropHandler);
        //trayManager = new TrayManager(primaryStage);
        Platform.setImplicitExit(false);
        //Stuff for data transfer goes here
        HubServer server = new HubServer(8992);
        server.start();
        client = new HubClient("127.0.0.1", 8992);
    }


    public static void main(String[] args) throws Exception {
        launch(args);
    }

    private EventHandler<DragEvent> fileDragHandler = new EventHandler<DragEvent>() {
        @Override
        public void handle(DragEvent event) {
            Dragboard db = event.getDragboard();
            if (db.hasFiles()) {
                event.acceptTransferModes(TransferMode.MOVE);
            } else {
                event.consume();
            }
        }
    };

    private EventHandler<DragEvent> fileDropHandler = new EventHandler<DragEvent>() {
        @Override
        public void handle(DragEvent event) {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                //TODO: Add support for multiple files handling
                String filePath = null;
                File file = db.getFiles().get(0);
                client.send(new Parcel(file.getAbsolutePath(), "world"));
            }
            event.setDropCompleted(success);
            event.consume();
        }
    };
}
