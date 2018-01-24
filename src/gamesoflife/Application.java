/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamesoflife;

import simple.gui.SApplication;
import simple.gui.SButton;
import simple.gui.SRadioButton;
import simple.gui.SRadioController;
import simple.gui.SSlider;
import simple.gui.STextInput;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
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

/**
 *
 * @author Ben
 */
public class Application extends SApplication implements Runnable, ComponentListener, MouseListener, MouseMotionListener, KeyListener, ActionListener
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
    
    SButton resetButton;
    SButton quitButton;
    SButton runButton;
    SButton stepButton;
    SRadioButton gridButton;
    
    SRadioController speedSelectorController;
    SRadioButton[] speedSelectors = new SRadioButton[10];
    SRadioController ruleSelectorController;
    SRadioButton[] ruleSelectors = new SRadioButton[7];
    
    SSlider speedSlider;
    
    STextInput speedMax;
    STextInput speedMin;
    STextInput speedStep;
    
    QuitWindow quitWindow = null;
    
    public static Color backgroundColor = new Color(250, 250, 250);
    public static Color gridColor;
    public static Color gridBackgroundColor;
    
    public static Dimension screenSize;
    public static double scaleX = 1;
    public static double scaleY = 1;
            
        
    public static boolean paused = true;
    
    public Application()
    {
        super();
        
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //super.setSize(1920, 1080);
        //super.setSize(1500, 1500);
        //setSize(3200, 1800);
        setSize(screenSize.width, screenSize.height);
        
        //scaleX = 3200/(double)super.getSize().getWidth();
        //scaleY = 1800/(double)super.getSize().getHeight();
                
        System.out.print("Initializing Frontend: ");
                
        super.setScale(scaleX, scaleY);
        
        long initStart = System.currentTimeMillis();
        
        buildUI();
        
        super.setUndecorated(true);
        super.setVisible(true);
        dbImage = super.createImage(screenSize.width, screenSize.height);
        dbGraphics = dbImage.getGraphics();
        
        cellW = 3200/GamesOfLife.getGridWidth();
        cellH = 1800/GamesOfLife.getGridHeight();
                
        halted = false;
        
        System.out.println(System.currentTimeMillis() - initStart + "ms");
        
        conwayMode();
        
        super.repaint();
    }
    
    private void conwayMode()
    {
        gridBackgroundColor = Color.lightGray;
        gridColor = Color.white;
    }
    
    private void wireworldMode()
    {
        gridBackgroundColor = new Color(31, 129, 5);
        gridColor = Color.black;
    }
    
    private void viralMode()
    {
        gridBackgroundColor = new Color(200, 200, 255);
        gridColor = Color.gray;
    }
    
    @Override
    public void run() 
    {
        while(!halted)
        {
            //System.out.println("Running Frontend");
            
            //if(quitWindow != null)
                
            
            if(quitWindow != null && quitWindow.isActive())
            {
                controller.disable();
            }
            else if(quitWindow == null || !quitWindow.isActive())
            {
                controller.enable();
            }
            
            repaint();
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void halt(String cause)
    {
        System.out.println("Frontend halted for reason: " + cause);
        halted = true;
        
        if(quitWindow != null)
            quitWindow.halt(cause);
        
        controller.halt();
        
        this.dispose();
    }
    
    @Override
    protected void buildUI()
    {
        super.buildUI();        
        resetButton = new SButton(100, 1650, 200, 100, "Reset", controller);
        resetButton.setColor(new Color(100, 100, 255));
        runButton = new SButton(500, 1650, 300, 100, "Run / Pause", controller);
        runButton.setColor(Color.green);
        stepButton = new SButton(900, 1650, 100, 100, "Step", controller);
        stepButton.setColor(Color.lightGray);
        gridButton = new SRadioButton(1100, 1650, 200, 100, "Toggle Grid", controller);
        gridButton.toggleOn();
        quitButton = new SButton(3000, 1650, 100, 100, "Quit", controller);
        quitButton.setColor(new Color(225, 100, 100));
        
        speedSlider = new SSlider(1575, 1675, 700, 50, 50, 1000, (float) 50, controller);
        speedSlider.setValue(100);
        speedSlider.setLabelPrefix("Step: ");
        speedSlider.setLabelSuffex("ms");
        
        speedMin = new STextInput(1425, 1675, 100, 50, controller);
        speedMin.setValue("" + speedSlider.getMinValue());
        speedMin.setFont(new Font("", Font.BOLD, 30));
        
        speedMax = new STextInput(2325, 1675, 100, 50, controller);
        speedMax.setValue("" + speedSlider.getMaxValue());
        speedMax.setFont(new Font("", Font.BOLD, 30));
        
        int ssX = 1400;
        int ssY = 1675;
        int ssGap = 20;
        int ssWidth = 125;
        int ssHeight = 50;
        speedSelectorController = new SRadioController(controller);
        speedSelectors[0] =  new SRadioButton(ssX, ssY, ssWidth, ssHeight, "25ms", controller);
        speedSelectors[1] =  new SRadioButton(ssX+ssGap+ssWidth, ssY, ssWidth, ssHeight, "50ms", controller);
        speedSelectors[2] =  new SRadioButton(ssX+2*ssGap+2*ssWidth, ssY, ssWidth, ssHeight, "75ms", controller);
        speedSelectors[3] =  new SRadioButton(ssX+3*ssGap+3*ssWidth, ssY, ssWidth, ssHeight, "100ms", controller);
        speedSelectors[4] =  new SRadioButton(ssX+4*ssGap+4*ssWidth, ssY, ssWidth, ssHeight, "200ms", controller);
        speedSelectors[5] =  new SRadioButton(ssX+5*ssGap+5*ssWidth, ssY, ssWidth, ssHeight, "300ms", controller);
        speedSelectors[6] =  new SRadioButton(ssX+6*ssGap+6*ssWidth, ssY, ssWidth, ssHeight, "400ms", controller);
        speedSelectors[7] =  new SRadioButton(ssX+7*ssGap+7*ssWidth, ssY, ssWidth, ssHeight, "500ms", controller);
        speedSelectors[8] =  new SRadioButton(ssX+8*ssGap+8*ssWidth, ssY, ssWidth, ssHeight, "1000ms", controller);
        speedSelectors[9] =  new SRadioButton(ssX+9*ssGap+9*ssWidth, ssY, ssWidth, ssHeight, "2000ms", controller);
                
        speedSelectorController.add(speedSelectors);
        speedSelectorController.setDefaultButton(speedSelectors[0]);
        
        int rsX = 100;
        int rsY = 1525;
        int rsGap = 40;
        int rsWidth = 300;
        int rsHeight = 70;
        ruleSelectorController = new SRadioController(controller);
        ruleSelectors[0] = new SRadioButton(rsX, rsY, rsWidth, rsHeight, "Conway", controller);
        ruleSelectors[1] = new SRadioButton(rsX+rsGap+rsWidth, rsY, rsWidth, rsHeight, "Wireworld", controller);
        ruleSelectors[2] = new SRadioButton(rsX+2*rsGap+2*rsWidth, rsY, rsWidth, rsHeight, "Viral", controller);
        ruleSelectors[3] = new SRadioButton(rsX+3*rsGap+3*rsWidth, rsY, rsWidth, rsHeight, "Conway Custom", controller);
        ruleSelectors[4] = new SRadioButton(rsX+4*rsGap+4*rsWidth, rsY, rsWidth, rsHeight, "Wireworld Custom", controller);
        ruleSelectors[5] = new SRadioButton(rsX+5*rsGap+5*rsWidth, rsY, rsWidth, rsHeight, "Viral Custom", controller);
        ruleSelectors[6] = new SRadioButton(rsX+6*rsGap+6*rsWidth, rsY, rsWidth, rsHeight, "Custom", controller);
        
        ruleSelectorController.add(ruleSelectors);
        ruleSelectorController.setDefaultButton(ruleSelectors[0]);
        
        for(SRadioButton rb : ruleSelectors)
        {
            rb.setColor(new Color(250, 250, 250));
        }
        ruleSelectors[0].setColor(Color.YELLOW);
        ruleSelectors[1].setColor(Color.YELLOW);
        ruleSelectors[2].setColor(Color.YELLOW);
        ruleSelectors[0].toggleOn();
        
        // NOT YET AVAILABLE!!!!!!!!
            ruleSelectors[3].disable();
            ruleSelectors[3].setVisible(false);
            ruleSelectors[4].disable();
            ruleSelectors[4].setVisible(false);
            ruleSelectors[5].disable();
            ruleSelectors[5].setVisible(false);
            ruleSelectors[6].disable();
            ruleSelectors[6].setVisible(false);
        
        controller.add(resetButton);
        controller.add(runButton);
        controller.add(stepButton);
        controller.add(gridButton);
        controller.add(quitButton);
        controller.add(speedSlider);
        controller.add(speedMin);
        controller.add(speedMax);
        
        controller.add(ruleSelectorController);
        //controller.add(speedSelectorController);
        
    }

    @Override
    public void paint(Graphics g) 
    {     
        if(dbGraphics != null)
        { 
            super.paint(dbGraphics);
            
            dbGraphics.setColor(backgroundColor);
            dbGraphics.fillRect(0, 0, 3200, 1800);
           // g.setColor(Color.BLACK);
            
            
            controller.paint(dbGraphics);

            for(int i = 1; i < GamesOfLife.getGridWidth()-2; ++i)
            {
                for(int j = 1; j < GamesOfLife.getGridHeight()-1; ++j)
                {
                    if((i*cellW) > 95 
                        && (j*cellH) > 95
                        && (i*cellW) < 3200 - 95
                        && (j*cellH) < 1800 - 325)
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

            dbGraphics.setFont(new Font("title font", Font.BOLD, 52));
            String title = "Games Of Life: Cellular Atomata";
            dbGraphics.drawString(title, 1600-dbGraphics.getFontMetrics().stringWidth(title)/2, 70);

            
            
            Integer tickCount = GamesOfLife.tickCount;
            int tickCountWidth = dbGraphics.getFontMetrics().stringWidth(tickCount.toString());
            dbGraphics.drawString("T -  " + GamesOfLife.tickCount, (int)((3000)-tickCountWidth), 1550);

            dbGraphics.setFont(new Font("Normal", Font.PLAIN, 12));

        }

        if(dbImage != null)
        {
            g.drawImage(dbImage.getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_DEFAULT), 0, 0, this);
        }
        
    }
    
    private Cell getCellClicked(double mouseX, double mouseY) throws ArrayIndexOutOfBoundsException
    {
        Cell c = null;
        
        if(mouseY < 0 || mouseY > GamesOfLife.getGridWidth()*cellH)
            throw new ArrayIndexOutOfBoundsException("Index out of bounds");
        
        c = GamesOfLife.getCells()[(int)(mouseX/ cellW)][(int)(mouseY / cellH)];
        
        return c;
    }
    
     private void writeCells(Cell c)
    {        
        if(mousePressEvent.getButton() == 1)
        {
            if(GamesOfLife.rules.equals("conway"))
            {
                    c.setStatus(Cell.ALIVE);
            }

            else if( GamesOfLife.rules.equals("wireworld"))
            {
                    c.setStatus(Cell.CONDUCTOR);
            }

            else if(GamesOfLife.rules.equals("viral"))
            {
                    c.setStatus(Cell.V1);
            }
        }

        else if(mousePressEvent.getButton() == 3)
        {
                c.setStatus(Cell.DEAD);
        }
        
        else if(mousePressEvent.getButton() == 2)
        {
            if(GamesOfLife.rules.equals("wireworld"))
            {
                if(c.getStatus() == Cell.ELECTRON_HEAD)
                    c.setStatus(Cell.CONDUCTOR);
                else if(c.getStatus() == Cell.CONDUCTOR)
                    c.setStatus(Cell.ELECTRON_HEAD);
            }
            else if(GamesOfLife.rules.equals("viral"))
            {
                c.setStatus(Cell.V2);
            }
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

            //System.out.println(x0 + ", " + y0 + ", " + x1 + ", " + y1 + ": " + slope);

            
            
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

            //System.out.println(x0 + ", " + y0 + ", " + x1 + ", " + y1 + ": " + slope);
            
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
    
    private void quit()
    {
        if(quitWindow != null && !quitWindow.isHalted())
            return;
        controller.disable();
        quitWindow = new QuitWindow();
        quitWindow.setAlwaysOnTop(true);
        quitWindow.start();

        //System.out.println("PAUSE ACTIVATED");
        paused = true;
        resetButton.enable();

        controller.disable();
    }

    @Override
    public void mouseClicked(MouseEvent me) 
    {          
        if(!halted)
        {
            mouseX2 = getMousePosition().getX();
            mouseY2 = getMousePosition().getY();
        }
    }

    @Override
    public Point getMousePosition() throws HeadlessException {
        return super.getMousePosition(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent me) 
    {        
        mousePressed = true;
        mousePressEvent = me;
        
        mouseX2 = getMousePosition().getX();
        mouseY2 = getMousePosition().getY();
        
        Cell c = getCellClicked(mouseX2, mouseY2);
        
        writeCells(c);
        
        repaint();
        
        //controller.mousePressed(me);
    }

    @Override
    public void mouseReleased(MouseEvent me) 
    {
        //controller.mouseReleased(me);
        
        Main.frontend.setPriority(Thread.MIN_PRIORITY);
        Main.backend.setPriority(Thread.MAX_PRIORITY);
        
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
    
    @Override
    public void mouseDragged(MouseEvent me) {
        if(mousePressed)
        {   
            mouseX1 = getMousePosition().getX();
            mouseY1 = getMousePosition().getY();
            
            if(mouseY1 < 1600 && mouseY2 < 1600)
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
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) 
    {
        if(e.getKeyCode() == 27) // Escape Key
        {
            quit();
            repaint();
            return;
        }
        
        if(e.getKeyCode() == 32) // Space Bar
        {
            paused = !paused;
            resetButton.toggleDisable();
        }
        
        
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
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
            quit();
            repaint();
            return;
        }
        else if(e.getSource().equals(runButton))
        {
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
        
        else if(e.getSource().equals(speedSlider))
        {
            GamesOfLife.tickInterval = (int)speedSlider.getValue();
            repaint();
            return;
        }
        
        else if(e.getSource().equals(speedMax))
        {
            speedSlider.setMaxValue((float)speedMax.getDouble());
            
            speedSlider.refresh();
            
            repaint();
            return;
        }
        
        else if(e.getSource().equals(speedMin))
        {
            speedSlider.setMinValue((float)speedMin.getDouble());
                        
            speedSlider.refresh();
            
            repaint();
            return;
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
                            viralMode();
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
