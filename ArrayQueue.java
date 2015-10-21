/*
 * Title: DSA Assignment 15-16
 * Description: 2048 game
 * Student name: Lin Ziqiao 
 * Student ID: 6826257
 * Date: 27 Nov 2015 
 */

public class ArrayQueue {

	private int maxSize;
	private Object [] undo_list;
	private int front;
	private int rear;
	private int count;

    public ArrayQueue(int size) {
		maxSize = size;
    	undo_list = new Object[maxSize];
    	count = 0; 
    	front = 0; 
    	rear = -1;
    }

	public ArrayQueue() {
		this(20);
	}

	public boolean empty() {
		return count <= 0;
	}

	public boolean full() {
		return count == maxSize;
	}

	public int length() {
		return count;
	}

	public void enqueue(Object item) throws QueueFullException {
		if (count < maxSize) {
			count++;
			if (rear < maxSize-1)
				rear++;
			else
				rear = 0;
			undo_list[rear] = item;
		} else
			throw new QueueFullException();
	}

	public Object dequeue() throws EmptyQueueException {
		if (count <= 0)
			throw new EmptyQueueException();
		count--;
		Object temp = undo_list[front];
		if (front < maxSize-1)
			front++;
		else
			front = 0;
		return temp;
	}

	public Object pop() throws EmptyQueueException {
		if (count <= 0) {
			throw new EmptyQueueException();
		}
		count--;
		Object temp = undo_list[rear];
		if (rear < maxSize - 1) {
			rear--;
		} else {
			rear = 0;
		}
		return temp;
	}

	public String toString() {
		String s = "front [ ";

		int index = front;
		for (int i = 0; i < count; i++) {
			s = s + undo_list[index] + " ";
			if (index < maxSize-1)
				index++;
			else
				index = 0;
		}

		s = s + "] rear";
		return s;

	}

} // class ArrayQueue

class EmptyQueueException extends RuntimeException {
	public EmptyQueueException () {
		super("Queue is empty");
	}
} // class EmptyQueueException

class QueueFullException extends RuntimeException {
	public QueueFullException () {
		super("Queue is full");
	}
} // class QueueFullException