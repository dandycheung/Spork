package spork.benchmark.inject.method;

import javax.inject.Inject;

import spork.SporkInstance;
import spork.benchmark.Benchmark;
import spork.inject.ObjectGraph;
import spork.inject.ObjectGraphs;
import spork.inject.Provides;
import spork.inject.internal.InjectMethodBinder;

public final class InjectFiveMethodWarm extends Benchmark {
	private final TestObject[] testObjects;

	public InjectFiveMethodWarm(int iterationCount) {
		SporkInstance spork = new SporkInstance();
		spork.register(new InjectMethodBinder());

		ObjectGraph graph = ObjectGraphs.builder()
				.module(new Module())
				.build();

		// Warm up the cache
		TestObject warmUpObject = new TestObject(spork, graph);
		warmUpObject.inject();

		testObjects = new TestObject[iterationCount];
		for (int i = 0; i < testObjects.length; ++i) {
			testObjects[i] = new TestObject(spork, graph);
		}
	}

	public static final class Module {

		@Provides
		public int provideInt() {
			return 1;
		}

		@Provides
		public long provideLong() {
			return 1L;
		}

		@Provides
		public boolean provideBoolean() {
			return true;
		}

		@Provides
		public float provideFloat() {
			return 1.0f;
		}

		@Provides
		public String provideString() {
			return "test";
		}
	}

	public static final class TestObject {
		private final SporkInstance spork;
		private final ObjectGraph objectGraph;

		public TestObject(SporkInstance spork, ObjectGraph objectGraph) {
			this.spork = spork;
			this.objectGraph = objectGraph;
		}

		@Inject
		private void onInjectInt(int value) {
		}

		@Inject
		private void onInjectLong(long value) {
		}

		@Inject
		private void onInjectBoolean(boolean value) {
		}

		@Inject
		private void onInjectFloat(float value) {
		}

		@Inject
		private void onInjectString(String text) {
		}

		public void inject() {
			objectGraph.inject(this, spork);
		}
	}

	@Override
	protected long doWork() {
		for (TestObject testObject : testObjects) {
			testObject.inject();
		}

		return testObjects.length;
	}
}
