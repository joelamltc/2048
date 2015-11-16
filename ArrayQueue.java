/**
 * Title: DSA Assignment 15-16
 * Description: ArrayQueue class of 2048 game
 * Student name: Lin Ziqiao 
 * Student ID: 6826257
 * Date: 17 Nov 2015 
 */

public class ArrayQueue {

	private int maxSize;
	private Object[] undo_list;
	private int front;
	private int rear;
	private int count;

	/**
	 * constructor method for ArrayQueeue
	 * 
	 * @param int size
	 */
    public ArrayQueue(int size) {
		maxSize = size;
    	undo_list = new Object[maxSize];
    	count = 0; 
    	front = 0; 
    	rear = -1;
    }

    /**
     * constructor method for ArrayQueeue
     * initial the size
     */
	public ArrayQueue() {
		this(20);
	}

	/**
	 * check the array is empty or not
	 * 
	 * @return boolean
	 */
	public boolean empty() {
		return count <= 0;
	}

	/**
	 * check the array is full or not
	 * 
	 * @return boolean
	 */
	public boolean full() {
		return count == maxSize;
	}

	/**
	 * get the array length
	 * 
	 * @return int count
	 */
	public int length() {
		return count;
	}

	/**
	 * add object to the rear of the queue
	 * 
	 * @param object item
	 * @throws QueueFullException
	 */
	public void enqueue(Object item) throws QueueFullException {
		if (count < maxSize) {
			count++;
			if (rear < maxSize - 1)
				rear++;
			else
				rear = 0;
			undo_list[rear] = item;
		} else
			throw new QueueFullException();
	}

	/**
	 * remove object from front of the queue
	 * 
	 * @return object temp
	 * @throws EmptyQueueException
	 */
	public Object dequeue() throws EmptyQueueException {
		if (count <= 0)
			throw new EmptyQueueException();
		count--;
		Object temp = undo_list[front];
		if (front < maxSize - 1)
			front++;
		else
			front = 0;
		return temp;
	}

	/**
	 * remove and return the last object from the queue
	 * @return object temp
	 * @throws EmptyQueueException
	 */
	public Object pop() throws EmptyQueueException {
		if (count <= 0)
			throw new EmptyQueueException();
		count--;
		Object temp = undo_list[rear];
		if (rear < front)
			if (rear != 0)
				rear--;
			else 
				rear = maxSize - 1;
		else 
			rear--;
		return temp;
	}

	/**
	 * override toString method
	 * 
	 * @return String s
	 */
	public String toString() {
		String s = "front [ ";

		int index = front;
		for (int i = 0; i < count; i++) {
			s = s + undo_list[index] + " ";
			if (index < maxSize - 1)
				index++;
			else
				index = 0;
		}

		s = s + "] rear";
		return s;
	}
}

class EmptyQueueException extends RuntimeException {
	/**
	 * customized EmptyQueueException
	 */
	public EmptyQueueException () {
		super("Queue is empty");
	}
}

class QueueFullException extends RuntimeException {
	/**
	 * customized QueueFullException
	 */
	public QueueFullException () {
		super("Queue is full");
	}
}