package jogoaviao;
import java.awt.event.*;
import javax.swing.JOptionPane;

   public class MyFinishWindow extends WindowAdapter
   {
     public void windowClosing(WindowEvent e)
     {
       //JOptionPane.showMessageDialog(null, "Programa Finalizado");
       System.exit(0);
     }
   }
