import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Demo4 extends Applet {
  Frame f;

  public void init(){
    f = new myFrame("Frame name");
    f.setSize(Integer.parseInt(getParameter("width")), Integer.parseInt(getParameter("height")));
    f.setVisible(true);
  }
  public void start(){
    f.setVisible(true);
  }
  public void stop(){
    f.setVisible(false);
  }

  public void paint(Graphics g){
    g.drawString("This is applet", 50, 50);
  }
}