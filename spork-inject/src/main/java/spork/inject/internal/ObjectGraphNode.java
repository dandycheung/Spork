package spork.inject.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Scope;

import spork.BindException;
import spork.inject.AnnotationSerializerRegistry;
import spork.inject.internal.lang.Annotations;

/**
 * A node represents an injection point (@Provides method) in a module.
 */
public final class ObjectGraphNode {
	private final InjectSignature injectSignature;
	private final Object parent;
	private final Method method;
	@Nullable private final String scopeId;

	ObjectGraphNode(InjectSignature injectSignature, Object parent, Method method) {
		this.injectSignature = injectSignature;
		this.parent = parent;
		this.method = method;
		this.scopeId = findScopeId(method);
	}

	public InjectSignature getInjectSignature() {
		return injectSignature;
	}

	@Nullable
	public String getScopeId() {
		return scopeId;
	}

	public Object resolve(Object... arguments) {
		method.setAccessible(true);
		try {
			return method.invoke(parent, arguments);
		} catch (InvocationTargetException e) {
			throw new BindException(Inject.class, "Failed to invoke method \"" + method.getName() + "\" on " + parent.getClass().getName(), e);
		} catch (IllegalAccessException e) {
			throw new BindException(Inject.class, "Failed to invoke method \"" + method.getName() + "\" on " + parent.getClass().getName() + ": " + e.getMessage(), e);
		} finally {
			try {
				method.setAccessible(false);
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
	}

	@Nullable
	Object[] collectParameters(ObjectGraph objectGraph) {
		try {
			return ObjectGraphs.getInjectableMethodParameters(objectGraph, method);
		} catch (ObjectGraphException e) {
			String message = "failed to call " + method.getDeclaringClass().getName() + "." + method.getName() + "(): " + e.getMessage();
			throw new BindException(Inject.class, method.getDeclaringClass(), injectSignature.getType(), message, e);
		}
	}

	private static String findScopeId(Method method) {
		Annotation annotation = Annotations.findAnnotationAnnotatedWith(Scope.class, method);
		if (annotation == null) {
			return null;
		} else {
			return AnnotationSerializerRegistry.serialize(annotation);
		}
	}
}
