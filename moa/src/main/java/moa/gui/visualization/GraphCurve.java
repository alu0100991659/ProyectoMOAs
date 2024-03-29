/*
 *    GraphCurve.java
 *    Copyright (C) 2010 RWTH Aachen University, Germany
 *    @author Jansen (moa@cs.rwth-aachen.de)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *    
 *    
 */
package moa.gui.visualization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import moa.evaluation.MeasureCollection;
import moa.streams.clustering.ClusterEvent;

public class GraphCurve extends javax.swing.JPanel {
    private double min_value = 0;
    private double max_value = 1;
    private MeasureCollection measure0 = null;
    private MeasureCollection measure1 = null;
    private int measureSelected = 0;

    private double x_resolution;
    private int processFrequency;

    private ArrayList<ClusterEvent> clusterEvents;
    
    
    /** Creates new form GraphCurve */
    public GraphCurve() {
        initComponents();
    }


    public void setGraph(MeasureCollection measure0, MeasureCollection measure1, int selection){
       this.measure0 = measure0;
       this.measure1 = measure1;
       this.measureSelected = selection;
       repaint();
    }
    
    void setProcessFrequency(int processFrequency) {
        this.processFrequency = processFrequency;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);

        if(measure0!=null && measure1!=null){
                paintFullCurve(g, measure0, measureSelected, Color.red);
                paintFullCurve(g, measure1, measureSelected, Color.blue);
        }
        else{
            if(measure0!=null){
                paintFullCurve(g, measure0, measureSelected, Color.red);
            }
        }
        paintEvents(g);
        
    }


    private void paintFullCurve(Graphics g, MeasureCollection m, int mSelect, Color color){
            if (m.getNumberOfValues(mSelect)==0) return;

            boolean corrupted = false;
            int height = getHeight();
            int width = getWidth();
            int n = m.getNumberOfValues(mSelect);
            if(x_resolution > 1) 
                n = (int)(n / (int)x_resolution);
            int[] x = new int[n];
            int[] y = new int[n];

            for (int i = 0; i < n; i ++) {
                if(x_resolution > 1){
                    //we need to compress the values
                    double sum_y = 0;
                    int counter = 0;
                    for (int j = 0; j < x_resolution; j++) {
                        if((i)*x_resolution+j<m.getNumberOfValues(mSelect)){
                            sum_y+= m.getValue(mSelect,i);
                            counter++;
                        }
                        sum_y/=counter;
                    }
                    x[i] = i;
                    y[i] = (int)(height-(sum_y/max_value)*height);
                }
                else{
                    //spreading one value
                    x[i] = (i)*(int)(1/x_resolution)+(int)(1/x_resolution/2);
                    double value = m.getValue(mSelect,i);
                    if(Double.isNaN(value)){
                        corrupted = true;
                        break;
                    }
                    y[i] = (int)(height-(value/max_value)*height);
                    
                }
            }
            if(!corrupted){
                g.setColor(color);
                g.drawPolyline(x, y, n);
            }
            
    }

    private void paintEvents(Graphics g){
       if(clusterEvents!=null){
            g.setColor(Color.DARK_GRAY);
            for (int i = 0; i < clusterEvents.size(); i++) {
                int x = (int)(clusterEvents.get(i).getTimestamp()/processFrequency/x_resolution);

                g.drawLine(x, 0, x, getHeight());
            }
        }
    }

    public void setYMinMaxValues(double min, double max){
        min_value = min;
        max_value = max;
    }

    void setClusterEventsList(ArrayList<ClusterEvent> clusterEvents) {
        this.clusterEvents = clusterEvents;
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }

    void setXResolution(double x_resolution) {
        this.x_resolution = x_resolution;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents







    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
