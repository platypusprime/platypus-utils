package platypus.util.monitoring;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

/**
 * An <code>ArrayList</code> which generates an <code>ActionCommand</code> every
 * time the list is modified.
 * 
 * @author Jingchen Xu
 * @since July 27, 2014
 * @version 1.0.0
 *
 * @param <E> The type of elements in this list
 */
public class ListenedList<E> extends ArrayList<E> {

	private static final long serialVersionUID = 1L;

	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	private String command = new String();

	/**
	 * Constructs an empty ListenedList with an initial capacity of ten.
	 */
	public ListenedList() {
		super();
	}

	/**
	 * Constructs a ListenedList containing the elements of the specified
	 * collection, in the order they are returned by the collection's iterator.
	 * 
	 * @param c the collection whose elements are to be placed into this list
	 */
	public ListenedList(Collection<E> c) {
		super(c);
	}

	/**
	 * Sets the action command for this list.
	 * 
	 * @param command
	 *            the action command for this list
	 */
	public void setActionCommand(String command) {
		this.command = command;
	}

	/**
	 * Adds an <code>ActionListener</code> to this list.
	 * 
	 * @param listener
	 *            the <code>ActionListener</code> to add to this list
	 */
	public void addActionListener(ActionListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes an <code>ActionListener</code> from this list.
	 * 
	 * @param listener
	 *            the <code>ActionListener</code> to remove from this list
	 */
	public void removeActionListener(ActionListener listener) {
		listeners.remove(listener);
	}

	private void notifyListeners() {

		synchronized (listeners) {
			for (ActionListener l : listeners)
				l.actionPerformed(new ActionEvent(this,
						ActionEvent.ACTION_PERFORMED, command));
		}
	}

	@Override
	public boolean add(E e) {

		boolean val = super.add(e);
		if (val)
			notifyListeners();
		return val;
	}

	@Override
	public void add(int index, E element) {
		super.add(index, element);
		notifyListeners();
		return;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean val = super.addAll(c);
		if (val)
			notifyListeners();
		return val;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		boolean val = super.addAll(index, c);
		if (val)
			notifyListeners();
		return val;
	}

	@Override
	public void clear() {
		super.clear();
		notifyListeners();
		return;
	}

	@Override
	public E remove(int index) {
		E val = super.remove(index);
		notifyListeners();
		return val;
	}

	@Override
	public boolean remove(Object o) {
		boolean val = super.remove(o);
		if (val)
			notifyListeners();
		return val;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean val = super.removeAll(c);
		if (val)
			notifyListeners();
		return val;
	}

	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		super.removeRange(fromIndex, toIndex);
		notifyListeners();
		return;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean val = super.retainAll(c);
		if (val)
			notifyListeners();
		return val;
	}

	@Override
	public E set(int index, E element) {
		E val = super.set(index, element);
		notifyListeners();
		return val;
	}

}
