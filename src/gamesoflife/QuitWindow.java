/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamesoflife;

import golGUI.GOLApp;
import golGUI.GOLButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author Benji
 */
public class QuitWindow extends GOLApp
{
    GOLButton proceedButton;
    GOLButton cancelButton;
    
    public QuitWindow() 
    {
        setLocation(1300, 500);
        setSize(600, 400);
        setUndecorated(true);
        setVisible(true);
        
        buildUI();
        
    }

    @Override
    protected void buildUI() {
        super.buildUI();
        
        proceedButton = new GOLButton(350, 250, "I'm sure", controller);
        cancelButton = new GOLButton(50, 250, "Cancel", controller);
        
        controller.add(proceedButton);
        controller.add(cancelButton);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        g.setColor(Color.darkGray);
        g.drawRect(0, 0, getWidth(), getHeight());
        g.drawRect(1, 1, getWidth()-3, getHeight()-3);
        g.setFont(new Font("", Font.PLAIN, 30));
        g.drawString("Are you sure you want to quit?", 100, 100);
    }
    
    

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {    
        if(e.getKeyCode() == 10) // Escape Key
        {
            this.halt("Quit Dialog Proceed Button");
            Main.halt("Quit Dialog Proceed Button");
            repaint();
            return;
        }
        
        if(e.getKeyCode() == 27) // Escape Key
        {
            this.halt("Escape Pressed");
            Main.app.resetController();
            repaint();
            return;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(proceedButton))
        {
            this.halt("Quit Dialog Proceed Button");
            Main.halt("Quit Dialog Proceed Button");
            repaint();
            return;
        }
        
        if(e.getSource().equals(cancelButton))
        {
            this.halt("Cancelled");
            Main.app.resetController();
            repaint();
            return;
        }
    }
}
