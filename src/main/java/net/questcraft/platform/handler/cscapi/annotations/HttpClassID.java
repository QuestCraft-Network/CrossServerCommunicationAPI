package net.questcraft.platform.handler.cscapi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies the ClassID for http work on a Serializable Class
 *
 * @author Chestly
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpClassID {
    String value() default "";
}
