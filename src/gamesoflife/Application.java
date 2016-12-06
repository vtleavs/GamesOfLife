/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamesoflife;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Ben
 */
public class Application extends JFrame implements Runnable, ComponentListener, MouseListener
{
    private Image dbImage;
    private Graphics dbGraphics;
    
    private double cellW;
    private double cellH;
    
    double mouseX2;
    double mouseY2;
    
    public static boolean paused = true;
    
    public Application()
    {
        setSize(3200, 1800);
        this.setUndecorated(true);
        this.setVisible(true);
        dbImage = createImage(3200, 1800);
        dbGraphics = dbImage.getGraphics();
        
        cellW = (double)dbImage.getWidth(this)/(double)GamesOfLife.getGridWidth();
        cellH = (double)dbImage.getHeight(this)/(double)GamesOfLife.getGridHeight();
        
        addMouseListener(this);
        
        repaint();
    }
    
    @Override
    public void run() 
    {
        while(true)
        {
            System.out.println("Running Frontend");
            repaint();
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @Override
    public void paint(Graphics g) 
    {
        if(dbGraphics != null)
        {
            super.paint(dbGraphics);
            
            for(int i = 5; i < GamesOfLife.getGridWidth(); ++i)
            {
                for(int j = 5; j < GamesOfLife.getGridHeight(); ++j)
                {
                    GamesOfLife.getCells()[i][j].paint(dbGraphics, (int)(i*cellW), (int)(j*cellH), (int)(cellW), (int)(cellH));
                    dbGraphics.setColor(Color.WHITE);
                    dbGraphics.drawRect((int)(i*cellW), (int)(j*cellH), (int)(cellW), (int)(cellH));
                    dbGraphics.setColor(Color.BLACK);
                }
            }
            g.drawImage(dbImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST), 0, 0, this);
        }
    }
    
    private Cell getCellClicked()
    {
        return GamesOfLife.getCells()[(int)(mouseX2/cellW)][(int)(mouseY2/cellH)];
    }

    @Override
    public void componentResized(ComponentEvent ce) 
    {
        if(ce.getSource() == this)
        {
            repaint();
        }
        
    }

    @Override
    public void componentMoved(ComponentEvent ce) 
    {
    }

    @Override
    public void componentShown(ComponentEvent ce) 
    {
    }

    @Override
    public void componentHidden(ComponentEvent ce) 
    {
    }

    @Override
    public void mouseClicked(MouseEvent me) 
    {
        
    }

    @Override
    public void mousePressed(MouseEvent me) 
    {
        mouseX2 = getMousePosition().getX();
        mouseY2 = getMousePosition().getY();
        
        if(me.getButton() == 2)
        {
            System.out.println("PAUSE ACTIVATED");
            paused = !paused;
        }
        
        if(me.getButton() == 1)
        {
            Cell c = getCellClicked();
                
            if(c.getStatus() == Cell.ALIVE)
                c.setStatus(Cell.DEAD);
            else if(c.getStatus() == Cell.DEAD)
                c.setStatus(Cell.ALIVE);
        }
        
        if(me.getButton() == 3)
        {
            Cell c = getCellClicked();
                if(c.getStatus() == Cell.CONDUCTOR)
                    c.setStatus(Cell.DEAD);
                else if(c.getStatus() == Cell.DEAD)
                    c.setStatus(Cell.CONDUCTOR);
        }
        
        if(me.getButton() == 4)
        {
            Cell c = getCellClicked();
                if(c.getStatus() == Cell.ELECTRON_HEAD)
                    c.setStatus(Cell.CONDUCTOR);
                else if(c.getStatus() == Cell.CONDUCTOR)
                    c.setStatus(Cell.ELECTRON_HEAD);
        }
        
        if(me.getButton() == 5)
        {
            Cell c = getCellClicked();
                if(c.getStatus() == Cell.ELECTRON_TAIL)
                    c.setStatus(Cell.CONDUCTOR);
                else if(c.getStatus() == Cell.CONDUCTOR)
                    c.setStatus(Cell.ELECTRON_TAIL);
        }
    }

    

    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}

   
}
