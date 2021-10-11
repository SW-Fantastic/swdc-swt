package org.swdc.swt.views;

import org.swdc.swt.SWTResource;

public abstract class AbstractSplash implements Splash {

    private SWTResource resource;

    public AbstractSplash(SWTResource resource) {
        this.resource = resource;
    }

}
