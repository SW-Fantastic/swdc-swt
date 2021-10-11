package org.swdc.dsl;

import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Display;
import org.swdc.dependency.DependencyContext;
import org.swdc.swt.EclipseApplication;
import org.swdc.swt.SWTApplication;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;

@EclipseApplication(configs = {},
        assetsFolder = "asset",
        splash = SWTTestSplash.class,
        view = SWTTestView.class
)
public class TestSWTApplication extends SWTApplication {

    @Override
    public void onStarted(DependencyContext context) {

        SWTTestView testView = context.getByClass(SWTTestView.class);
        Display display = SWTWidgets.getDisplay();
        Stage stage = testView.getStage();
        stage.show();

        while (stage.getShell().isVisible()) {
            if(!display.readAndDispatch()){
                display.sleep();
            }
        }

    }

    public static void main(String[] args) {
        TestSWTApplication application = new TestSWTApplication();
        application.launchApplication(args);
    }

}
