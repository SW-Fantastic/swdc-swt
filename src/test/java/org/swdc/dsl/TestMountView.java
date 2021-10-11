package org.swdc.dsl;

import org.swdc.swt.layouts.SWTGridLayout;
import org.swdc.swt.views.SWTView;
import org.swdc.swt.views.View;
import org.swdc.swt.widgets.Stage;

@SWTView("MountDemo")
public class TestMountView extends View {

    @Override
    public void config(Stage stage) {
        stage.layout(SWTGridLayout
                .gridLayout()
                .margin(6,6)
                .spacing(8,8)
                .columns(6));
    }

}
