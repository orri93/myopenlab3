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


public class Speicher extends JVSMain
{
  private Image image;
  private VSBoolean in1;
  private boolean oldIn1Value=false;
  private VSDouble in2;
  private VSDouble out= new VSDouble();
  private double value=0.0;

  public void paint(java.awt.Graphics g)
  {
    drawImageCentred(g,image);
  }

  public void onDispose()
  {
    if (image!=null)
    {
      image.flush();
      image=null;
    }
  }
  
  public void init()
  {
    initPins(0,1,0,2);
    setSize(40,25);
    element.jSetInnerBorderVisibility(false);
    element.jSetTopPinsVisible(false);
    element.jSetBottomPinsVisible(false);

    image=element.jLoadImage(element.jGetSourcePath()+"icon.gif");
    
    setPin(0,ExternalIF.C_DOUBLE,element.PIN_OUTPUT);
    setPin(1,ExternalIF.C_BOOLEAN,element.PIN_INPUT);
    setPin(2,ExternalIF.C_DOUBLE,element.PIN_INPUT);
    element.jSetPinDescription(0,"Out");
    element.jSetPinDescription(1,"In1");
    element.jSetPinDescription(2,"In2");

    setName("Cell");
  }
  

  public void initInputPins()
  {
    in1=(VSBoolean)element.getPinInputReference(1);
    in2=(VSDouble)element.getPinInputReference(2);
  }

  public void initOutputPins()
  {
    element.setPinOutputReference(0,out);
  }



  public void process()
  {
    if (in1!=null && in2!=null)
    {
      if (in1.getValue()!=oldIn1Value)
      {
        if (in1.getValue()==true)  value=in2.getValue();
        oldIn1Value=in1.getValue();
      }
      out.setValue(value);
      element.notifyPin(0);
    }
  }


}

