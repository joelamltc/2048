public class Test {
	public static void main(String[] args) {
		ArrayQueue q = new ArrayQueue(5);

		// for (int i = 1; i < 8; i++) {
		// 	if (q.length() < 5) {
		// 		q.enqueue(i);
		// 		System.out.println("added: " + i);
		// 		System.out.println("count: " + q.length());
		// 		System.out.println("front: " + q.front());
		// 		System.out.println("rear: " + q.rear());
		// 		System.out.println(q.toString());
		// 		System.out.println();
		// 	} else {
		// 		System.out.println("removed: " + q.dequeue());
		// 		q.enqueue(i);
		// 		System.out.println("added: " + i);
		// 		System.out.println("count: " + q.length());
		// 		System.out.println("front: " + q.front());	
		// 		System.out.println("rear: " + q.rear());
		// 		System.out.println(q.toString());
		// 		System.out.println();
		// 	}
		// }

		// // System.out.println("popped: " + q.pop());
		// // System.out.println(q.toString());

		// // System.out.println("removed: " + q.dequeue());
		// // System.out.println(q.toString());

		// // q.enqueue(9);
		// // System.out.println("added: 9");
		// // System.out.println(q.toString());

		// // System.out.println("removed: " + q.dequeue());
		// // System.out.println(q.toString());

		// // q.enqueue(10);
		// // System.out.println("added: 10");
		// // System.out.println(q.toString());

		// // q.enqueue(11);
		// // System.out.println("added: 11");
		// // System.out.println(q.toString());

		// while (q.length() > 0 ) {
		// 	System.out.println("count: " + q.length());
		// 	System.out.println("front: " + q.front());	
		// 	System.out.println("rear: " + q.rear());
		// 	System.out.println("popped: " + q.pop());
		// 	System.out.println(q.toString());
		// 	System.out.println();
		// }
	}
}