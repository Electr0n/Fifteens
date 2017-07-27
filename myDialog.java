import java.awt.*;
import java.awt.event.*;
import java.applet.*;

class myDialog extends Dialog implements ActionListener{
  String msg;
  myDialog(myFrame parent, String name){
    super(parent, name, true);
    setLayout(new FlowLayout());
    setSize(500, 50);
    switch(name){
      case "Author":
        msg = "skorodumov.eugene@gmail.com";
        break;
      case "Version":
        msg = "Version 1.0a";
        break;
      case "Win":
        msg = "Clicks: " + parent.count + "; Time: " + (System.currentTimeMillis() - parent.startTime)/1000 + " seconds";
        break;
      default:
        msg = "ooops";
    }
    Button b = new Button("Ok");
    b.setSize(new Dimension(30, 30));
    add(new Label(msg));
    add(b);
    b.addActionListener(this);
    setVisible(true);
  }

  public void windowClosing(ActionEvent ae){
    setVisible(false);
  }

  public void actionPerformed(ActionEvent ae){
    dispose();
  }

  public void paint(Graphics g){
  }
}