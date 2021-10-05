

import org.eclipse.swt.SWT
import org.swdc.dsl.SubController
import org.swdc.swt.ViewController
import org.swdc.swt.widgets.SWTButton
import org.swdc.swt.widgets.base.SWTView
import org.swdc.swt.widgets.SWTWidget

@ViewController(SubController.class)
class MountDemo extends SWTView {

    @Override
    protected SWTWidget viewPage() {
        SWTButton.button(SWT.FLAT).define {
            text "Mounted From Resource"
            onAction "resourceClick"
        }
    }

    void test() {
        System.err.println("call test");
    }

}
