package com.github.kemonoske.drophub.ui.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class UndecoratedStage extends Stage {

    private double initX;
    private double initY;

    protected Label titleLabel;

    public UndecoratedStage(Parent content) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/simple-window.fxml"));

        initStyle(StageStyle.TRANSPARENT);
        setResizable(false);

        BorderPane titleBar = (BorderPane) root.lookup("#titleBar");
        titleLabel = (Label) root.lookup("#titleLabel");
        Button closeButton = (Button) root.lookup("#closeButton");
        AnchorPane window = (AnchorPane) root.lookup("#windowBody");

        SubScene windowContent = (SubScene) root.lookup("#windowContent");

        windowContent.setRoot(content);

        EventHandler<MouseEvent> mousePressHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {

                initX = me.getScreenX() - getX();
                initY = me.getScreenY() - getY();
            }
        };

        EventHandler<MouseEvent> mouseDragHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                setX(me.getScreenX() - initX);
                setY(me.getScreenY() - initY);
            }
        };

        //when mouse button is pressed, save the initial position of screen
        titleBar.setOnMousePressed(mousePressHandler);
        window.setOnMousePressed(mousePressHandler);

        //when screen is dragged, translate it accordingly
        titleBar.setOnMouseDragged(mouseDragHandler);
        window.setOnMouseDragged(mouseDragHandler);

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        setScene(new Scene(window));

        getScene().setFill(Color.TRANSPARENT);

    }

    public void setWindowTitle(String title) {

        titleLabel.setText(title);

    }

}