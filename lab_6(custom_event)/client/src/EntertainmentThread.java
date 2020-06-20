import java.io.IOException;

public abstract class EntertainmentThread extends Thread{
    private Listener listener;

    public EntertainmentThread(){
    }

    public void addListener(Listener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        this.listener = null;
    }

    public abstract void startEntertainment();

    public abstract void stopEntertainment();


    public void handleEvent(boolean showEntertainment) {
        EntertainmentEvent event = new EntertainmentEvent(this, showEntertainment);
        listener.handleEvent(event);
    }
}
