/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamesoflife;

import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;


/**
 *
 * @author Ben
 */
public class GamesOfLife implements Runnable
{    
    /**
     * "conway"
     * "viral"
     * "wireworld"
     * custom-conway"
     * "custom-viral"
     * "custom-wireworld"
     * "custom"
     */
    public static String rules;
    private static int gridWidth = 320; //160
    private static int gridHeight = 180; //90
    
    public static int tickCount = 0;
        
    private static Cell[][] cells = new Cell[gridWidth][gridHeight];
    
    private boolean halted;
    
    private static long prevTickTime;
    
    public static int tickInterval = 20;
    
    public static boolean initialized = false;

    public GamesOfLife()
    {
        System.out.print("Initializing Backend: ");
        long initStart = System.currentTimeMillis();
        
        rules = "conway";
        
        for(int i = 0; i < gridWidth; ++i)
        {
            for (int j = 0; j < gridHeight; j++) 
            {
                cells[i][j] = new Cell(i, j);
            }
        }
        
        for(int i = 1; i < gridWidth-1; ++i)
        {
            for (int j = 1; j < gridHeight-1; j++) 
            {
                cells[i][j].setNorth(cells[i][j-1]);
                cells[i][j].setNortheast(cells[i+1][j-1]);
                cells[i][j].setEast(cells[i+1][j]);
                cells[i][j].setSoutheast(cells[i+1][j+1]);
                cells[i][j].setSouth(cells[i][j+1]);
                cells[i][j].setSouthwest(cells[i-1][j+1]);
                cells[i][j].setWest(cells[i-1][j]);
                cells[i][j].setNorthwest(cells[i-1][j-1]);
            }
        }
        
        halted = false;
        
        System.out.println(System.currentTimeMillis() - initStart + "ms");
        
        initialized = true;
    }

    @Override
    public void run() 
    {
        while(!halted)
        {
            //System.out.println("Running Backend...");
            
            if(!Application.paused && System.currentTimeMillis()-prevTickTime > tickInterval)
            {
                tick();
            }
            
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void tick()
    {
        for (int j = 0; j < gridWidth; j++) 
        {
            for (int k = 0; k < gridHeight; k++) 
            {
                cells[j][k].cycle(rules);
            }
        }

        for (int j = 0; j < gridWidth; j++) 
        {
            for (int k = 0; k < gridHeight; k++) 
            {
                cells[j][k].updateStatus();
            }
        }
        tickCount++;
        prevTickTime = System.currentTimeMillis();
    }
    
    public static void reset()
    {
        for(int i = 0; i < gridWidth; ++i)
        {
            for (int j = 0; j < gridHeight; j++) 
            {
                cells[i][j].flushTempStatus();
            }
        }
            
        tickCount = 0;
    }

    public static int getGridHeight() {
        return gridHeight;
    }

    public static int getGridWidth() {
        return gridWidth;
    }

    public static Cell[][] getCells() {
        return cells;
    }
    
    public void halt(String cause)
    {
        System.out.println("Backend halted for reason: " + cause);
        halted = true;
    }

    public static void setGridWidth(int gridWidth) {
        GamesOfLife.gridWidth = gridWidth;
    }

    public static void setGridHeight(int gridHeight) {
        GamesOfLife.gridHeight = gridHeight;
    }
}
