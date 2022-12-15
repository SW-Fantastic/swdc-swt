package org.swdc.swt.views;

import org.eclipse.swt.widgets.Shell;
import org.swdc.swt.ControllerFactory;
import org.swdc.swt.SWTViewLoader;
import org.swdc.swt.widgets.Stage;

/**
 * 这里只提供stage。
 *
 * 其他的组件都应当在groovy的view中处理完毕，包括
 * 需要loader加载的，被依赖的view。
 */
public abstract class View {

    private SWTViewLoader loader = null;

    private ControllerFactory controllerFactory = null;

    private Stage stage = null;

    public void loadView(){
        SWTView view = this.getClass().getAnnotation(SWTView.class);
        if (view == null) {
            throw new RuntimeException("view must has SWTView annotation。");
        }

        String pathOnResource = view.value();
        loader = new SWTViewLoader(pathOnResource,null,this.getClass().getModule());
        if (this.controllerFactory != null) {
            loader.setFactory(controllerFactory);
        }

        this.stage = (Stage) loader.loadView();
        Shell shell = this.stage.getShell();
        shell.setMinimumSize(view.minWidth(),view.minHeight());
    }

    public void factory(ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    public Stage getStage() {
        return stage;
    }
}
