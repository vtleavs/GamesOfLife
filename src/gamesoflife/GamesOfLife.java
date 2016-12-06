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
    private static int gridWidth = 160;
    private static int gridHeight = 90;
        
    private static Cell[][] cells = new Cell[gridWidth][gridHeight];

    public GamesOfLife()
    {
        rules = "custom-conway";
        
        
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
        
    }

    @Override
    public void run() 
    {
        while(true)
        {
            System.out.println("Running Backend");
            
            if(!Application.paused)
            {
    //            for (int j = 0; j < gridWidth; j++) 
    //            {
    //                for (int k = 0; k < gridHeight; k++) 
    //                {
    //                    if(cells[k][j].getStatus() == Cell.DEAD)
    //                        System.out.print("- ");
    //                    if(cells[k][j].getStatus() == Cell.ALIVE)
    //                        System.out.print("X ");
    //                }
    //                System.out.println();
    //            }

                System.out.println();

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
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GamesOfLife.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
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
}
