/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author Benji
 */
public class GOLSlider extends GOLElement 
{
    private float value;
    private float maxValue = 1;
    private float minValue = 0;
    private float step;
    
    private String labelPrefix;
    private String labelSuffex;
    
    private boolean controlCaptured;
    
    public GOLSlider(int xLoc, int yLoc, int width, int height, float minValue, float maxValue, float step, GOLController controller) 
    {
        super(xLoc, yLoc, width, height, controller);
        
        this.minValue = minValue;
        value = minValue;
        this.maxValue = maxValue;
        this.step = step;
    }

    @Override
    public void paint(Graphics g) {
        
        g.setColor(Color.lightGray);
        g.fillRect(xLocation, yLocation+15, width, height-30);
        //g.fillRect(xLocation, yLocation+(height/2 - 10), width, (height/2));
        g.setColor(Color.black);
        g.drawRect(xLocation, yLocation+15, width, height-30);
        //g.drawRect(xLocation, yLocation+(height/2 - 10), width, (height/2));
        
        g.setColor(Color.black);
        for(float i = minValue; i < maxValue; i+=step)
            g.drawLine(xLocation + (int)getPositionFromValue(i), yLocation+15, xLocation + (int)getPositionFromValue(i), yLocation+height-15);
        
        g.setColor(Color.gray);
        g.fillRect(xLocation + (int)getPositionFromValue(value)-10, yLocation, 20, height);
        
        g.setColor(Color.gray);
        g.setFont(new Font("", Font.BOLD, 30));
        
        String label = labelPrefix + value + labelSuffex;   
        g.drawString(label, xLocation + (int)getPositionFromValue(value) - g.getFontMetrics().stringWidth(label)/2, yLocation - 20);
    }
    
    private float getValueFromPosition(float x)
    {
        float mapRate = (maxValue-minValue)/width;
        
        float answer = (x - xLocation) * mapRate + minValue;       
        
        return step*Math.round((answer/step));
    }
    
    private float getPositionFromValue(float v)
    {
        float mapRate = width/(maxValue-minValue);
        
        float answer = (v - minValue) * mapRate;
        
        return answer;
    }
    
    @Override
    public ActionEvent mousePressed(MouseEvent me)
    {
        ActionEvent actionEvent = null;
        
        Point p = controller.getMousePosition();
        
        if(p.getX() > xLocation && p.getX() < xLocation+width
                && p.getY() > yLocation && p.getY() < yLocation+height)
        {
            controlCaptured = true;
            value = getValueFromPosition((float)p.getX());
            
            return new ActionEvent(this, 195819, name);
        }
        
        
        
        return actionEvent;
    }
    
    @Override
    public ActionEvent mouseReleased(MouseEvent me)
    {
        ActionEvent actionEvent = null;
        
        controlCaptured = false;
        
        return actionEvent;
    }
    
    @Override
    public ActionEvent mouseMoved(MouseEvent me)
    {
        ActionEvent actionEvent = null;
        
        //System.out.println("MOUSEDRAGGED");
        
//        if(controlCaptured)
//        {
//            value = getValueFromPosition((float)GOLController.getMousePosition().getX());
//            //return new ActionEvent(this, 195819, name);
//        }
        
        return actionEvent;
    }
    
    @Override
    public ActionEvent mouseDragged(MouseEvent me)
    {
        ActionEvent actionEvent = null;
        
        if(controlCaptured)
        {
            float tempValue = getValueFromPosition((float)controller.getMousePosition().getX());

            if(tempValue < minValue)
                 value = minValue;
            else if(tempValue > maxValue)
                value = maxValue;
            else
                value = tempValue;

            return new ActionEvent(this, 195819, name);
        }
        
        return actionEvent;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
    }

    public String getLabelPrefix() {
        return labelPrefix;
    }

    public void setLabelPrefix(String labelPrefix) {
        this.labelPrefix = labelPrefix;
    }

    public String getLabelSuffex() {
        return labelSuffex;
    }

    public void setLabelSuffex(String labelSuffex) {
        this.labelSuffex = labelSuffex;
    }

    @Override
    public String toString() {
        return labelPrefix + value + labelSuffex;
    }

    public void refresh() {
        if(value < minValue)
                 value = minValue;
            else if(value > maxValue)
                value = maxValue;
    }
    
    
    
    
}
