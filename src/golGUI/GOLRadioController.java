/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golGUI;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author Benji
 */
public class GOLRadioController extends GOLElement 
{
    private ArrayList<GOLRadioButton> radioButtons;
    private GOLButton defaultButton;
    
    private Point upperLeftCorner;
    private Point lowerRightCorner;

    public GOLRadioController() 
    {
        radioButtons = new ArrayList<>();
    }
    
    public void add(GOLRadioButton button)
    {
        radioButtons.add(button);
    }
    
    public void add(GOLRadioButton[] buttons)
    {
        for(GOLRadioButton b : buttons)
            add(b);
    }
    
    public void remove(GOLRadioButton button)
    {
        radioButtons.remove(button);
    }
    
    public int numButtons()
    {
        return radioButtons.size();
    }
    
    public void setDefaultButton(GOLRadioButton button)
    {
        defaultButton = button;
        radioControl(button);
    }
    
    public void setDefaultButton(int index)
    {
        defaultButton = radioButtons.get(index);
    }

    @Override
    public void paint(Graphics g) {
        for(GOLRadioButton b : radioButtons)
        {
            b.paint(g);
        }
    }

    @Override
    public ActionEvent mouseClicked(MouseEvent e) {        
        for(GOLRadioButton b : radioButtons)
        {
            if(e.getButton() == 1 && b.isCaptured() && !b.isDisabled())
            {
                radioControl(b);
                return new ActionEvent(b, 195819, b.getName());
            }
        }
        return null;
    }
    
    private void radioControl(GOLRadioButton button)
    {
        button.toggleOn();
        for(GOLRadioButton b2 : radioButtons)
        {
            if(!b2.equals(button))
            {
                b2.toggleOff();
            }

        }
    }
    
    public String[] getLinkedButtonNames() {
        String[] names = new String[100];
        for(int i = 0; i < radioButtons.size(); ++i)
            names[i] = radioButtons.get(i).getName();
        return names;
    }

    public ArrayList<GOLRadioButton> getRadioButtons() {
        return radioButtons;
    }

    @Override
    public ActionEvent mousePressed(MouseEvent e) {
        return super.mousePressed(e);
    }

    @Override
    public ActionEvent mouseReleased(MouseEvent e) {
        return super.mouseReleased(e);
    }
    
    
}
