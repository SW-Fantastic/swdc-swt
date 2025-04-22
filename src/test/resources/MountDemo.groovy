

import org.eclipse.swt.SWT
import org.swdc.dsl.SubController
import org.swdc.swt.SWTViewLoader
import org.swdc.swt.ViewController
import org.swdc.swt.layouts.SWTGridLayout
import org.swdc.swt.layouts.SWTLayout
import org.swdc.swt.widgets.SWTButton
import org.swdc.swt.widgets.base.SWTView
import org.swdc.swt.widgets.SWTWidget

@ViewController(SubController.class)
class MountDemo extends SWTView {

    @Override
    protected SWTWidget viewPage(SWTWidget self) {
        SWTButton.button(SWT.FLAT).define {
            text "Mounted From Resource"
            onAction "resourceClick"
        }
    }

    @Override
    protected SWTLayout layout() {
        return SWTGridLayout
                .gridLayout()
                .margin(6,6)
                .spacing(8,8)
                .columns(6)
    }

    void test() {
        System.err.println("call test");
    }

}
