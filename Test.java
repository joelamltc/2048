public class Test {
	public static void main(String[] args) {
		ArrayQueue q = new ArrayQueue(5);

		q.enqueue(1);
		System.out.println("added: 1");
		System.out.println(q.toString());

		q.enqueue(2);
		System.out.println("added: 2");
		System.out.println(q.toString());

		q.enqueue(3);
		System.out.println("added: 3");
		System.out.println(q.toString());

		q.enqueue(4);
		System.out.println("added: 4");
		System.out.println(q.toString());

		q.enqueue(5);
		System.out.println("added: 5");
		System.out.println(q.toString());

		System.out.println("removed: " + q.dequeue());
		System.out.println(q.toString());

		q.enqueue(6);
		System.out.println("added: 6");
		System.out.println(q.toString());

		System.out.println("removed: " + q.dequeue());
		System.out.println(q.toString());

		q.enqueue(7);
		System.out.println("added: 7");
		System.out.println(q.toString());

		System.out.println("popped: " + q.pop());
		System.out.println(q.toString());

		System.out.println("removed: " + q.dequeue());
		System.out.println(q.toString());

		q.enqueue(9);
		System.out.println("added: 9");
		System.out.println(q.toString());

		System.out.println("removed: " + q.dequeue());
		System.out.println(q.toString());

		q.enqueue(10);
		System.out.println("added: 10");
		System.out.println(q.toString());

		q.enqueue(11);
		System.out.println("added: 11");
		System.out.println(q.toString());
	}
}