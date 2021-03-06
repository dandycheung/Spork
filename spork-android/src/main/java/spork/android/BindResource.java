package spork.android;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import spork.android.internal.utils.ResourceId;

/**
 * Bind a resource object.
 * Can be used with classes derived from:
 * <ul>
 * <li>{@link android.app.Fragment}</li>
 * <li>{@link android.app.Activity}</li>
 * <li>{@link android.app.Service}</li>
 * <li>{@link android.app.Application}</li>
 * <li>{@link android.content.ContentProvider}</li>
 * <li>{@link android.view.View}</li>
 * <li>{@link ViewProvider}</li>
 * <li>{@link ContextProvider}</li>
 * </ul>
 *
 * Binds to the following field types:
 * <ul>
 * <li>Drawable</li>
 * <li>String</li>
 * <li>Float and float</li>
 * <li>Integer and int</li>
 * <li>Boolean and boolean</li>
 * </ul>
 *
 * The value specified is the resource id.
 * When not specified, the name of the field will be used to resolve the id's name.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BindResource {
	/**
	 * The value specified is the id. When not specified, the name of the field will be used to resolve the id's name.
	 * For example: "@BindResource String my_string;" would bind to R.string.my_string
	 *
	 * @return resource id as defined in R.java
	 */
	int value() default ResourceId.NONE;
}
