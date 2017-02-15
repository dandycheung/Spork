package spork.inject.internal.objectgraph;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Qualifier;
import javax.inject.Scope;

import spork.exceptions.BindException;
import spork.inject.internal.objectgraph.nodes.ModuleNode;
import spork.inject.internal.objectgraph.nodes.ScopedInstanceCache;

public class ObjectGraph {
	private final List<ObjectGraphNode> nodes;

	private ObjectGraph(List<ObjectGraphNode> nodes) {
		this.nodes = nodes;
	}

	public <T> Provider<T> findProvider(Field targetField, Class<T> targetType) {
		Annotation qualifierAnnotation = Annotations.findAnnotationAnnotatedWith(Qualifier.class, targetField);
		Nullability nullability = Nullability.create(targetField);
		InjectSignature injectSignature = new InjectSignature(targetType, nullability, qualifierAnnotation);

		return findProvider(injectSignature);
	}

	public <T> Provider<T> findProvider(InjectSignature injectSignature) {
		for (ObjectGraphNode node : nodes) {
			Provider<T> provider = node.findProvider(this, injectSignature);

			if (provider != null) {
				return provider;
			}
		}

		String message = "no provider found in ObjectGraph for " + injectSignature.toString();

		throw new BindException(Inject.class, message);
	}

	public static class Builder {
		private final ArrayList<ObjectGraphNode> nodes = new ArrayList<>(2);
		private final ScopedInstanceCache scopedInstanceCache = new ScopedInstanceCache();

		public Builder module(Object module) {
			nodes.add(new ModuleNode(module, scopedInstanceCache));
			return this;
		}

		public ObjectGraph build() {
			nodes.trimToSize();
			return new ObjectGraph(nodes);
		}
	}
}
