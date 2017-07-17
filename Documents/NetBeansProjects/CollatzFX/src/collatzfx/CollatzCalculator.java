/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collatzfx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author m.moench
 */
public class CollatzCalculator {
    private int number;
    private List<Integer> sequence;
    private int max;
    private int steps;
    
    public CollatzCalculator() {
        this.sequence = new ArrayList<Integer>();
        this.number = 0;
        this.max = 0;
        this.steps = 0;
    }
    
    public void calculateSequence(String j) {
        
        this.number = Integer.parseInt(j);
        int i = this.number;
        this.sequence.add(i);
        
        while (i != 1) {
            if (i % 2 == 0) {
                i = i / 2;
                this.sequence.add(i);
            }
            
            if (i % 2 != 0 && i != 1) {
                i = i * 3 + 1;
                this.sequence.add(i);
            }
        }
        
        this.max = Collections.max(sequence);
        this.steps = this.sequence.size() - 1;
        
    }

    public int getNumber() {
        return number;
    }

    public int getMax() {
        return max;
    }

    public List<Integer> getSequence() {
        return sequence;
    }

    public String printSequence() {
        
        String sequence = "";
        
        for (int i = 0; i < this.sequence.size(); i++) {
            if (i == this.sequence.size() - 1) {
                sequence = sequence + this.sequence.get(i);
            } else {
                sequence = sequence + this.sequence.get(i) + ", ";
            }
        }
        
        return sequence;
    }

    public int getSteps() {
        return steps;
    }
    
    public void truncateSequence() {
        this.sequence.removeAll(sequence);
        this.max = 0;
        this.steps = 0;
    }
}
