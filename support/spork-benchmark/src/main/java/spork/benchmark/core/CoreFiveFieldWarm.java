package spork.benchmark.core;

import spork.SporkInstance;
import spork.benchmark.Benchmark;

public final class CoreFiveFieldWarm extends Benchmark {
	private final TestObject[] testObjects;

	public CoreFiveFieldWarm(int iterationCount) {
		SporkInstance spork = new SporkInstance();
		spork.register(new TestBinder());

		// Warm up the cache
		TestObject warmUpObject = new TestObject(spork);
		warmUpObject.bind();

		testObjects = new TestObject[iterationCount];
		for (int i = 0; i < testObjects.length; ++i) {
			testObjects[i] = new TestObject(spork);
		}
	}

	public static final class TestObject {
		private final SporkInstance spork;

		public TestObject(SporkInstance spork) {
			this.spork = spork;
		}

		@TestAnnotation
		Object a;

		@TestAnnotation
		Object b;

		@TestAnnotation
		Object c;

		@TestAnnotation
		Object d;

		@TestAnnotation
		Object e;

		public void bind() {
			spork.bind(this);
		}
	}

	@Override
	protected long doWork() {
		for (TestObject testObject : testObjects) {
			testObject.bind();
		}

		return testObjects.length;
	}
}
