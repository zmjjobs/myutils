import javax.swing.*;
import java.awt.*;

class MyCanvas extends Canvas{

public MyCanvas() {

// TODO Auto-generated constructor stub

setSize(150,150);

}

public void paint(Graphics g) {

g.setColor(Color.yellow);

g.fillOval(12, 15, 100, 100);

g.setColor(Color.black);

g.fillArc(35, 35, 75, 75, 90, 90);

g.setColor(Color.yellow);

g.fillArc(25, 37, 75, 65, 90, 90);

g.setColor(Color.black);

g.fillOval(40, 50, 15, 15);

g.fillOval(90, 50, 15, 15);

}

}

public class F8 extends JFrame {

public F8() {


super("制造bug，我很快乐！");

setSize(310,200);

setVisible(true);

setDefaultCloseOperation(EXIT_ON_CLOSE);

add(new MyCanvas());

validate();

}

    public static void main(String[] args) {
        new F8();
    }
}