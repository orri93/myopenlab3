//*****************************************************************************
//* Element of MyOpenLab Library                                              *
//*                                                                           *
//* Copyright (C) 2004  Carmelo Salafia (cswi@gmx.de)                         *
//*                                                                           *
//* This library is free software; you can redistribute it and/or modify      *
//* it under the terms of the GNU Lesser General Public License as published  *
//* by the Free Software Foundation; either version 2.1 of the License,       *
//* or (at your option) any later version.                                    *
//* http://www.gnu.org/licenses/lgpl.html                                     *
//*                                                                           *
//* This library is distributed in the hope that it will be useful,           *
//* but WITHOUTANY WARRANTY; without even the implied warranty of             *
//* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                      *
//* See the GNU Lesser General Public License for more details.               *
//*                                                                           *
//* You should have received a copy of the GNU Lesser General Public License  *
//* along with this library; if not, write to the Free Software Foundation,   *
//* Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110, USA                  *
//*****************************************************************************


import VisualLogic.*;
import VisualLogic.variables.*;
import tools.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;
import java.awt.geom.Rectangle2D;

public class Const extends JVSMain
{
  private int pw=7;  // Pin Width
  private int width, height;
  private VSInteger uniChar= new VSInteger(92);
  private VSString constValue=new VSString(String.valueOf(uniChar.getValue()));
  private Graphics2D g2;
  private Font fnt = new Font("Courier",1,12);
  private VSString out= new VSString();
  
  
  private boolean changed=false;

  
  public Const()
  {
    width=80;
    height=25;
  }

   public void resizeWidth()
   {
       g2.setFont(fnt);
       String caption="ASCCI "+uniChar.getValue();
       FontMetrics fm = g2.getFontMetrics();
       Rectangle2D   r = fm.getStringBounds(caption,g2);
       int newWidth=(int)r.getWidth()+20;
       int diff=(width-newWidth);
       element.jSetSize(newWidth,height);
       element.jSetLeft(element.jGetLeft()+diff);
       width=newWidth;
   }

   public void paint(java.awt.Graphics g)
   {
    if (element!=null)
    {
       g2=(Graphics2D)g;
       Rectangle bounds=element.jGetBounds();
       g2.setFont(fnt);
       g2.setColor(Color.black);

       String caption="ASCCI "+uniChar.getValue();
       FontMetrics fm = g2.getFontMetrics();
       Rectangle2D   r = fm.getStringBounds(caption,g2);

       g2.setColor(new Color(245,101,234));
       g.fillRect(bounds.x,bounds.y,bounds.width-1,bounds.height-1);
       
       g2.setColor(Color.black);
       g.drawString(caption,bounds.x+5,((bounds.height) /2)+5);
       g.drawRect(bounds.x,bounds.y,bounds.width-1,bounds.height-1);
       //resizeWidth();
    }
   }

  public void init()
  {
    initPins(0,1,0,0);
    setSize(width,height);

    element.jSetInnerBorderVisibility(false);
    element.jSetTopPinsVisible(false);
    element.jSetLeftPinsVisible(false);
    element.jSetBottomPinsVisible(false);

    setPin(0,ExternalIF.C_STRING,element.PIN_OUTPUT);
    element.jSetPinDescription(0,"Out");
    setName("ASCCI_Const");
    constValue=new VSString(String.valueOf(uniChar.getValue()));

  }
  

  public void initInputPins()
  {
  }

  public void initOutputPins()
  {
    element.setPinOutputReference(0,out);
  }




  public void setPropertyEditor()
  {
    element.jAddPEItem("Ascci Char (0 - 255)",uniChar, 0,255);
    localize();
  }


  private void localize()
  {
    int d=6;
    String language;

    language="en_US";

    element.jSetPEItemLocale(d+0,language,"Ascci Value (0-255)");

    language="es_ES";

    element.jSetPEItemLocale(d+0,language,"Valor (Ascci 0-255)");
  }

  public void propertyChanged(Object o)
  {
    resizeWidth();
    element.jRepaint();
  }

  public void start()
  {   int byte1 = uniChar.getValue();
      String temp = ""+ new Character((char) byte1);
      constValue=new VSString(temp);
      out.setValue(constValue.getValue());
      out.setChanged(true);
      element.notifyPin(0);
  }
  
  public void process()
  {

  }
  
  public void loadFromStream(java.io.FileInputStream fis)
  {
      uniChar.loadFromStream(fis);
  }

  public void saveToStream(java.io.FileOutputStream fos)
  {
      uniChar.saveToStream(fos);
  }
}

