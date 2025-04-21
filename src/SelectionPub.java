import java.util.ArrayList;
import java.util.List;

public class SelectionPub
{

    // manages selectionListeners and dispatches Selection match to them
    private static final List<SelectionListener> listeners = new ArrayList<>();

    // register a new observer
    public void addListener(SelectionListener l)
    {
        listeners.add(l);
    }

    public void removeListener(SelectionListener l)
    {
        listeners.remove(l);
    }

    // send to every registered listener
    public static void publish(Selection event)
    {
        for (SelectionListener l : listeners)
        {
            l.selectionChanged(event);
        }
    }

}
