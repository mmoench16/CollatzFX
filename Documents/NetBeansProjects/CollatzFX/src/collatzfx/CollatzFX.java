/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collatzfx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author m.moench
 */
public class CollatzFX extends Application {
    
    private CollatzCalculator calculator = new CollatzCalculator();
    private LineChartCreator lineChartCreator = new LineChartCreator();
    
    public boolean isNumericAndPositive(String str) {
        
        try {
            int i = Integer.parseInt(str);
            if (i < 1) {
                return false;
            }
        } catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    private void saveFile(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("Collatz Sequence of " + calculator.getSequence().get(0));
            fileWriter.write(System.getProperty("line.separator"));
            fileWriter.write("Sequence: " + this.calculator.printSequence());
            fileWriter.write(System.getProperty("line.separator"));
            fileWriter.write("Steps: " + this.calculator.getSteps());
            fileWriter.write(System.getProperty("line.separator"));
            fileWriter.write("Maximum: " + this.calculator.getMax());
            
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Collatz Calculator");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        
        Text scenetitle = new Text("Collatz Calculator");
        scenetitle.setId("scenetitle");
        grid.add(scenetitle, 0, 0, 2, 1);
        
        Label instructions = new Label("Enter a number in text field "
                + "below and press CALCULATE in order to "
                + "calculate the Collatz sequence of that number. The "
                + "number must be a positive integer.");
        instructions.setWrapText(true);
        grid.add(instructions, 0, 1, 2, 1);
        
        HBox hbox = new HBox(6);
        
        TextField numberField = new TextField("Enter number here");
        Button btn = new Button("Calculate");
        btn.setDefaultButton(true);
        Button exp = new Button("Export");
        exp.setDisable(true);
        
        hbox.getChildren().addAll(numberField, btn, exp);
        grid.add(hbox, 0, 2);
        
        
        // Label where Collatz seq of number is displayed
        final Label labelSequence = new Label("Sequence: ");
        labelSequence.setId("labelSequence");
        grid.add(labelSequence, 0, 3, 2, 1);
        labelSequence.setWrapText(true);
        
        final Label labelSteps = new Label("Number of Steps: ");
        labelSteps.setId("labelSteps");
        grid.add(labelSteps, 0, 4, 2, 1);
        
        final Label labelMaximum = new Label("Maximum Value: ");
        labelMaximum.setId("labelMaximum");
        grid.add(labelMaximum, 0, 5, 2, 1);
        
        grid.add(lineChartCreator.createLineChart(), 0, 6, 2, 1);
        
        // Set action for Calculate button
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (numberField.getText() != null && !numberField.getText().isEmpty() && isNumericAndPositive(numberField.getText())) {
                    lineChartCreator.removeData();
                    calculator.truncateSequence();
                    calculator.calculateSequence(numberField.getText());
                    exp.setDisable(false);
                    labelSequence.setText("Sequence: " + calculator.printSequence());
                    labelSteps.setText("Number of Steps: " + calculator.getSteps());
                    labelMaximum.setText("Maximum Value: " + calculator.getMax());
                    numberField.clear();
                    numberField.setPromptText("Enter number here");
                    // Create LineChart and add to grid
                    lineChartCreator.setSequence(calculator.getSequence());
                    grid.add(lineChartCreator.createLineChart(), 0, 6, 2, 1);
                } else {
                    calculator.truncateSequence();
                    labelSequence.setText("Please enter a valid integer.");
                    labelSteps.setText("Number of Steps: N/A");
                    labelMaximum.setText("Maximum Value: N/A");
                    numberField.clear();
                    numberField.setPromptText("Enter number here");
                }
            }
            
        });
        
        exp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Export Collatz Sequence");
                fileChooser.setInitialFileName("CollatzSeq - " + calculator.getSequence().get(0));
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Document(*.txt)", "*.txt"));
                File file = fileChooser.showSaveDialog(primaryStage);
                if (file != null) {
                    saveFile(file);
                }
            }
        });
        
        Scene scene = new Scene(grid, 480, 520);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(CollatzFX.class.getResource("CollatzFXStyleSheet.css").toExternalForm());
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
