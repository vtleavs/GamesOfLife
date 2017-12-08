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
import java.awt.List;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author Benji
 */
public class GOLRadioButton extends GOLButton
{    
    public GOLRadioButton(String title, GOLController controller) 
    {
        super(title, controller);
    }
    
    public GOLRadioButton(String title, String name, GOLController controller) 
    {
        super(title, name, controller);
    }
    
    public GOLRadioButton(int x, int y, String title, GOLController controller) 
    {
        super(x, y, title, controller);
    }
    
    public GOLRadioButton(int x, int y, String title, String name, GOLController controller) 
    {
        
        super(x, y, title, name, controller);
    }
    
    public GOLRadioButton(int x, int y, int width, int height, String title, GOLController controller) 
    {
        
        super(x, y, width, height, title, controller);
    }
    
    public GOLRadioButton(int x, int y, int width, int height, String title, String name, GOLController controller) 
    {
        super(x, y, width, height, title, name, controller);
    }

    @Override
    public void paint(Graphics g) {
        if(!visible)
            return;
        
        if(toggled)
            g.setColor(new Color(Math.max(color.getRed()-50, 0), 
                    Math.max(color.getGreen()-50, 0), 
                    Math.max(color.getBlue()-50, 0)));
        if(disabled)
            g.setColor(new Color(Math.max(color.getRed()-60, 0), 
                    Math.max(color.getGreen()-60, 0), 
                    Math.max(color.getBlue()-60, 0)));
        if(!toggled && !disabled)
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
    
    
}
