package ca.kanoa.kclient.settings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to set a field in a class as a setting.
 * <br>
 * Settings will be saved when used with a setting saver
 * <br>
 * <p>This only works with fields that are one of the following types:</p>
 * <ul>
 * <li>Integers</li>
 * <li>Booleans</li>
 * <li>Doubles</li>
 * <li>Strings</li>
 * <li>Lists</li>
 * </ul>
 * @author Kanoa
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Setting {

	String key() default "field_name";
	
}
