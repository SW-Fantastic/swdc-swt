package org.swdc.swt.widgets.form;

import groovy.lang.Closure;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;
import org.swdc.swt.widgets.base.SWTLabelControlWidget;

import java.lang.reflect.Method;

public class SWTFormHyperLink extends SWTLabelControlWidget<Hyperlink> {

    private int flag;

    private Hyperlink hyperlink;
    private Method actionMethod;
    private String methodName;

    private HyperlinkAdapter linkActivated;

    private HyperlinkAdapter dispatcher = new HyperlinkAdapter(){
        @Override
        public void linkActivated(HyperlinkEvent e) {
            if (SWTFormHyperLink.this.linkActivated != null) {
                linkActivated.linkActivated(e);
            }
        }
    };

    public SWTFormHyperLink (int flag) {
        this.flag = flag;
    }

    public SWTFormHyperLink onAction(Closure closure) {

        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);

        this.linkActivated = new HyperlinkAdapter() {
            @Override
            public void linkActivated(HyperlinkEvent e) {
                closure.call(e);
            }
        };
        return this;
    }

    public SWTFormHyperLink onAction(String name) {
        this.methodName = name;
        this.linkActivated = new HyperlinkAdapter() {
            @Override
            public void linkActivated(HyperlinkEvent e) {
                Stage stage = SWTFormHyperLink.this.getStage();
                Object controller = stage.getController();
                if (controller == null) {
                    return;
                }

                Method finalMethod = actionMethod;
                if (actionMethod == null) {
                    return;
                }
                try {
                    if (finalMethod.getParameterCount() > 0) {
                        finalMethod.invoke(controller,e);
                    } else {
                        finalMethod.invoke(controller);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        return this;
    }

    @Override
    public void initWidget(Hyperlink created) {
        if (hyperlink == null) {
            return;
        }
        super.initWidget(hyperlink);
        Object controller = this.getController();
        if (controller == null || methodName == null) {
            return;
        }

        Class controllerClazz = controller.getClass();
        if (actionMethod == null) {
            try {
                actionMethod = controllerClazz.getMethod(methodName);
            } catch (Exception e) {
                try {
                    actionMethod = controllerClazz.getMethod(methodName, SelectionEvent.class);
                } catch (Exception ex) {
                    throw new RuntimeException("找不到可用的方法：" + methodName);
                }
            }
        }
        SWTWidgets.setupLayoutData(this,hyperlink);
    }


    @Override
    public Hyperlink getWidget(Composite parent) {
        if (hyperlink == null && parent != null) {
            FormToolkit toolkit = SWTWidgets.factory();
            hyperlink = toolkit.createHyperlink(parent,"",flag);
            toolkit.paintBordersFor(parent);
            hyperlink.addHyperlinkListener(dispatcher);
            initWidget(hyperlink);
        }
        return hyperlink;
    }

    public static SWTFormHyperLink hyperLink(int flag){
        return new SWTFormHyperLink(flag);
    }

}
