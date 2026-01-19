package cz.muni.fi.pa165.socialnetwork.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MethodExecutionTimeTracker {
}
