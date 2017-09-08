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
public class GOLController implements Runnable, MouseListener, MouseMotionListener
{
    ArrayList<GOLElement> elements = new ArrayList<>();
    
    private double graphicScaleFactorX;
    private double graphicScaleFactorY;
    
    private boolean halted;
    
    static Application uiHandler;
    
    public GOLController(Application handler, double scaleX, double scaleY) 
    {
        this.uiHandler = handler;
        graphicScaleFactorX = scaleX;
        graphicScaleFactorY = scaleY;
        
        handler.addMouseListener(this);
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
        element.setScale(graphicScaleFactorX, graphicScaleFactorY);
        elements.add(element);
    }
    
    public void remove(GOLElement element)
    {
        elements.remove(element);
    }
    
    public void mouseClicked(MouseEvent me) {
        ActionEvent event = null;
        for(GOLElement e : elements)
        {
            if(!e.isDisabled())
            {
                event = e.mouseClicked(me);
                if(event != null)
                {
                    uiHandler.actionPerformed(event);
                    return;
                }
            }
        }
    }

    public void mousePressed(MouseEvent me) {
        ActionEvent event = null;
        for(GOLElement e : elements)
        {
            if(!e.isDisabled())
            {
                event = e.mousePressed(me);
                if(event != null)
                {
                    uiHandler.actionPerformed(event);
                    return;
                }
            }
        }
    }

    public void mouseReleased(MouseEvent me) {
        ActionEvent event = null;
        for(GOLElement e : elements)
        {
            if(e.isDisabled())
            {
                event = e.mouseReleased(me);
                if(event != null)
                {
                    uiHandler.actionPerformed(event);
                    return;
                }
            }
        }
    }
    
    static Point getMousePosition()
    {
        return uiHandler.getMousePosition();
    }
    
    public void halt()
    {
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
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent me) 
    {
        for(GOLElement e : elements)
        {
            if(e.isDisabled())
            {
                e.hover();
            }
        }
    }
}
