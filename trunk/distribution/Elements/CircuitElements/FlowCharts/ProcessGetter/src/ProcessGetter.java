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
import java.awt.*;
import java.awt.event.*;
import tools.*;
import javax.swing.*;
import java.util.*;
import tools.*;
import java.text.*;
import java.awt.geom.Rectangle2D;


public class ProcessGetter extends MainFlow
{
    private Image image;
    private VSBasisIF basis;
    private VSFlowInfo in;
    private VSFlowInfo out = new VSFlowInfo();
    private VSObject outValue ;

  
   public void paint(java.awt.Graphics g)
   {
    if (element!=null)
    {
       g2=(Graphics2D)g;
       Rectangle bounds=element.jGetBounds();
       g2.setFont(font);

      int mitteX=bounds.x+(bounds.width)/2;
      int mitteY=bounds.y+(bounds.height)/2;

       String caption=""+variable.getValue()+" ->";
       FontMetrics fm = g2.getFontMetrics();
       Rectangle2D   r = fm.getStringBounds(caption,g2);


       g2.setColor(new Color(204,204,255));
       Polygon p = new Polygon();
       p.addPoint(bounds.x+10,bounds.y);
       p.addPoint(bounds.x+bounds.width-1,bounds.y);
       p.addPoint(bounds.x+bounds.width-11,bounds.y+bounds.height);
       p.addPoint(bounds.x,bounds.y+bounds.height);
       g2.fillPolygon(p);
       g2.setColor(Color.black);
       g2.drawPolygon(p);

       
       g.drawString(caption,mitteX-(int)(r.getWidth()/2),(int)(mitteY+fm.getHeight()/2)-5);
       //g.drawString(caption,bounds.x+5,bounds.y+((bounds.height) /2)+3);
    }
    super.paint(g);
   }


  public void init()
  {
    standardWidth=130;
    width=standardWidth;
    height=40;

    initPins(1,1,1,0);
    setSize(width,height);
    initPinVisibility(true,true,true,true);
    
    element.jSetInnerBorderVisibility(false);

    image=element.jLoadImage(element.jGetSourcePath()+"icon.gif");
    
    setPin(0,element.C_FLOWINFO,element.PIN_INPUT);
    setPin(1,element.C_VARIANT,element.PIN_OUTPUT);
    setPin(2,element.C_FLOWINFO,element.PIN_OUTPUT);
    
    element.jSetResizable(false);
    element.jSetCaptionVisible(false);
    element.jSetCaption("ProcessGetter");

    setName("#FLOWCHART_ProcessGetter#");
   }


  public void initInputPins()
  {
    in=(VSFlowInfo)element.getPinInputReference(0);
    if (in==null) in = new VSFlowInfo();
  }

  public void initOutputPins()
  {
    outValue=element.jCreatePinDataType(1);
    element.setPinOutputReference(1,outValue);
    element.setPinOutputReference(2,out);
    basis=element.jGetBasis();
  }
  


  public void xOnInit()
  {
    super.xOnInit();
  }
  
  public void checkPinDataType()
  {
    if (element==null) return;

    basis=element.jGetBasis();
    if (basis!=null && variable.getValue().trim().length()>0)
    {
      int varDT=basis.vsGetVariableDT(variable.getValue());
      if (varDT>-1)
      {

        int dt=element.C_VARIANT;

        if (varDT==0)
        {
          dt=element.C_DOUBLE;
        }
        if (varDT==1)
        {
          dt=element.C_STRING;
        }
        if (varDT==2)
        {
          dt=element.C_BOOLEAN;
        }

        element.jSetPinDataType(1,dt);
      }else
      element.jSetPinDataType(1,element.C_VARIANT);

     element.jRepaint();
     }
  }



  public void start()
  {
    //basis.vsNotifyMeWhenVariableChanged(element,variable.getValue());
  }
  

  public void process()
  {
      if (basis!=null && outValue!=null)
      {

        if (basis.vsCopyVariableToVSObject(variable.getValue(), outValue)==true)
        {
          element.notifyPin(1);
        } else
        {
          element.jException("Types unkompatible or variable unknown");
        }
      }
      element.notifyPin(2);
  }

}

