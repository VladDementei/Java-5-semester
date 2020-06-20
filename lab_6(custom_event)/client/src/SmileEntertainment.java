import java.io.IOException;

public class SmileEntertainment extends EntertainmentThread {
    private PaintPanel panel;
    private Boolean isMoving = null;


    public SmileEntertainment() throws IOException {
        this.panel = new PaintPanel();
        panel.setVisible(false);
    }

    @Override
    public synchronized void startEntertainment() {
        if(isMoving == null){
            isMoving = true;
            this.start();
        }else {
            isMoving = true;
            this.notify();
        }
        panel.setVisible(true);
    }

    @Override
    public void stopEntertainment() {
        isMoving = false;
        panel.setVisible(false);
    }

    @Override
    public void run() {
        while (true){
            if(!isMoving){
                synchronized (this){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            panel.repaint();
        }
    }

    public PaintPanel getPanel() {
        return panel;
    }

}
