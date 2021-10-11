package org.swdc.swt.views;

import jakarta.inject.Scope;
import org.eclipse.swt.SWT;
import org.swdc.dependency.annotations.ScopeImplement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Scope
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ScopeImplement(ViewManager.class)
public @interface SWTView {

    String value();

    int style() default SWT.SHELL_TRIM;

}
