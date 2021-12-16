package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.text.DecimalFormat;

public class Main extends Application {
    public static DecimalFormat numberFormat = new DecimalFormat("#.00");
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background:#d687d6;");
        TextField principalAmount = new TextField("10000");
        Label monthLabel = new Label("Time in months");
        monthLabel.setStyle("-fx-text-fill:white;");
        Label principalLabel = new Label("Principal amount");
        principalLabel.setStyle("-fx-text-fill:white;");
        TextField timeInMonths = new TextField("12");
        TextField answer = new TextField("");
        Slider setInterestRate = new Slider();
        ToggleButton compound = new ToggleButton("Compound");
        Button go = new Button("Calculate");
        answer.setMinWidth(30);
        setInterestRate.setStyle("-fx-text-fill:white;");

        setInterestRate.setShowTickLabels(true);
        setInterestRate.setMajorTickUnit(25f);
        setInterestRate.adjustValue(100);
        Label interestLabel = new Label("Interest amount %: " + setInterestRate.getValue());
        Label toggleLabel = new Label("Press Shift to toggle");
        interestLabel.setStyle("-fx-text-fill:white;");
        root.getChildren().addAll(toggleLabel, answer, monthLabel, principalLabel, go, compound, setInterestRate, interestLabel, principalAmount, timeInMonths);
        setInterestRate.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                interestLabel.setText("Interest amount %: " + numberFormat.format(setInterestRate.getValue()));

            }
        });

        go.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(compound.isSelected()) {
                    answer.setText(compound(Double.valueOf(principalAmount.getText()), Double.valueOf(setInterestRate.getValue() / 100f),
                            Integer.parseInt(timeInMonths.getText())));
                        System.out.println("compound " + answer.getText());
                }
                else {
                    answer.setText(simple(Double.valueOf(principalAmount.getText()), Double.valueOf(setInterestRate.getValue() / 100f),
                            Integer.parseInt(timeInMonths.getText())));
                    System.out.println("simple " + answer.getText());
                }
            }
        });

        root.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode() == KeyCode.SHIFT){
                compound.fire();
                event.consume();
            }

        });

        AnchorPane.setLeftAnchor(principalAmount, 15.0);
        AnchorPane.setLeftAnchor(timeInMonths, 180.0);
        AnchorPane.setTopAnchor(principalAmount, 50.0);
        AnchorPane.setTopAnchor(timeInMonths, 50.0);

        AnchorPane.setLeftAnchor(setInterestRate, 120.0);
        AnchorPane.setTopAnchor(setInterestRate, 120.0);
        AnchorPane.setLeftAnchor(go, 75.0);
        AnchorPane.setTopAnchor(go, 240.0);
        AnchorPane.setLeftAnchor(answer, 140.0);
        AnchorPane.setTopAnchor(answer, 240.0);

        AnchorPane.setLeftAnchor(monthLabel, 220.0);
        AnchorPane.setTopAnchor(monthLabel, 25.0);

        AnchorPane.setLeftAnchor(principalLabel, 40.0);
        AnchorPane.setTopAnchor(principalLabel, 25.0);

        AnchorPane.setLeftAnchor(compound, 145.0);
        AnchorPane.setTopAnchor(compound, 180.0);
        AnchorPane.setLeftAnchor(toggleLabel, 135.0);
        AnchorPane.setTopAnchor(toggleLabel, 160.0);

        AnchorPane.setLeftAnchor(interestLabel, 130.0);
        AnchorPane.setTopAnchor(interestLabel, 100.0);

        Scene scene = new Scene(root, 350, 300);
        primaryStage.setTitle("Monthly Interest Rate Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    private String simple(double principal, double interest, int time){
        return String.valueOf(numberFormat.format(principal * (1 + interest*(time))));
    }
    private String compound(double principal, double interest, int time){
        return String.valueOf(numberFormat.format(principal * Math.pow((1 + interest),(time))));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
