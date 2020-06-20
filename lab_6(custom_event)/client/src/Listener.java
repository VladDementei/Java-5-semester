import java.util.EventListener;

public interface Listener extends EventListener {
    void handleEvent(EntertainmentEvent e);
}
