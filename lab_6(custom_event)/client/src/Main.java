import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main extends JFrame {

    public Main() throws Exception{
        super("File sender");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout (new BorderLayout()) ;
        this.getContentPane().setBackground(Color.white);
        setBounds(100, 100, 630, 630);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenu buttonSend = new JMenu("Send");

        menuFile.add(buttonSend);
        menuBar.add(menuFile);
        this.setJMenuBar(menuBar);

        EntertainmentThread entertainmentThread = new SmileEntertainment();
        this.add(((SmileEntertainment)entertainmentThread).getPanel());
        entertainmentThread.addListener(e -> {
            if (e.isShowEntertainment()) {
                e.getSource().startEntertainment();
            } else {
                e.getSource().stopEntertainment();
            }
        });

        buttonSend.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
                fileChooser.showDialog(Main.this, "Choose file");
                File file = fileChooser.getSelectedFile();
                if(file != null) {
                    try {
                        Socket socket = new Socket("localhost", 9999);
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                        Executor executor = Executors.newFixedThreadPool(2);
                        executor.execute(() -> {
                            try {
                                entertainmentThread.handleEvent(true);
                                oos.writeObject(file.getName());
                                FileInputStream fis = new FileInputStream(file);
                                byte[] buffer = new byte[1000];
                                int bytesRead;
                                while ((bytesRead = fis.read(buffer)) > 0) {
                                    oos.writeObject(Arrays.copyOf(buffer, bytesRead));
                                }
                            } catch (IOException e1) {
                                JOptionPane.showMessageDialog(Main.this, "Error " + e1.getMessage());
                            }
                        });

                        executor.execute(() -> {
                            try {
                                Pair<Integer, String> response = (Pair<Integer, String>) ois.readObject();
                                if(response.getKey() == 0){
                                    JOptionPane.showMessageDialog(Main.this, "Error " + response.getValue());
                                }
                            } catch (IOException | ClassNotFoundException e1) {
                                JOptionPane.showMessageDialog(Main.this, "Error " + e1.getMessage());
                            } finally {
                                entertainmentThread.handleEvent(false);
                                try {
                                    ois.close();
                                    oos.close();
                                    socket.close();
                                } catch (IOException e1) {}
                            }
                        });
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(Main.this, "Error " + e1.getMessage());
                    }
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) throws Exception{
        new Main();
    }
}
