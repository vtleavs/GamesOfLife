/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golGUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Benji
 */
public abstract class GOLApp extends JFrame implements KeyListener, MouseListener, MouseMotionListener, ActionListener, Runnable
{
    protected GOLController controller;
    
    protected double graphicScaleX = 1;
    protected double graphicScaleY = 1;
    
    protected Thread controllerThread;
    
    protected boolean halted;
    
    Dimension screenSize;
    
    public GOLApp() 
    {        
        controllerThread = new Thread(controller);
        controllerThread.setPriority(Thread.MAX_PRIORITY);
        controllerThread.start();
        
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                
        addMouseListener(this);
        addKeyListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void run() {
        while(!halted){
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(GOLApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }
    
    public void halt(String reason) {
        halted = true;
        controller.halt();
        this.setAlwaysOnTop(false);
        this.dispose();
    }
    
    public void resetController() {
        halted = false;
        //controller.enable();
        controllerThread = new Thread(controller);
        controllerThread.setPriority(Thread.MAX_PRIORITY);
        controllerThread.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        controller.paint(g);
    }

    public boolean isHalted() {
        return halted;
    }
    
    protected void buildUI()
    {
        controller = new GOLController(this, graphicScaleX, graphicScaleY);
    }
}
