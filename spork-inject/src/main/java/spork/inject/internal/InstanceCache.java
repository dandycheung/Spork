package spork.inject.internal;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * A 2-dimensional cache that maps as follows:
 *
 *   scope identifier -&gt; injection signature -&gt; instance
 *
 */
@SuppressWarnings("PMD.UseConcurrentHashMap")
@ThreadSafe
public final class InstanceCache {
	private final Map<String, Map<InjectSignature, Object>> scopeMap = new HashMap<>();

	public void put(String scope, InjectSignature injectSignature, Object instance) {
		synchronized (scopeMap) {
			getOrCreateInjectSignatureMap(scope).put(injectSignature, instance);
		}
	}

	@Nullable
	public Object get(@Nullable String scopeIdentifier, InjectSignature injectSignature) {
		Map<InjectSignature, Object> map;

		synchronized (scopeMap) {
			map = scopeMap.get(scopeIdentifier);
		}

		if (map == null) {
			return null;
		}

		return map.get(injectSignature);
	}

	private Map<InjectSignature, Object> getOrCreateInjectSignatureMap(@Nullable String scopeIdentifier) {
		synchronized (scopeMap) {
			Map<InjectSignature, Object> map = scopeMap.get(scopeIdentifier);

			if (map == null) {
				map = new HashMap<>(2);
				scopeMap.put(scopeIdentifier, map);
			}

			return map;
		}
	}
}
