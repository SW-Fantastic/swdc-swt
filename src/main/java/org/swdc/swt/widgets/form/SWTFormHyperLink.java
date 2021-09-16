package org.swdc.swt.widgets.form;

import groovy.lang.Closure;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.swdc.swt.beans.ObservableValue;
import org.swdc.swt.widgets.SWTButton;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.SWTWidgets;
import org.swdc.swt.widgets.Stage;

import java.lang.reflect.Method;

public class SWTFormHyperLink extends SWTWidget<Hyperlink> {

    private int flag;
    private ObservableValue<String> text = new ObservableValue<>();
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
        this.text.addListener(((oldVal, newVal) -> {
            if (this.hyperlink != null && !text.isEmpty()) {
                this.hyperlink.setText(text.get());
            }
        }));
    }

    public SWTFormHyperLink text(String text) {
        this.text.set(text);
        return this;
    }

    public SWTFormHyperLink action(Closure closure) {
        this.linkActivated = new HyperlinkAdapter() {
            @Override
            public void linkActivated(HyperlinkEvent e) {
                closure.call(e);
            }
        };
        return this;
    }

    public SWTFormHyperLink action(String name) {
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
    public void ready(Stage stage) {
        Object controller = stage.getController();
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
    }

    @Override
    public Hyperlink getWidget(Composite parent) {
        if (hyperlink == null && parent != null) {
            FormToolkit toolkit = SWTWidgets.factory();
            hyperlink = toolkit.createHyperlink(parent,"",flag);
            toolkit.paintBordersFor(parent);
            if (!text.isEmpty()) {
                hyperlink.setText(text.get());
            }
            hyperlink.addHyperlinkListener(dispatcher);
        }
        return hyperlink;
    }

    public static SWTFormHyperLink hyperLink(int flag){
        return new SWTFormHyperLink(flag);
    }

}
