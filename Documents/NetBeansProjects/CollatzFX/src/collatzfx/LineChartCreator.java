/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collatzfx;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 *
 * @author m.moench
 */
public class LineChartCreator {
    private List<Integer> sequence;
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    final LineChart<Number, Number> lineChart;
        
    
    public LineChartCreator() {
        this.lineChart = new LineChart<>(xAxis, yAxis);
        this.sequence = new ArrayList<Integer>();
    }

    public void setSequence(List<Integer> sequence) {
        this.sequence = sequence;
    }
    
    public LineChart<Number, Number> createLineChart() {
        
        this.xAxis.setLabel("Steps");
        if (this.sequence.isEmpty()) {
            this.lineChart.setTitle("No integer entered.");
        } else {
            this.lineChart.setTitle("Collatz Sequence of " + this.sequence.get(0));
        }
        //this.lineChart.setTitle("Collatz Sequence of " + this.sequence.get(0));
        
        XYChart.Series series = new XYChart.Series();
        
        if (this.sequence.isEmpty()) {
            series.setName("No integer entered");
            series.getData().add(new XYChart.Data(0,0));
        } else {
            series.setName("#" + this.sequence.get(0));
        
            for (int i = 0; i < this.sequence.size(); i++) {
                series.getData().add(new XYChart.Data(i, this.sequence.get(i)));
            }
        }
        
        //series.setName("#" + this.sequence.get(0));
        
        //for (int i = 0; i < this.sequence.size(); i++) {
        //    series.getData().add(new XYChart.Data(i, this.sequence.get(i)));
        //}
        
        this.lineChart.getData().add(series);
        
        return this.lineChart;
    }
    
    public void removeData() {
        this.lineChart.getData().remove(0);
    }
}
