package org.swdc.swt.views;

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

    public abstract void config(Stage stage);

    public void loadView(){
        SWTView view = this.getClass().getAnnotation(SWTView.class);
        if (view == null) {
            throw new RuntimeException("view must has SWTView annotation。");
        }

        stage = new Stage(view.style());

        this.config(stage);

        String pathOnResource = view.value();
        loader = new SWTViewLoader(pathOnResource,stage,this.getClass().getModule());
        if (this.controllerFactory != null) {
            loader.setFactory(controllerFactory);
        }

        this.stage = (Stage) loader.loadView();
    }

    public void factory(ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    public Stage getStage() {
        return stage;
    }
}
