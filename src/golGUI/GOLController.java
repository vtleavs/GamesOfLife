/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golGUI;

import gamesoflife.Application;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Benji
 */
public class GOLController implements Runnable, KeyListener, MouseListener, MouseMotionListener
{
    ArrayList<GOLElement> elements = new ArrayList<>();
    
    private double graphicScaleFactorX;
    private double graphicScaleFactorY;
    
    private boolean halted;
    
    private boolean disabled;
    
    GOLApp uiHandler;
    
    public GOLController(GOLApp handler, double scaleX, double scaleY) 
    {
        this.uiHandler = handler;
        graphicScaleFactorX = scaleX;
        graphicScaleFactorY = scaleY;
        
        handler.addMouseListener(this);
        handler.addMouseMotionListener(this);
        handler.addKeyListener(this);
    }
    
    public void paint(Graphics g)
    {
        for(GOLElement e : elements)
        {
            if(e.isVisible())
            {
                e.paint(g);
            }
        }
    }
    
    public void add(GOLElement element)
    {
        element.setSize(new Dimension((int)(graphicScaleFactorX * element.width), 
                (int)(graphicScaleFactorY * element.height)));
        element.setLocation(new Point((int)(graphicScaleFactorX * element.xLocation), 
                (int)(graphicScaleFactorY * element.yLocation)));
        elements.add(element);
    }
    
    public void remove(GOLElement element)
    {
        elements.remove(element);
    }
    
    public void mouseClicked(MouseEvent me) {
        if(disabled)
            return;
        
        ActionEvent event = null;
        for(GOLElement e : elements)
        {
            if(!e.isDisabled())
            {
                event = e.mouseClicked(me);
                if(event != null)
                {
                    uiHandler.actionPerformed(event);
                    
                }
            }
        }
    }

    public void mousePressed(MouseEvent me) {
        if(disabled)
            return;
        
        ActionEvent event = null;
        for(GOLElement e : elements)
        {
            if(!e.isDisabled())
            {
                event = e.mousePressed(me);
                if(event != null)
                {
                    uiHandler.actionPerformed(event);
                    
                }
            }
        }
    }

    public void mouseReleased(MouseEvent me) {
        if(disabled)
            return;
        
        ActionEvent event = null;
        for(GOLElement e : elements)
        {
            if(!e.isDisabled())
            {
                event = e.mouseReleased(me);
                if(event != null)
                {
                    uiHandler.actionPerformed(event);
                    
                }
            }
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent me) {
        if(disabled)
            return;
        
        ActionEvent event = null;
        for(GOLElement e : elements)
        {
            if(!e.isDisabled())
            {
                event = e.mouseDragged(me);
                if(event != null)
                {
                    uiHandler.actionPerformed(event);
                    
                }
            }
        }
    }
    
    public Point getMousePosition()
    {
        return uiHandler.getMousePosition();
    }
    
    public void halt()
    {
        disabled = true;
        halted = true;
    }

    @Override
    public void run() {
        while(!halted)
        {            
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(GOLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent me) 
    {
        if(disabled)
            return;
        
        for(GOLElement e : elements)
        {
            if(!e.isDisabled())
            {
                e.mouseMoved(me);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        if(disabled)
            return;
        
        for(GOLElement e : elements)
        {
            if(!e.isDisabled())
            {
                e.keyTyped(ke);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(disabled)
            return;
        
        for(GOLElement e : elements)
        {
            if(!e.isDisabled())
            {
                e.keyPressed(ke);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public boolean isHalted() {
        return halted;
    }
    
    public void unHalt()
    {
        halted = false;
    }

    public void disable() {
        this.disabled = true;
    }
    
    public void enable() {
        this.disabled = false;
    }
    
    
}