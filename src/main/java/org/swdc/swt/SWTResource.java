package org.swdc.swt;

import org.swdc.swt.views.Splash;
import org.swdc.swt.views.View;

import java.io.File;
import java.util.concurrent.ThreadPoolExecutor;

public class SWTResource {

    private File assetsFolder;

    private Class<? extends Splash> splashClass;

    private Class[] configures;

    private ThreadPoolExecutor executor;

    private Class<? extends View> viewClass;

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public void setViewClass(Class<? extends View> viewClass) {
        this.viewClass = viewClass;
    }

    public void setExecutor(ThreadPoolExecutor executor) {
        this.executor = executor;
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }

    public void setSplashClass(Class<? extends Splash> splashClass) {
        this.splashClass = splashClass;
    }

    public Class<? extends Splash> getSplashClass() {
        return splashClass;
    }

    public void setAssetsFolder(File assetsFolder) {
        this.assetsFolder = assetsFolder;
    }

    public Class[] getConfigures() {
        return configures;
    }

    public void setConfigures(Class[] configures) {
        this.configures = configures;
    }

    public File getAssetsFolder() {
        return assetsFolder;
    }
}
