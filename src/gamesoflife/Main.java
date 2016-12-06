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
public class Main {

    /**
     * @param args the command line arguments
     */
    
    static Thread frontend;
    static Thread backend;
    
    public static void main(String[] args) 
    {
        GamesOfLife gol = new GamesOfLife();
        Application app = new Application();
        
        frontend = new Thread(app);
        backend = new Thread(gol);
        
        frontend.setPriority(Thread.MIN_PRIORITY);
        backend.setPriority(Thread.MAX_PRIORITY);
        
        frontend.start();
        backend.start();
    }
    
}
