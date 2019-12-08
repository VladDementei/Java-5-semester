import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main extends JFrame {

    private final CountDownLatch latch = new CountDownLatch(4);

    public Main() throws Exception{
        super("Main Frame");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 600);

        PaintPanel panel = new PaintPanel();
        this.add(panel);

        Executor executor = Executors.newFixedThreadPool(2);

        executor.execute(() -> {
            while (latch.getCount() != 0){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                panel.repaint();
            }
        });

        executor.execute(() -> {
            int numCros = 0;
            while (latch.getCount() != 0){
                if(panel.getNumCros() != numCros){
                    numCros++;
                    latch.countDown();
                    System.out.println(numCros);
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) throws Exception{
        new Main();
    }
}
