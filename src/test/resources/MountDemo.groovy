

import org.eclipse.swt.SWT
import org.swdc.swt.widgets.SWTButton
import org.swdc.swt.widgets.base.SWTView
import org.swdc.swt.widgets.SWTWidget

class MountDemo extends SWTView {

    @Override
    SWTWidget viewPage() {
        SWTButton.button(SWT.FLAT).define {
            text "Mounted From Resource"
        }
    }

    void test() {
        System.err.println("call test");
    }

}
