package Exercise2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

public class Exercise2 {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400, 500);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel c1Label = new JLabel();
        frame.add(c1Label);
        c1Label.setBounds(20, 40, 150, 40);
        c1Label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton c1StartBtn = new JButton("start");
        frame.add(c1StartBtn);
        c1StartBtn.setBounds(20, 100, 150, 40);

        JButton c1ResetBtn = new JButton("reset");
        frame.add(c1ResetBtn);
        c1ResetBtn.setBounds(20, 250, 150, 40);

        Counter counter1 = new Counter();
        counter1.addAction(() -> {
            SwingUtilities.invokeLater(() -> {
                c1Label.setText("Counter: " + counter1);
            });
        });
        c1StartBtn.addActionListener((ActionEvent actionEvent) -> counter1.start());
        c1ResetBtn.addActionListener((ActionEvent actionEvent) -> counter1.reset());





        JLabel c2Label = new JLabel();
        frame.add(c2Label);
        c2Label.setBounds(200, 40, 150, 40);
        c2Label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton c2StartBtn = new JButton("start");
        frame.add(c2StartBtn);
        c2StartBtn.setBounds(200, 100, 150, 40);

        JButton c2ResetBtn = new JButton("reset");
        frame.add(c2ResetBtn);
        c2ResetBtn.setBounds(200, 250, 150, 40);


        Counter counter2 = new Counter();
        counter2.addAction(() -> {
            try {
                SwingUtilities.invokeAndWait(() -> {
                    c2Label.setText("Counter: " + counter2);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        c2StartBtn.addActionListener((ActionEvent actionEvent) -> counter2.start());
        c2ResetBtn.addActionListener((ActionEvent actionEvent) -> counter2.reset());


        frame.setVisible(true);
    }
}  
