package org.swdc.dsl;

import org.swdc.swt.layouts.SWTGridLayout;
import org.swdc.swt.views.SWTView;
import org.swdc.swt.views.View;
import org.swdc.swt.widgets.Stage;

@SWTView("TestWindow")
public class SWTTestView extends View {

    @Override
    public void config(Stage stage) {

        stage.layout(SWTGridLayout
                .gridLayout()
                .margin(6,6)
                .spacing(8,8)
                .columns(6))
                .size(800,600);

    }


}
