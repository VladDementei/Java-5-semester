import java.util.EventObject;

public class EntertainmentEvent extends EventObject {
    private boolean showEntertainment;

    public EntertainmentEvent(EntertainmentThread source, boolean showEntertainment) {
        super(source);
        this.showEntertainment = showEntertainment;
    }

    @Override
    public EntertainmentThread getSource() {
        return (EntertainmentThread)super.getSource();
    }

    public boolean isShowEntertainment() {
        return showEntertainment;
    }
}
