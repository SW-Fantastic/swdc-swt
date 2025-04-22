package org.swdc.swt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.swdc.config.AbstractConfig;
import org.swdc.dependency.AnnotationLoader;
import org.swdc.dependency.DependencyContext;
import org.swdc.dependency.EnvironmentLoader;
import org.swdc.dependency.LoggerProvider;
import org.swdc.dependency.application.AbstractApplication;
import org.swdc.dependency.application.SWApplication;
import org.swdc.ours.common.annotations.AnnotationDescription;
import org.swdc.ours.common.annotations.AnnotationDescriptions;
import org.swdc.ours.common.annotations.Annotations;
import org.swdc.swt.views.Splash;
import org.swdc.swt.views.ViewManager;
import org.swdc.swt.widgets.SWTWidgets;

import javax.swing.*;
import java.io.File;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SWTApplication extends AbstractApplication {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private SWTResource resource;

    private DependencyContext context;

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(4,16,1, TimeUnit.HOURS,new LinkedBlockingQueue<>());

    private void initConfig(EnvironmentLoader loader) {
        try {
            InputStream bannerInput = this.getClass().getModule().getResourceAsStream("banner/banner.txt");
            if (bannerInput == null) {
                bannerInput = SWTApplication.class.getModule().getResourceAsStream("banner/banner.txt");
            }
            String banner = ApplicationIOUtil.readStreamAsString(bannerInput);
            System.out.println(banner);
            bannerInput.close();
            logger.info(" Application initializing..");

            loader.withScope(new ViewManager());

            AnnotationDescriptions annotations = Annotations.getAnnotations(this.getClass());
            AnnotationDescription appDesc = Annotations.findAnnotationIn(annotations,EclipseApplication.class);
            logger.info(" using assets: " + appDesc.getProperty(String.class,"assetsFolder"));
            Class[] configs = appDesc.getProperty(Class[].class,"configs");
            Class splash = appDesc.getProperty(Class.class,"splash");


            File file = null;
            String osName = System.getProperty("os.name").trim().toLowerCase();
            logger.info(" starting at : " + osName);
            if (osName.contains("mac")) {
                String url = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
                String base = URLDecoder.decode(url, StandardCharsets.UTF_8);
                if (base.indexOf(".app") > 0) {
                    // 位于MacOS的Bundle（.app软件包）内部，特殊处理以获取正确的路径。
                    String location = base.substring(0,base.indexOf(".app")) + ".app/Contents/";
                    Path target = new File(location).toPath();
                    target = target.resolve(appDesc.getProperty(String.class,"assetsFolder"));
                    file = target.toFile();
                }
            } else {
                file = new File(appDesc.getProperty(String.class,"assetsFolder"));
            }

            logger.info(" dependency environment loading...");


            resource = new SWTResource();
            resource.setConfigures(configs);
            resource.setSplashClass(splash);
            resource.setAssetsFolder(file);
            resource.setExecutor(executor);
            resource.setViewClass(appDesc.getProperty(Class.class,"view"));

            logger.info(" swt initializing...");

            this.doInit(loader);

        } catch (Exception e) {
            logger.error("failed to start  application: ",e);
            System.exit(0);
        }
    }

    @Override
    public void onConfig(EnvironmentLoader loader) {
    }

    private void doInit(EnvironmentLoader loader) {

        try {

            Class< ? extends Splash> splashClass = resource.getSplashClass();

            Splash splash = splashClass
                    .getConstructor(SWTResource.class)
                    .newInstance(this.resource);

            JWindow sSplashWindow = splash.getSplash();
            sSplashWindow.setVisible(true);

            this.onConfig(loader);

            Class[] configures = resource.getConfigures();
            for (Class conf: configures) {
                try {
                    AbstractConfig confInstance = (AbstractConfig) conf.getConstructor().newInstance();
                    loader.withInstance(conf,confInstance);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            logger.info(" config loaded.");

            loader.withInstance(Executor.class,this.executor);
            loader.withInstance(ThreadPoolExecutor.class,this.executor);
            loader.withInstance(SWTResource.class, resource);
            loader.withInstance(SWTApplication.class,this);
            loader.withProvider(LoggerProvider.class);
            loader.withScope(new ViewManager());

            context = loader.load();

            sSplashWindow.setVisible(false);

            SWTWidgets.getDisplay().syncExec(() -> {
                this.onStarted(context);
                logger.info("  stopping...");
                this.executor.shutdown();
                this.onShutdown(context);
                logger.info(" application has shutdown.");
                System.exit(0);
            });

        } catch (Exception e) {
            logger.info(" application has shutdown.",e);
            System.exit(0);
        }
    }

    @Override
    public void onStarted(DependencyContext context) {


    }

    @Override
    public void onShutdown(DependencyContext context) {

    }

    public void launchApplication(String[] argv) {
        this.initConfig(new AnnotationLoader());
    }

}
