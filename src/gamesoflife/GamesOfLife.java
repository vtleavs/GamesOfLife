/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamesoflife;

/**
 *
 * @author Ben
 */
public class GamesOfLife 
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        rules = "conway";
        int gridWidth = 20;
        int gridHeight = 20;
        
        Cell[][] cells = new Cell[gridWidth][gridHeight];
        
        for(int i = 0; i < gridWidth; ++i)
        {
            for (int j = 0; j < gridHeight; j++) 
            {
                cells[i][j] = new Cell(i, j);
            }
        }
        
        for(int i = 1; i < gridHeight-1; ++i)
        {
            for (int j = 1; j < gridWidth-1; j++) 
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
        
        cells[4][4].setStatus(Cell.ALIVE);
        cells[4][5].setStatus(Cell.ALIVE);
        cells[5][4].setStatus(Cell.ALIVE);
        cells[7][7].setStatus(Cell.ALIVE);
        cells[7][6].setStatus(Cell.ALIVE);
        cells[6][7].setStatus(Cell.ALIVE);
        
        for(int i = 0; i < 10; ++i)
        {
            for (int j = 0; j < gridWidth; j++) 
            {
                for (int k = 0; k < gridHeight; k++) 
                {
                    if(cells[k][j].getStatus() == Cell.DEAD)
                        System.out.print("- ");
                    if(cells[k][j].getStatus() == Cell.ALIVE)
                        System.out.print("X ");
                }
                System.out.println();
            }
            
            System.out.println();
            
            for (int j = 0; j < gridWidth; j++) 
            {
                for (int k = 0; k < gridHeight; k++) 
                {
                    cells[j][k].cycle();
                }
            }
            
            for (int j = 0; j < gridHeight; j++) 
            {
                for (int k = 0; k < gridWidth; k++) 
                {
                    cells[j][k].updateStatus();
                }
            }
            
            
        }
    }
    
}
