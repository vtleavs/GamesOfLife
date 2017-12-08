/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golGUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.tools.DocumentationTool;

/**
 *
 * @author Benji
 */
public class GOLElement 
{
    private static int numElements = 0;
    
    protected int xLocation;
    protected int yLocation;
    protected Point location;
    
    protected int width;
    protected int height;
    protected Dimension size;
    
    protected String name;
    
    protected boolean visible = true;
    protected boolean disabled;
    
    private double gScaleX = 1;
    private double gScaleY = 1;
    
    protected GOLController controller;

    public GOLElement(int x, int y, int width, int height, GOLController controller) 
    {
        xLocation = x;
        yLocation = y;
        location = new Point(x, y);
        
        this.width = width;
        this.height = height;
        size = new Dimension(width, height);
        
        numElements++;
        
        this.controller = controller;
    }
    
    protected boolean isCaptured() {
        
        double xL = gScaleX*xLocation;
        double yL = gScaleY*yLocation;
        double w = gScaleX*width;
        double h = gScaleY*height;
        
        Point mousePos = controller.getMousePosition();
        try
        {
//            return mousePos.getX() < xLocation+width
//                && mousePos.getX() > xLocation
//                && mousePos.getY() < yLocation+height
//                && mousePos.getY() > yLocation;
            return mousePos.getX() < xL+w
                && mousePos.getX() > xL
                && mousePos.getY() < yL+h
                && mousePos.getY() > yL;
        } 
        catch (NullPointerException e)
        {
            System.err.println("Unresolved NullPointerException; harmless");
            return false;
        }
        //return false;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isDisabled() {
        return disabled;
    }
    
    public void enable(){
        disabled = false;
    }
    
    public void disable(){
        disabled = true;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void toggleDisable() {
        disabled = !disabled;
    }
    
    public Dimension getSize() {
        return size;
    }
    
    public int getHeight() {
        return height;
    }

    public Point getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return xLocation;
    }

    public int getY() {
        return yLocation;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }
    
    public void paint(Graphics g) {
        if(!visible)
            return;
    }
    
    public ActionEvent mouseClicked(MouseEvent e) { 
        return null;
    }

    public ActionEvent mousePressed(MouseEvent e) { 
        return null;
    }

    public ActionEvent mouseReleased(MouseEvent e) { 
        return null;
    }
    
    public ActionEvent mouseDragged(MouseEvent e){
        return null;
    }
    
    public ActionEvent mouseMoved(MouseEvent e) {
        return null;
    }
    
    public ActionEvent keyTyped(KeyEvent ke) {
        return null;
    }
    
    public ActionEvent keyPressed(KeyEvent ke){
        return null;
    }

    public void setScale(double graphicScaleFactorX, double graphicScaleFactorY) 
    {
        gScaleX = graphicScaleFactorX;
        gScaleY = graphicScaleFactorY;
    }
    
    public void hover()
    {
        
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
