import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;
import java.lang.*;

class myFrame extends Frame implements ActionListener{
  String msg="";
  int n=4;
  int count=0;
  int zeroIndex;
  boolean timerFlag = false;
  long startTime;
  java.util.List<Button> btns = new ArrayList<Button>();
  java.util.List<Boolean> btnsStates = new ArrayList<Boolean>();
  java.util.List<Integer> btnsLabels = new ArrayList<Integer>();

  myFrame(String name){
    super(name);
    setLayout(new GridLayout(n, n));
    setFont(new Font("Dialog", Font.BOLD, 16));

    // shuffle array and draw buttons
    
    btnsReset();
    for (int i=1; i<n*n; i++){
      Button b = new Button("" + btnsLabels.get(i-1));
      btns.add(b);
      add(b);
      b.addActionListener(this);
    }
    Button b = new Button("" + 0);
    btns.add(b);
    add(b);
    b.addActionListener(this);
    b.setVisible(false);
    zeroIndex = findZero();

    // initialisation of buttons states
    for (int i=0; i<(n*n); i++){
      if (Integer.parseInt(btns.get(i).getLabel()) == 0){
        btnsStates.add(true);
      } else if (Integer.parseInt(btns.get(i).getLabel()) == i+1){
        btnsStates.add(true);
      } else {
        btnsStates.add(false);
      } 
    }

    // Menu bar
    MenuBar mbar = new MenuBar();
    Menu about;
    MenuItem version, author;
    // Menu file, settings, about, colors;
    // MenuItem newF, openF, saveF, saveAs, closeF, fColor, bColor, fSize, version, author;
    // colors = new Menu("Colors");
    
    // Componize menu
    setMenuBar(mbar);
    // file      = new Menu("File");
    // settings  = new Menu("Settings");
    about     = new Menu("About");
    // file.add(newF   = new MenuItem("New"));
    // file.add(openF  = new MenuItem("Open"));
    // file.add(saveF  = new MenuItem("Save"));
    // file.add(saveAs = new MenuItem("Save as"));
    // file.add(closeF = new MenuItem("Close"));
    // settings.add(fSize  = new MenuItem("Font size"));
    // colors.add(fColor = new MenuItem("Font color"));
    // colors.add(bColor = new MenuItem("Background color"));
    // settings.add(colors);
    about.add(author    = new MenuItem("Author"));
    about.add(version   = new MenuItem("Version"));
    // mbar.add(file);
    // mbar.add(settings);
    mbar.add(about);
    
    // Handlers
    myAdapter a = new myAdapter(this);
    menuHandler mHandler = new menuHandler(this);
    addWindowListener(a);
    // newF.addActionListener(mHandler);
    // openF.addActionListener(mHandler);
    // saveF.addActionListener(mHandler);
    // saveAs.addActionListener(mHandler);
    // closeF.addActionListener(mHandler);
    // fSize.addActionListener(mHandler);
    // fColor.addActionListener(mHandler);
    // bColor.addActionListener(mHandler);
    author.addActionListener(mHandler);
    version.addActionListener(mHandler);
  }

  // true if winnable
  boolean checkEngine(){
    int sum = 0;
    for (int j=0; j<(n*n-2); j++){
      for (int i=j+1; i<(n*n-1); i++){
        if (btnsLabels.get(j) > btnsLabels.get(i)){
          sum +=1;
        }
      }
    }
    sum += n;
    if (sum % 2 == 0){
      return true;
    } else {
      return false;
    }
  }

  // reset btnsLabels
  void btnsReset(){
    for (int i=1; i<n*n; i++){
      btnsLabels.add(i);
    }
    do {
      Collections.shuffle(btnsLabels);
    } while (!checkEngine());
  }

  // check if win
  boolean checkStates(){
    if (btnsStates.contains(false)){
      return false;
    } else {
      return true;
    }
  }

  // get index of zero button
  int findZero(){
    int i = 0;
    while(i<n*n){
      
      if (Integer.parseInt(btns.get(i).getLabel()) == 0){
        break;
      } else {
        i++;
      }

    }
    return i;
  }

  // check if zero button adjoin to active button
  boolean checkZero(Button currentButton){

    int curIndex = btns.indexOf(currentButton);

    if ((curIndex - n) == zeroIndex){
      return true; 
    } else if ((curIndex + n) == zeroIndex){
      return true;
    } else if (((curIndex + 1) % n != 0) && curIndex + 1 == zeroIndex){
      return true;
    } else if (((curIndex) % n != 0) && curIndex - 1 == zeroIndex){
      return true;
    } else {
      return false;
    }
  }

  // switch zero && active button
  void switchButtons(Button currentButton){

    Button emptyButton = btns.get(zeroIndex);
    emptyButton.setLabel(currentButton.getLabel());
    currentButton.setLabel("" + 0);
    emptyButton.setVisible(true);
    currentButton.setVisible(false);

    if (Integer.parseInt(btns.get(zeroIndex).getLabel()) == zeroIndex + 1){
      btnsStates.set(zeroIndex, true);
    } else {
      btnsStates.set(zeroIndex, false);
    }
    btnsStates.set(btns.indexOf(currentButton), true);
    zeroIndex = findZero();
  }

  // when button pressed
  public void actionPerformed(ActionEvent ae){
    Button currentButton = (Button)(ae.getSource());
    
    if (checkZero(currentButton)){
      switchButtons(currentButton);
      count++;
      if (!timerFlag){
        timerFlag = true;
        startTime = System.currentTimeMillis();
      }
    }

    if (checkStates()){
      timerFlag = false;
      new myDialog(this, "Win");
    }
    repaint();
  }

  public void paint(Graphics g){
    g.drawString(msg, 450, 550);
  }
}

class menuHandler implements ActionListener {
  myFrame f;

  menuHandler(myFrame f){
    this.f = f;
  }

  public void actionPerformed(ActionEvent ae){
    String choise = ae.getActionCommand();

    switch(choise){
      case "Author":
        new myDialog(f, "Author");
        break;
      case "Version":
        new myDialog(f, "Version");
      default:
        f.msg = "Something went wrong";
        break;
    }
    f.repaint();
  }
}

class myAdapter extends WindowAdapter{
  myFrame f;
  myAdapter(myFrame f){
    this.f = f;
  }
  public void windowClosing(WindowEvent we){
    f.setVisible(false);
  }
}