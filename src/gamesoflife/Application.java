/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamesoflife;

import golGUI.GOLButton;
import golGUI.GOLController;
import golGUI.GOLRadioButton;
import golGUI.GOLRadioController;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
//import javax.swing.*;

/**
 *
 * @author Ben
 */
public class Application extends JFrame implements Runnable, ComponentListener, MouseListener, MouseMotionListener, KeyListener, ActionListener
{
    private Image dbImage;
    private Graphics dbGraphics;
    
    private double cellW;
    private double cellH;
    
    double mouseX1;
    double mouseY1;
    double mouseX2;
    double mouseY2;
    double mouseXFirst;
    double mouseYFirst;
    
    private boolean halted;
    private boolean mousePressed = false;
    
    MouseEvent mousePressEvent;
    MouseEvent mouseClickEvent;
    
    GOLButton resetButton;
    GOLButton quitButton;
    GOLButton runButton;
    GOLButton stepButton;
    GOLRadioButton gridButton;
    
    GOLRadioController speedSelectorController = new GOLRadioController();
    GOLRadioButton[] speedSelectors = new GOLRadioButton[10];
    GOLRadioController ruleSelectorController = new GOLRadioController();
    GOLRadioButton[] ruleSelectors = new GOLRadioButton[7];
    
    double scaleFactorX;
    double scaleFactorY;
    
    GOLController controller;
    
    public static Color backgroundColor;
    public static Color gridColor;
    public static Color gridBackgroundColor;
    
    private boolean straightMode = false;
        
    public static boolean paused = true;
    
    public Application()
    {
        System.out.print("Initializing Frontend: ");
        long initStart = System.currentTimeMillis();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        setSize(3200, 1800);
        //setSize(screenSize.width, screenSize.height);
        
        this.setUndecorated(true);
        this.setVisible(true);
        dbImage = createImage(3200, 1800);
        dbGraphics = dbImage.getGraphics();
        
        scaleFactorX = 1;//this.getSize().getWidth()/3200;
        scaleFactorY = 1;//this.getSize().getHeight()/1800;
        
        cellW = (double)dbImage.getWidth(this)/(double)GamesOfLife.getGridWidth();
        cellH = (double)dbImage.getHeight(this)/(double)GamesOfLife.getGridHeight();
        
        addMouseListener(this);
        addKeyListener(this);
        addMouseMotionListener(this);
        
        halted = false;
        
        repaint();
        
        controller = new GOLController(this, scaleFactorX, scaleFactorY);
        
        Thread controllerThread = new Thread(controller);
        controllerThread.start();
        
        controllerThread.setPriority(Thread.MAX_PRIORITY);
        
        buildUI();
        
        System.out.println(System.currentTimeMillis() - initStart + "ms");
        
        conwayMode();
        
         
    }
    
    private void conwayMode()
    {
        gridBackgroundColor = Color.lightGray;
        gridColor = Color.white;
    }
    
    private void wireworldMode()
    {
        gridBackgroundColor = new Color(31, 129, 5);
        gridColor = Color.white;
    }
    
    @Override
    public void run() 
    {
        while(!halted)
        {
            //System.out.println("Running Frontend");
            repaint();
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    private void buildUI()
    {
        resetButton = new GOLButton(100, 1650, 200, 100, "Reset");
        resetButton.setColor(new Color(100, 100, 255));
        runButton = new GOLButton(500, 1650, 300, 100, "Run / Pause");
        runButton.setColor(Color.green);
        stepButton = new GOLButton(900, 1650, 100, 100, "Step");
        stepButton.setColor(Color.lightGray);
        gridButton = new GOLRadioButton(1100, 1650, 200, 100, "Toggle Grid");
        gridButton.toggleOn();
        quitButton = new GOLButton(3000, 1650, 100, 100, "Quit");
        quitButton.setColor(new Color(225, 100, 100));
        
        int ssX = 1400;
        int ssY = 1675;
        int ssGap = 20;
        int ssWidth = 125;
        int ssHeight = 50;
        speedSelectors[0] =  new GOLRadioButton(ssX, ssY, ssWidth, ssHeight, "25ms");
        speedSelectors[1] =  new GOLRadioButton(ssX+ssGap+ssWidth, ssY, ssWidth, ssHeight, "50ms");
        speedSelectors[2] =  new GOLRadioButton(ssX+2*ssGap+2*ssWidth, ssY, ssWidth, ssHeight, "75ms");
        speedSelectors[3] =  new GOLRadioButton(ssX+3*ssGap+3*ssWidth, ssY, ssWidth, ssHeight, "100ms");
        speedSelectors[4] =  new GOLRadioButton(ssX+4*ssGap+4*ssWidth, ssY, ssWidth, ssHeight, "200ms");
        speedSelectors[5] =  new GOLRadioButton(ssX+5*ssGap+5*ssWidth, ssY, ssWidth, ssHeight, "300ms");
        speedSelectors[6] =  new GOLRadioButton(ssX+6*ssGap+6*ssWidth, ssY, ssWidth, ssHeight, "400ms");
        speedSelectors[7] =  new GOLRadioButton(ssX+7*ssGap+7*ssWidth, ssY, ssWidth, ssHeight, "500ms");
        speedSelectors[8] =  new GOLRadioButton(ssX+8*ssGap+8*ssWidth, ssY, ssWidth, ssHeight, "1000ms");
        speedSelectors[9] =  new GOLRadioButton(ssX+9*ssGap+9*ssWidth, ssY, ssWidth, ssHeight, "2000ms");
                
        speedSelectorController.add(speedSelectors);
        speedSelectorController.setDefaultButton(speedSelectors[0]);
        
        int rsX = 100;
        int rsY = 1525;
        int rsGap = 40;
        int rsWidth = 300;
        int rsHeight = 70;
        ruleSelectors[0] = new GOLRadioButton(rsX, rsY, rsWidth, rsHeight, "Conway");
        ruleSelectors[1] = new GOLRadioButton(rsX+rsGap+rsWidth, rsY, rsWidth, rsHeight, "Wireworld");
        ruleSelectors[2] = new GOLRadioButton(rsX+2*rsGap+2*rsWidth, rsY, rsWidth, rsHeight, "Viral");
        ruleSelectors[3] = new GOLRadioButton(rsX+3*rsGap+3*rsWidth, rsY, rsWidth, rsHeight, "Conway Custom");
        ruleSelectors[4] = new GOLRadioButton(rsX+4*rsGap+4*rsWidth, rsY, rsWidth, rsHeight, "Wireworld Custom");
        ruleSelectors[5] = new GOLRadioButton(rsX+5*rsGap+5*rsWidth, rsY, rsWidth, rsHeight, "Viral Custom");
        ruleSelectors[6] = new GOLRadioButton(rsX+6*rsGap+6*rsWidth, rsY, rsWidth, rsHeight, "Custom");
        
        ruleSelectorController.add(ruleSelectors);
        ruleSelectorController.setDefaultButton(ruleSelectors[0]);
        
        for(GOLRadioButton rb : ruleSelectors)
        {
            rb.setColor(new Color(250, 250, 250));
        }
        ruleSelectors[0].setColor(Color.YELLOW);
        ruleSelectors[1].setColor(Color.YELLOW);
        ruleSelectors[2].setColor(Color.YELLOW);
        ruleSelectors[0].toggleOn();
        
        controller.add(resetButton);
        controller.add(runButton);
        controller.add(stepButton);
        controller.add(gridButton);
        controller.add(quitButton);
        
        controller.add(ruleSelectorController);
        controller.add(speedSelectorController);
        
    }

    @Override
    public void paint(Graphics g) 
    {        
        if(dbGraphics != null)
        {
            if(straightMode)
            {
                dbGraphics.drawLine((int)mouseX1, (int)mouseY1, (int)mouseX2, (int)mouseY2);
                dbGraphics.drawLine((int)mouseX1+1, (int)mouseY1+1, (int)mouseX2+1, (int)mouseY2+1);
                dbGraphics.drawLine((int)mouseX1-1, (int)mouseY1-1, (int)mouseX2-1, (int)mouseY2-1);
                dbGraphics.drawLine((int)mouseX1+2, (int)mouseY1+2, (int)mouseX2+2, (int)mouseY2+2);
                dbGraphics.drawLine((int)mouseX1-2, (int)mouseY1-2, (int)mouseX2-2, (int)mouseY2-2);
            }
            
            else
            {
                super.paint(dbGraphics);

                for(int i = 1; i < GamesOfLife.getGridWidth()-2; ++i)
                {
                    for(int j = 1; j < GamesOfLife.getGridHeight()-1; ++j)
                    {
                        if((i*cellW) > 95 && (j*cellH) > 95
                                && (i*cellW) < getWidth() - 95 && (j*cellH) < getHeight() - 325)
                        {
                            dbGraphics.setColor(gridBackgroundColor);
                            GamesOfLife.getCells()[i][j].paint(dbGraphics, (int)(i*cellW), (int)(j*cellH), (int)(cellW), (int)(cellH));
                            dbGraphics.setColor(gridColor);
                            if(gridButton.isToggled())
                            {
                                dbGraphics.drawRect((int)(i*cellW), (int)(j*cellH), (int)(cellW), (int)(cellH));
                            }
                            dbGraphics.setColor(Color.BLACK);
                        }
                    }
                }


//                dbGraphics.drawRect((int)(5*cellW), (int)(5*cellH), 
//                        (int)((GamesOfLife.getGridWidth()-10)*cellW), 
//                        (int)((GamesOfLife.getGridHeight()-20)*cellH));

                //dbGraphics.drawRect(95, 95, getWidth() - 95-95, getHeight() - 325-95);

                dbGraphics.setFont(new Font("title font", Font.BOLD, 52));
                String title = "Games Of Life: Cellular Atomata";
                dbGraphics.drawString(title, 1600-dbGraphics.getFontMetrics().stringWidth(title)/2, 70);

                Integer tickCount = GamesOfLife.tickCount;
                int tickCountWidth = dbGraphics.getFontMetrics().stringWidth(tickCount.toString());
                dbGraphics.drawString("T -  " + GamesOfLife.tickCount, (int)((3000)-tickCountWidth), 1550);

                dbGraphics.setFont(new Font("Normal", Font.PLAIN, 12));

                controller.paint(dbGraphics);
            }
            
            g.drawImage(dbImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT), 0, 0, this);
        }
    }
    
    private Cell getCellClicked(double mouseX, double mouseY) throws ArrayIndexOutOfBoundsException
    {
        Cell c = null;
        
        if(mouseY < 0 || mouseY > GamesOfLife.getGridWidth()*cellH)
            throw new ArrayIndexOutOfBoundsException("Index out of bounds n stuff");
        
//        try
//        {
            c = GamesOfLife.getCells()[(int)(mouseX * scaleFactorX/cellW)][(int)(mouseY * scaleFactorY/cellH)];
//        }
//        catch(Exception e)
//        {
//            throw e;
//        }
        
        return c;
    }
    
     private void writeCells(Cell c)
    {        
        if(mousePressEvent.getButton() == 1 && !GamesOfLife.rules.equals("wireworld"))
        {
                c.setStatus(Cell.ALIVE);
        }
        
        else if(mousePressEvent.getButton() == 1 && GamesOfLife.rules.equals("wireworld"))
        {
                c.setStatus(Cell.CONDUCTOR);
        }

        else if(mousePressEvent.getButton() == 3)
        {
                c.setStatus(Cell.DEAD);
        }

        else if(mousePressEvent.getButton() == 2 && GamesOfLife.rules.equals("wireworld"))
        {
            if(c.getStatus() == Cell.ELECTRON_HEAD)
                c.setStatus(Cell.CONDUCTOR);
            else if(c.getStatus() == Cell.CONDUCTOR)
                c.setStatus(Cell.ELECTRON_HEAD);
        }

        else if(mousePressEvent.getButton() == 5 && GamesOfLife.rules.equals("wireworld"))
        {
            if(c.getStatus() == Cell.ELECTRON_TAIL)
                c.setStatus(Cell.CONDUCTOR);
            else if(c.getStatus() == Cell.CONDUCTOR)
                c.setStatus(Cell.ELECTRON_TAIL);
        }
        repaint();
    }
    
    private void leavittLine(double x0, double y0, double x1, double y1)
    {
        if(Math.abs(x0 - x1) >= 1)
        {
            if(x0 > x1)
            {
                double xt = x0;
                x0 = x1;
                x1 = xt;

                double yt = y0;
                y0 = y1;
                y1 = yt;
            }
            
            // calculate slope
            double slope = (y1 - y0)/(x1 - x0); 
            
            if(slope == 0)
                slope = .001;
            else if(Math.abs(slope) > 100)
                slope = 10;
            else if(slope == -0)
                slope = -.001;
            else if(slope == Double.NaN)
                slope = 1000;

            System.out.println(x0 + ", " + y0 + ", " + x1 + ", " + y1 + ": " + slope);

            
            
            double granularity = Math.pow(Math.E, -slope/100 - 2.3); // pixels

            for(double i = x0; i <= x1; i+=granularity)
            {
                double yResult = slope * (i - x1) + y1;

                writeCells(getCellClicked(i, yResult));
            }
        }
        else
        {
            if(y0 > y1)
            {
                double xt = x0;
                x0 = x1;
                x1 = xt;

                double yt = y0;
                y0 = y1;
                y1 = yt;
            }

            // calculate slope
            double slope = (y1 - y0)/(x1 - x0);        
            
            if(slope == 0)
                slope = .001;
            else if(Math.abs(slope) > 100)
                slope = 10;
            else if(slope == -0)
                slope = -.001;
            else if(slope == Double.NaN)
                slope = 1000;

            System.out.println(x0 + ", " + y0 + ", " + x1 + ", " + y1 + ": " + slope);
            
            double granularity = .001; // pixels

            for(double i = y0; i <= y1; i+=granularity)
            {
                double yResult = slope * (i - x1) + y1;

                if(yResult >= 0 && yResult <= GamesOfLife.getGridHeight()*cellH)
                    writeCells(getCellClicked(i, yResult));
            }
        }        
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
        mouseClickEvent = me;
        
        mouseX2 = getMousePosition().getX();
        mouseY2 = getMousePosition().getY();
        
        
        if(mouseY2 > 1500);
            //controller.mouseClicked(me);
        
        
    }

    @Override
    public void mousePressed(MouseEvent me) 
    {        
        mousePressed = true;
        mousePressEvent = me;
        
        mouseX2 = getMousePosition().getX();
        mouseY2 = getMousePosition().getY();
        
        if(straightMode)
        {
            mouseX1 = getMousePosition().getX();
            mouseY1 = getMousePosition().getY();
        }
        
        Cell c = getCellClicked(mouseX2, mouseY2);
        
        writeCells(c);
        
        repaint();
        
        //controller.mousePressed(me);
    }

    @Override
    public void mouseReleased(MouseEvent me) 
    {
        //controller.mouseReleased(me);
        
        if(straightMode)
        {
            mouseX2 = getMousePosition().getX();
            mouseY2 = getMousePosition().getY();
            
            leavittLine(mouseX1, mouseY1, mouseX2, mouseY2);
        }
        
        Main.frontend.setPriority(Thread.MIN_PRIORITY);
        Main.backend.setPriority(Thread.MAX_PRIORITY);
        
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
    
    Runnable LeavittLine = new Runnable() {
        @Override
        public void run() {
            leavittLine(mouseX2, mouseY2, mouseX1, mouseY1);
        }
    };
    
    @Override
    public void mouseDragged(MouseEvent me) {
        if(mousePressed)
        {   
            mouseX1 = getMousePosition().getX();
            mouseY1 = getMousePosition().getY();
            
            leavittLine(mouseX2, mouseY2, mouseX1, mouseY1);
            
            //EventQueue.invokeLater(LeavittLine);
            
            //SwingUtilities.invokeLater(doBresenhamLine);
            //Thread bresenhamThread = new Thread(doBresenhamLine);
            //bresenhamThread.start();
            
            mouseX2 = mouseX1;//getMousePosition().getX();
            mouseY2 = mouseY1;//getMousePosition().getY();
            
//            Cell c = getCellClicked(mouseX2, mouseY2);
//            Cell cF = getCellClicked(mouseXFirst, mouseYFirst);

            //drawCells(c, cF);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //System.out.println(straightMode);
        
        if(straightMode)
        {
            mouseX2 = getMousePosition().getX();
            mouseY2 = getMousePosition().getY();
            
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) 
    {
        if(e.getKeyCode() == 27) // Escape Key
        {
            Main.halt("Escape key exited the program");
        }
        
        if(e.getKeyCode() == 32) // Space Bar
        {
            System.out.println("PAUSE ACTIVATED");
            paused = !paused;
            resetButton.toggleDisable();
        }
        
        if(e.getKeyCode() == 16) // Shift Key
        {
            straightMode = true;
        }
        
        
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        if(e.getKeyCode() == 16) // Shift Key
        {
            straightMode = false;
        }
    }

    public void halt(String cause)
    {
        System.out.println("Frontend halted for reason: " + cause);
        halted = true;
        
        controller.halt();
        
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource().equals(resetButton))
        {
            paused = true;
            GamesOfLife.reset();
            resetButton.enable();
            repaint();
            return;
        }
        else if(e.getSource().equals(quitButton))
        {
            Main.halt("Quit button was pressed");
            repaint();
            return;
        }
        else if(e.getSource().equals(runButton))
        {
            System.out.println("PAUSE ACTIVATED");
            paused = !paused;
            resetButton.toggleDisable();
            repaint();
            return;
        }
        else if(e.getSource().equals(stepButton))
        {
            GamesOfLife.tick();
            repaint();
            return;
        }
        
        for(int i = 0; i < speedSelectors.length; ++i)
        {
            if(speedSelectors[i].isToggled() && e.getSource().equals(speedSelectors[i]))
            {
                switch(i)
                {
                    case 0 : GamesOfLife.tickInterval = 25;
                            break;
                    case 1 : GamesOfLife.tickInterval = 50;
                            break;
                    case 2 : GamesOfLife.tickInterval = 75;
                            break;
                    case 3 : GamesOfLife.tickInterval = 100;
                            break;
                    case 4 : GamesOfLife.tickInterval = 200;
                            break;
                    case 5 : GamesOfLife.tickInterval = 300;
                            break;
                    case 6 : GamesOfLife.tickInterval = 400;
                            break;
                    case 7 : GamesOfLife.tickInterval = 500;
                            break;
                    case 8 : GamesOfLife.tickInterval = 1000;
                            break;
                    case 9 : GamesOfLife.tickInterval = 2000;                            
                }
                repaint();
                return;
            }
        }
        
        for(int i = 0; i < ruleSelectors.length; ++i)
        {
            if(ruleSelectors[i].isToggled() && e.getSource().equals(ruleSelectors[i]))
            {
                paused = true;
                switch(i)
                {
                    case 0 : GamesOfLife.rules = "conway";
                            conwayMode();
                            break;
                    case 1 : GamesOfLife.rules = "wireworld";
                            wireworldMode();
                            break;
                    case 2 : GamesOfLife.rules = "viral";
                            break;
                    case 3 : GamesOfLife.rules = "custom conway";
                            break;
                    case 4 : GamesOfLife.rules = "custom wireworld";
                            break;
                    case 5 : GamesOfLife.rules = "custom viral";
                            break;
                    case 6 : GamesOfLife.rules = "custom";
                            break;                      
                }
                GamesOfLife.reset();
                repaint();
                return;
            }
        }
        repaint();
    }

    
}
