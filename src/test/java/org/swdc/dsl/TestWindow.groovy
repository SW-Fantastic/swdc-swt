import org.eclipse.jface.dialogs.MessageDialog
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.CoolBar
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Widget
import org.eclipse.ui.forms.widgets.Section
import org.swdc.dsl.TestCell
import org.swdc.dsl.TestController
import org.swdc.swt.SWTViewLoader
import org.swdc.swt.ViewRequire
import org.swdc.swt.layouts.SWTBorderLayout
import org.swdc.swt.layouts.SWTFillLayout
import org.swdc.swt.layouts.SWTFormData
import org.swdc.swt.layouts.SWTFormLayout
import org.swdc.swt.layouts.SWTGridLayout
import org.swdc.swt.layouts.SWTRowLayout
import org.swdc.swt.layouts.SWTStackLayout
import org.swdc.swt.widgets.SWTButton
import org.swdc.swt.widgets.SWTCComboBox
import org.swdc.swt.widgets.SWTCLabel
import org.swdc.swt.widgets.SWTCanvas
import org.swdc.swt.widgets.SWTComboBox
import org.swdc.swt.widgets.SWTCoolBar
import org.swdc.swt.widgets.SWTCoolItem
import org.swdc.swt.widgets.SWTDateTime
import org.swdc.swt.widgets.SWTLabel
import org.swdc.swt.widgets.SWTLink
import org.swdc.swt.widgets.SWTList
import org.swdc.swt.widgets.SWTProgressBar
import org.swdc.swt.widgets.SWTStyledText
import org.swdc.swt.widgets.SWTToolBar
import org.swdc.swt.widgets.SWTToolItem
import org.swdc.swt.widgets.SWTTree
import org.swdc.swt.widgets.SWTWidget
import org.swdc.swt.widgets.SWTWidgets
import org.swdc.swt.widgets.base.SWTView
import org.swdc.swt.widgets.form.SWTForm
import org.swdc.swt.widgets.form.SWTFormExpandPane
import org.swdc.swt.widgets.form.SWTFormHyperLink
import org.swdc.swt.widgets.form.SWTFormText
import org.swdc.swt.widgets.pane.SWTCBanner
import org.swdc.swt.widgets.pane.SWTCTab
import org.swdc.swt.widgets.pane.SWTCTabPane
import org.swdc.swt.widgets.pane.SWTExpandBar
import org.swdc.swt.widgets.pane.SWTExpandItem
import org.swdc.swt.widgets.pane.SWTGroup
import org.swdc.swt.widgets.pane.SWTPane
import org.swdc.swt.widgets.SWTScale
import org.swdc.swt.widgets.SWTSlider
import org.swdc.swt.widgets.SWTSpinner
import org.swdc.swt.widgets.pane.SWTSashForm
import org.swdc.swt.widgets.pane.SWTScrollPane
import org.swdc.swt.widgets.pane.SWTTab
import org.swdc.swt.widgets.pane.SWTTabPane
import org.swdc.swt.widgets.SWTTable
import org.swdc.swt.widgets.SWTTableColumn
import org.swdc.swt.widgets.SWTText
import org.swdc.swt.widgets.Stage
import org.swdc.swt.widgets.form.SWTFormSection
import org.swdc.swt.widgets.pane.SWTViewForm

class TestWindow extends Stage {


    void createContent() {


    }

    static void main(String[] args) {
        Display display = SWTWidgets.getDisplay();

        SWTViewLoader mLoader = new SWTViewLoader("MountDemo")
        Stage mount = mLoader.loadView()

        SWTViewLoader loader = new SWTViewLoader("TestWindow",
                new Stage().define {
                    layout SWTGridLayout.gridLayout().define {
                        margin 6,6
                        spacing 8,8
                        columns 6
                    }
                    size 800,600
                }, SWTView.class.getModule())
        Stage window = (Stage)loader.loadView()

        window.show()
        mount.show()


        /*  TestWindow window = new TestWindow().create();
          window.createContent()
          window.show()*/

        while(!window.isDisposed()){
            if(!display.readAndDispatch()){
                display.sleep();
            }
        }
        display.dispose();

    }


}
