/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golGUI;

import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBase;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author Benji
 */

public class GOLTextInput extends GOLElement
{
    private String value = " ";
    
    private String[] words;
    
    private int cursorIndex = 0;
    private Font font = new Font("normal", Font.PLAIN,12);
    
    private boolean focused;
    
    public GOLTextInput(int x, int y, int width, int height, GOLController controller) 
    {
        super(x, y, width, height, controller);
    }

    @Override
    public void paint(Graphics g) {
        
        g.setFont(font);
        
        int newWidth;
        if(g.getFontMetrics().stringWidth(value) + 20 > width)
            newWidth = g.getFontMetrics().stringWidth(value) + 20;
        else
            newWidth = width;
        
        g.setColor(Color.white);
        g.fillRect(xLocation, yLocation, newWidth, height);
        
        g.setColor(Color.gray);
        g.drawRect(xLocation, yLocation, newWidth, height);
        
        if(focused)
        {
            g.setColor(new Color(9,223,255));
            //g.drawRect(xLocation, yLocation, width, height);
            g.drawRect(xLocation-1, yLocation-1, newWidth+2, height+2);
            g.setColor(new Color(9,223,225, 50));
            g.drawRect(xLocation-2, yLocation-2, newWidth+4, height+4);
        }
        
        g.setColor(Color.black);
        g.drawString(value, xLocation + 10, yLocation + font.getSize()+ 5);
        
        
//        g.drawLine(xLocation + 5 + g.getFontMetrics().stringWidth(value.substring(0, cursorIndex)), 
//                yLocation + width/2 - font.getSize(), 
//                xLocation + 5 + g.getFontMetrics().stringWidth(value.substring(0, cursorIndex)), 
//                yLocation + width/2 + font.getSize());

        if(focused)
        {
            g.drawLine(xLocation + 9 + g.getFontMetrics().stringWidth(value.substring(0, cursorIndex)), 
                yLocation + 10, 
                xLocation + 9 + g.getFontMetrics().stringWidth(value.substring(0, cursorIndex)), 
                yLocation + height - 10);
            
            g.drawLine(xLocation + 10 + g.getFontMetrics().stringWidth(value.substring(0, cursorIndex)), 
                yLocation + 10, 
                xLocation + 10 + g.getFontMetrics().stringWidth(value.substring(0, cursorIndex)), 
                yLocation + height - 10);
        }
    }
    
//    double findCursorPosition(Graphics g)
//    {
//        return xLocation + 10 + g.getFontMetrics()
//    }

    @Override
    public ActionEvent mousePressed(MouseEvent e) {
        
        if(isCaptured())
        {
            focused = true;
        }
        else
            focused = false;
        
        return new ActionEvent(this, 937823, name);
    }
    
    public ActionEvent keyPressed(KeyEvent ke)
    {
        if(!focused)
        {
            return null;
        }
        
        switch(ke.getKeyCode())
        {
//            case 32: insertCharAtCursor(' ');
//                break;
            case 8: removeCharAtCursorBackward();
                break;
            case 10: focused = false;
                break;
            case 127: removeCharAtCursorForward();
                break;
            case 37: cursorIndex--;
                break;
            case 39: cursorIndex++;
                break;
            default: insertCharAtCursor(ke.getKeyChar());
        }
            
        
        System.out.println(ke.getKeyCode());
        
        return new ActionEvent(this, 345345, name);
    }
    
    void insertCharAtCursor(char c)
    {
        value = value.substring(0, cursorIndex) + c + value.substring(cursorIndex);
        cursorIndex++;
    }
    
    void removeCharAtCursorBackward()
    {   
        value = value.substring(0, cursorIndex-1) + value.substring(cursorIndex);
        cursorIndex--;
    }
    
    void removeCharAtCursorForward()
    {
        value = value.substring(0, cursorIndex) + value.substring(cursorIndex+1);
    }
    
    
    public void parseWords()
    {
        // should find all words and populate the words array
    }
    
    public char getCharAtCursor()
    {
        return getCharAt(cursorIndex);
    }
    
    public char getCharAt(int index)
    {
        return value.charAt(index);
    }
    
    public String getWordAtCursor()
    {
        return getWordAt(cursorIndex);
    }
    
    public String getWordAt(int index)
    {
        return words[index];
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return font;
    }

    public int getCursorIndex() {
        return cursorIndex;
    }

    public void setCursorIndex(int cursorIndex) {
        this.cursorIndex = cursorIndex;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public int getLength()
    {
        return value.length();
    }

    public int wordcount() {
        return words.length;
    }
    
    public int getInt()
    {
        return Integer.parseInt(value);
    }
    
    public double getDouble()
    {
        return Double.parseDouble(value);
    }
    
}
