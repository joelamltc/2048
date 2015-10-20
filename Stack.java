/*
 * Title: DSA Assignment 15-16
 * Description: 2048 game
 * Student name: Lin Ziqiao 
 * Student ID: 6826257
 * Date: 27 Nov 2015 
 */

public class Stack {
	private int maxSize;
	private Object[] stackArray;
	private int top;

	public Stack(int s) {
      	maxSize = s;
      	stackArray = new Object[maxSize];
      	top = -1;
   	}

	public void push(long j) {
	    stackArray[++top] = j;
	}

	public Object pop() {
	   return stackArray[top--];
	}

	public Object peek() {
	   return stackArray[top];
	}

	public boolean isEmpty() {
	   return (top == -1);
	}

	public boolean isFull() {
	   return (top == maxSize - 1);
	}

	public int search(Object o) {
		if (isEmpty()) return -1;
	}
}