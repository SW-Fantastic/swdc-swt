package org.swdc.swt;

import org.swdc.swt.views.Splash;
import org.swdc.swt.views.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EclipseApplication {

    Class[] configs();

    String assetsFolder();

    Class<? extends Splash> splash();

    Class<? extends View> view();

}
