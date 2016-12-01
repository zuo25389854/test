package test;


public class test {
	static Integer c=0;
	public static void main(String[] args) throws InterruptedException {
		Thread a = new Thread() {
			@Override
			public void run() {
				synchronized (c) {
					for (int i = 0; i < 10000; i++) {
						c++;
					}
				}
			}
		};
		Thread b = new Thread() {
			@Override
			public void run() {
				synchronized (c) {
					for (int i = 0; i < 10000; i++) {
						c--;
					}
				}
			}
		};
		a.start();
		b.start();
		a.join();
		b.join();
		System.out.println(c);
	}

}
