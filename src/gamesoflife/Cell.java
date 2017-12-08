/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamesoflife;

import java.awt.Color;
import java.awt.Graphics;
import sun.net.www.content.audio.x_aiff;

/**
 *
 * @author Ben
 */
public class Cell 
{
    public static final char DEAD = 0;
    public static final char ALIVE = 1;
    public static final char CONDUCTOR = 2;
    public static final char ELECTRON_HEAD = 3;
    public static final char ELECTRON_TAIL = 4;
    public static final char V1 = 5;
    public static final char V2 = 6;
    public static final char V3 = 7;
    public static final char V4 = 8;
    public static final char V5 = 9;
    public static final char V6 = 10;
    
    /**
     * 0 - Dead
     * 1 - Alive
     * 2 - Conductor
     * 3 - Electron Head
     * 4 - Electron Tail
     * 5:55 - Virus Type 1:50
    */
    protected char status;
    protected char tempStatus;
    
    protected int x;
    protected int y;
    
    protected Cell north;
    protected Cell northeast;
    protected Cell east;
    protected Cell southeast;
    protected Cell south;
    protected Cell southwest;
    protected Cell west;
    protected Cell northwest;
    
    public Cell(int x, int y)
    {
        this.status = DEAD;
        this.x = x;
        this.y = y;
    }
    
    public Cell(char status, int x, int y)
    {
        this.status = status;
        this.x = x;
        this.y = y;
    }
    
    public void paint(Graphics g, int xl, int yl, int w, int h)
    {
        g.setColor(deterimineColor());
        g.fillRect(xl, yl, w, h);
        g.setColor(Color.BLACK);
    }
    
    public void cycle(String rules)
    {
        if(rules.equals("conway"))
            conway();
        if(rules.equals("viral"))
            viral();
        if(rules.equals("wireworld"))
            wireworld();
        if(rules.equals("custom-conway"))
            customConway();
        if(rules.equals("custom-viral"))
            customViral();
        if(rules.equals("custom-wireworld"))
            customWireworld();
        if(rules.equals("custom"))
            custom();
    }
    
    private Color deterimineColor()
    {
        if(status == DEAD)
            return Application.gridBackgroundColor;
        if(status == ALIVE)
            return Color.BLACK;
        if(status == CONDUCTOR)
            return Color.ORANGE;
        if(status == ELECTRON_HEAD)
            return Color.BLUE;
        if(status == ELECTRON_TAIL)
            return Color.GREEN;
        if(status == V1)
            return Color.BLUE;
        if(status == V2)
            return Color.GREEN;
        if(status == V3)
            return Color.RED;
        if(status == V4)
            return Color.ORANGE;
        if(status == V5)
            return Color.YELLOW;
        if(status == V6)
            return Color.CYAN;
        return Color.LIGHT_GRAY;
    }
    
    public void conway() throws IllegalArgumentException
    {
        if(this.status == ALIVE)
        {
            if(countNeighborStatus(ALIVE) < 2)
                tempStatus = DEAD;
            else if(countNeighborStatus(ALIVE) == 2 
                    || countNeighborStatus(ALIVE) == 3)
                tempStatus = ALIVE;
            else if(countNeighborStatus(ALIVE) > 3)
                tempStatus = DEAD;
        }
        else if(this.status == DEAD)
        {
            if(countNeighborStatus(ALIVE) == 3)
                tempStatus = ALIVE;
        }
        else
        {
            throw new IllegalArgumentException(
                    "Conway's Rules Allow Only 2 Types, DEAD or ALIVE");
        }
    }
    
    public void viral()
    {        
        if(this.status == DEAD)
        {
            if(countNeighborStatus(V1) > 2 && countNeighborStatus(V1) > countNeighborStatus(V2))
                tempStatus = V1;
            else if(countNeighborStatus(V2) > 2 && countNeighborStatus(V2) > countNeighborStatus(V1))
                tempStatus = V2;
        }
        
        if(this.status == V1)
        {
            if(countNeighborStatus(V1) <= 2)
                tempStatus = DEAD;
            else if(countNeighborStatus(V1) >= 6)
                tempStatus = DEAD;
            
            if(countNeighborStatus(V2) > countNeighborStatus(V1))
                tempStatus = DEAD;
        }  
    
        if(this.status == V2)
        {
            if(countNeighborStatus(V2) <= 2)
                tempStatus = DEAD;
            else if(countNeighborStatus(V2) >= 5)
                tempStatus = DEAD;
            
            if(countNeighborStatus(V1) > countNeighborStatus(V2))
                tempStatus = DEAD;
        }  
        
        
//        
//        if(this.status == V1)
//        {
//            if(countNeighborStatus(V1) < 2)
//                tempStatus = DEAD;
//            else if(countNeighborStatus(V1) == 2 
//                    || countNeighborStatus(V1) == 3)
//                tempStatus = V1;
//            else if(countNeighborStatus(V1) > 5)
//                tempStatus = DEAD;
//            
//            if(countNeighborStatus(V2) > countNeighborStatus(V1)+1)
//                tempStatus = DEAD;
//            
////            if(countNeighborStatus(V2) > 0)
////            {
////                
////            }
//        }
//        
//        
//        else if(this.status == V2)
//        {
//            if(countNeighborStatus(V2) < 2)
//                tempStatus = DEAD;
//            else if(countNeighborStatus(V2) == 2 
//                || countNeighborStatus(V2) == 3)
//                tempStatus = V2;
//            else if(countNeighborStatus(V2) > 5)
//                tempStatus = DEAD;
//            
//            if(countNeighborStatus(V1) > countNeighborStatus(V2)+1)
//                tempStatus = DEAD;
//        }
//        
//        else if(this.status == DEAD)
//        {
//            if(countNeighborStatus(V1) >= 3 && countNeighborStatus(V2) < 4)
//                tempStatus = V1;
//            else if(countNeighborStatus(V2) >= 3 && countNeighborStatus(V1) < 4)
//                tempStatus = V2;
//        }
        
    }
    
    public void wireworld()
    {
        if(this.status == ELECTRON_HEAD)
            tempStatus = ELECTRON_TAIL;
        else if(this.status == CONDUCTOR)
        {
            if(countNeighborStatus(ELECTRON_HEAD) == 1
                    || countNeighborStatus(ELECTRON_HEAD) == 2)
                tempStatus = ELECTRON_HEAD;
            else
                tempStatus = CONDUCTOR;
        }
        else if(this.status == ELECTRON_TAIL)
            tempStatus = CONDUCTOR;
        else if(this.status == DEAD)
            tempStatus = DEAD;
            
    }
    
    public void customConway()
    {
        if(this.status == ALIVE)
        {
            if(countNeighborStatus(ALIVE) < 1)
                tempStatus = DEAD;
            else if(countNeighborStatus(ALIVE) == 2 
                    || countNeighborStatus(ALIVE) == 3)
                tempStatus = ALIVE;
            else if(countNeighborStatus(ALIVE) > 3)
                tempStatus = DEAD;
        }
        else if(this.status == DEAD)
        {
            if(countNeighborStatus(ALIVE) == 3)
                tempStatus = ALIVE;
        }
        else
        {
            throw new IllegalArgumentException(
                    "Conway's Rules Allow Only 2 Types, DEAD or ALIVE");
        }
    }
    
    public void customViral()
    {
        
    }
    
    public void customWireworld()
    {
        
    }
    
    public void custom()
    {
        
    }
    
    private char countNeighborStatus(char nStatus)
    {
        char result = 0;
        if(north != null && north.getStatus() == nStatus) { result++; }
        if(northeast != null && northeast.getStatus() == nStatus) { result++; }
        if(east != null && east.getStatus() == nStatus) { result++; }
        if(southeast != null && southeast.getStatus() == nStatus) { result++; }
        if(south != null && south.getStatus() == nStatus) { result++; }
        if(southwest != null && southwest.getStatus() == nStatus) { result++; }
        if(west != null && west.getStatus() == nStatus) { result++; }
        if(northwest != null && northwest.getStatus() == nStatus) { result++; }
        return result;
    }

    public void updateStatus()
    {
        status = tempStatus;
    }

    public char getStatus() {
        return status;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public void setNorth(Cell north) {
        this.north = north;
    }

    public void setNortheast(Cell northeast) {
        this.northeast = northeast;
    }

    public void setEast(Cell east) {
        this.east = east;
    }

    public void setSoutheast(Cell southeast) {
        this.southeast = southeast;
    }

    public void setSouth(Cell south) {
        this.south = south;
    }

    public void setSouthwest(Cell southwest) {
        this.southwest = southwest;
    }

    public void setWest(Cell west) {
        this.west = west;
    }

    public void setNorthwest(Cell northwest) {
        this.northwest = northwest;
    }
    
    public void flushTempStatus()
    {
        status = DEAD;
        tempStatus = DEAD;
    }
}
