/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author Benji
 */
public class GOLButton extends GOLElement
{
    protected static int numButtons = 0;
    
    
    protected String title;
    
    protected boolean pressed;
    protected boolean toggled;
    protected boolean disabled;
    
    protected Color color = new Color(200, 200, 200);

    /**     * 
     * @param title The title of the button which will be displayed
     * on the button itself
     */
    public GOLButton(String title, GOLController controller) 
    {
        this(0, 0, 200, 100, title, "GOLButton" + numButtons, controller);
    }
    
    /**
     * 
     * @param title The title of the button which will be displayed
     * on the button itself
     * @param name The identifier that you wish the button to be known by,
     * not really necessary, but it makes for more understandable output
     */
    public GOLButton(String title, String name, GOLController controller) 
    {
        this(0, 0, 200, 100, title, name, controller);
    }
    
    /**
     * 
     * @param x The x-location in pixels of the upper left corner of the button
     * @param y The y-location in pixels of the upper left corner of the button
     * @param title The title of the button which will be displayed
     * on the button itself
     */
    public GOLButton(int x, int y, String title, GOLController controller) 
    {
        this(x, y, 200, 100, title, "GOLButton" + numButtons, controller);
    }
    
    /**
     * 
     * @param x The x-location in pixels of the upper left corner of the button
     * @param y The y-location in pixels of the upper left corner of the button
     * @param title The title of the button which will be displayed
     * on the button itself
     * @param name The identifier that you wish the button to be known by,
     * not really necessary, but it makes for more understandable output
     */
    public GOLButton(int x, int y, String title, String name, GOLController controller) 
    {
        this(x, y, 200, 100, title, name, controller);
    }
    
    /**
     * 
     * @param x The x-location in pixels of the upper left corner of the button
     * @param y The y-location in pixels of the upper left corner of the button
     * @param width The width in pixels of the button
     * @param height The height in pixels of the button
     * @param title The title of the button which will be displayed
     * on the button itself
     */
    public GOLButton(int x, int y, int width, int height, String title, GOLController controller) 
    {
        this(x, y, width, height, title, "GOLButton" + numButtons, controller);
    }
    
    /**
     * 
     * @param x The x-location in pixels of the upper left corner of the button
     * @param y The y-location in pixels of the upper left corner of the button
     * @param width The width in pixels of the button
     * @param height The height in pixels of the button
     * @param name The identifier that you wish the button to be known by,
     * not really necessary, but it makes for more understandable output
     * @param title The title of the button which will be displayed
     * on the button itself
     */
    public GOLButton(int x, int y, int width, int height, String title, String name, GOLController controller) 
    {
        super(x, y, width, height, controller);
        
        numButtons++;
        
        this.title = title;
        this.name = name;
    }

    @Override
    public void paint(Graphics g) {
        if(pressed)
            g.setColor(new Color(Math.max(color.getRed()-50, 0), 
                    Math.max(color.getGreen()-50, 0), 
                    Math.max(color.getBlue()-50, 0)));
        else if(disabled)
            g.setColor(new Color(Math.max(color.getRed()-60, 0), 
                    Math.max(color.getGreen()-60, 0), 
                    Math.max(color.getBlue()-60, 0)));
        else
            g.setColor(color);
        
        g.fillRect(xLocation, yLocation, width, height);
        g.setColor(Color.black);
        g.drawRect(xLocation, yLocation, width, height);
        
        g.setFont(new Font("ButtonFont", Font.PLAIN, 30));
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, 
                xLocation+(int)(width/2.0)-(int)(titleWidth/2), 
                yLocation+(int)(height/2.0)+10);
        g.setFont(new Font("Normal", Font.PLAIN, 12));
    }

    public static int getNumButtons() {
        return numButtons;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setX(int xLocation) {
        this.xLocation = xLocation;
    }

    public void setY(int yLocation) {
        this.yLocation = yLocation;
    }

    public boolean isPressed() {
        return pressed;
    }

    public boolean isToggled() {
        return toggled;
    }   
    
    public void toggleDisable(){
        disabled = !disabled;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void toggle() {
        toggled = !toggled;
    }
    
    public void toggleOff(){
        toggled = false;
    }
    
    public void toggleOn(){
        toggled = true;
    }

    public ActionEvent mouseClicked(MouseEvent e) { 
        if(e.getButton() == 1 && isCaptured() && !disabled)
        {
            //toggled = !toggled;
            return new ActionEvent(this, 195819, name);
        }
        return null;
    }

    public ActionEvent mousePressed(MouseEvent e) { 
        if(e.getButton() == 1 && isCaptured() && !disabled)
        {
            toggled = !toggled;
            pressed = true;
        }
        return null;
    }

    public ActionEvent mouseReleased(MouseEvent e) { 
        if(e.getButton() == 1)
        {
            pressed = false;
        }
        return null;
    }

    @Override
    public void hover() {
        this.setColor(new Color(Math.max(color.getRed()-10, 0), 
                    Math.max(color.getGreen()-10, 0), 
                    Math.max(color.getBlue()-10, 0)));
    }
    
    
}
