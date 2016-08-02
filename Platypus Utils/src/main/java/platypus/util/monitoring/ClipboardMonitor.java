package platypus.util.monitoring;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A <code>ClipboardMonitor</code> is a <code>ClipboardOwner</code> which
 * detects changes in the system clipboard and sends notifications through an
 * ActionListener interface. String-flavor clipboard contents are sent as
 * commands in their corresponding <code>ActionEvent</code>s.
 * <p>
 * The <code>ClipboardMonitor</code> operates on its own thread, which must be
 * started manually. Additionally, the <code>resumeListening()</code> must be
 * called for the <code>ClipboardMonitor</code> to begin detecting changes,
 * since this is turned off by default.
 * <p>
 * Irregular behavior can occur if multiple <code>ClipboardMonitor</code>
 * threads run concurrently.
 *
 * @author Jingchen Xu
 */
public class ClipboardMonitor extends Thread implements ClipboardOwner {

    private Clipboard sysClip = Toolkit.getDefaultToolkit()
            .getSystemClipboard();

    private boolean end = false;

    private boolean listening = false;

    private String prevVal = "";

    private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();

    // TODO change to singleton

    public ClipboardMonitor() {
        super();
    }

    @Override
    public void run() {
        Transferable trans = sysClip.getContents(this);
        regainOwnership(trans);
        // System.out.println("Listening to board...");
        while (true) {
            if (isOver())
                break;
        }
        // System.out.println("No more Listening...");
    }

    /**
     * Allows the listener to begin detecting clipboard changes. By default,
     * listening is turned off.
     */
    public void resumeListening() {
        listening = true;
    }

    /**
     * Prevents the listener from detecting clipboard changes.
     */
    public void pauseListening() {
        listening = false;
    }

    /**
     * Signals the thread to join.
     */
    public void end() {
        end = true;
    }

    private boolean isOver() {
        return end;
    }

    @Override
    public void lostOwnership(Clipboard c, Transferable t) {
        try {
            sleep(200);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        try {
            Transferable contents = c.getContents(this); // EXCEPTION
            processContents(contents);
            regainOwnership(contents);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processContents(Transferable t) {

        if (!listening)
            return;

        try {
            Object o = t.getTransferData(DataFlavor.stringFlavor);
            if (!o.equals(prevVal)) {
                notifyListeners((String) o);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void regainOwnership(Transferable t) {
        sysClip.setContents(t, this);
        processContents(t);
    }

    /**
     * Adds an <code>ActionListener</code> to the monitor.
     *
     * @param l
     *        the <code>ActionListener</code> to be added
     */
    public void addActionListener(ActionListener l) {
        listeners.add(l);
    }

    /**
     * Removes an <code>ActionListener</code> to the monitor.
     *
     * @param l
     *        the <code>ActionListener</code> to be removed
     */
    public void removeActionListener(ActionListener l) {
        listeners.remove(l);
    }

    private void notifyListeners(String s) {

        synchronized (listeners) {
            for (int i = 0; i < listeners.size(); i++)
                listeners.get(i).actionPerformed(
                        new ActionEvent(this, ActionEvent.ACTION_PERFORMED, s));
            prevVal = s;
        }
    }
}
