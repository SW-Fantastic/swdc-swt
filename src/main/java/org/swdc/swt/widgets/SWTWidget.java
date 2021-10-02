package org.swdc.swt.widgets;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Widget;
import org.swdc.swt.Modifiable;
import org.swdc.swt.ViewRequire;
import org.swdc.swt.beans.ColorProperty;
import org.swdc.swt.beans.SizeProperty;
import org.swdc.swt.beans.TextProperty;
import org.swdc.swt.layouts.LayoutData;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class SWTWidget<T extends Widget> implements Modifiable<SWTWidget<T>> {

    /**
     * 下一个，本类的链表结构
     */
    private SWTWidget next;

    /**
     * 上一个，本类的链表结构
     */
    private SWTWidget prev;

    /**
     * Id，用于查找组件和Controller的注入
     */
    private String id;

    /**
     * 窗口
     */
    private Stage stage;

    /**
     * 本组件的layoutData。
     */
    private LayoutData layoutData;

    /**
     *
     * 创建后的组件，
     * 本类的实例的create方法被调用后，
     * 将会创建SWT的widget。
     */
    private T widget;

    private SizeProperty sizeProperty = new SizeProperty();

    private ColorProperty colorProperty = new ColorProperty();

    private SWTContainer parent;

    public SWTWidget rightShift(SWTWidget item) {
        if (this.next == null) {
            this.next = item;
            item.prev = this;
        } else {
            this.next.rightShift(item);
        }
        return this;
    }

    public SWTWidget leftShift(SWTWidget item) {
        if (this.prev == null) {
            this.prev = item;
            item.next = this;
        } else {
            this.prev.leftShift(item);
        }
        return this;
    }

    public <R extends SWTWidget> R color(String color) {
        this.colorProperty.setForeground(color);
        return (R) this;
    }

    public <R extends SWTWidget> R background(String color) {
        this.colorProperty.setBackground(color);
        return (R)this;
    }

    public String color() {
        return colorProperty.get();
    }

    public String background() {
        return colorProperty.getBackground();
    }

    public <R extends SWTWidget> R size(int width,int height) {
        this.sizeProperty.set(width,height);
        return (R) this;
    }


    public Point size() {
        return sizeProperty.get();
    }

    public Stage getStage() {
        return stage;
    }

    /**
     * 用来传递stage窗口和在它里面的controller
     * @param stage
     */
    public void initStage(Stage stage) {
        this.stage = stage;
        Object controller = stage.getController();
        if (controller != null && this.getId() != null){
            Field[] fields = controller.getClass().getDeclaredFields();
            for (Field field : fields) {
                org.swdc.swt.Widget widget = field.getAnnotation(org.swdc.swt.Widget.class);
                if (widget == null || !widget.value().equals(this.getId())) {
                    continue;
                }
                try {
                    field.setAccessible(true);
                    field.set(controller,this);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }

    public SWTWidget getFirst(){
        if (this.prev == null) {
            return this;
        }
        return this.prev.getFirst();
    }

    public SWTWidget getNext() {
        return next;
    }

    public SWTWidget getPrev() {
        return prev;
    }

    public SWTWidget getLast() {
        if (this.next == null) {
            return this;
        } else {
            return this.next.getLast();
        }
    }

    public <R extends SWTWidget<T>> R layout(LayoutData layoutData) {
        this.layoutData = layoutData;
        return (R) this;
    }

    public LayoutData getLayoutData() {
        return layoutData;
    }

    public T create(Composite parent,SWTContainer parentWidget) {
        this.parent = parentWidget;
        this.widget = getWidget(parent);
        // 通用属性
        this.sizeProperty.manage(widget);
        this.colorProperty.manage(widget);
        return widget;
    }

    /**
     * 创建SWT组件
     * @param parent
     * @return
     */
    protected abstract T getWidget(Composite parent);

    /**
     * 初始化此组件（SWT组件此时应当已经创建娲完毕）
     * @param stage
     */
    public void ready(Stage stage) {

    }

    public SWTWidget<T> id(String id) {
        this.id = id;
        return this;
    }

    public <R extends Widget> R findById(String id) {
        if (id.equals(this.getId())) {
            if (this.widget != null) {
                return (R) widget;
            }
        }

        if (widget != null && this instanceof SWTContainer) {
            R target = doFindWidget(id,(SWTContainer) this);
            if (target != null) {
                return target;
            }
        }

        return this.parent != null ? doFindWidget(id,parent) : null;
    }

    public Point getSize() {
        return sizeProperty.get();
    }

    private <R extends Widget> R  doFindWidget(String id, SWTContainer parent) {
        List<SWTWidget> children = parent.children();
        for (SWTWidget item: children) {

            if (item == null) {
                continue;
            }

            SWTWidget widget = item.getFirst();
            while (widget != null) {
                if (id.equals(widget.getId())) {
                    return (R) widget.getWidget();
                } else if (widget instanceof SWTContainer) {
                    R target = doFindWidget(id,(SWTContainer) item);
                    if (target != null) {
                        return target;
                    }
                }
                widget = widget.getNext();
            }
        }
        return null;
    }

    public SizeProperty sizeProperty() {
        return this.sizeProperty;
    }

    /**
     * 在Resources中定义的Groovy的View
     * 它们被注入到Controller将会表现为SWTWidget的对象，
     * 在这种状态下，它们独有的方法将会被隐藏。
     *
     * 因此如果需要调用此对象的具体的方法，使用本方法进行调用。
     *
     * @param methodName groovy的view的方法名
     * @param param groovy的view的方法参数
     * @param <T> 返回类型
     * @return
     */
    public <T> T call(String methodName, Object ...param) {
        List<Class> paramList = new ArrayList<>();
        for (Object item: param) {
            paramList.add(item.getClass());
        }
        try {
            Method method = this.getClass().getMethod(methodName,paramList.toArray(Class[]::new));
            return (T) method.invoke(this,param);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SWTContainer getParent() {
        return parent;
    }

    public T getWidget() {
        return widget;
    }

    public String getId() {
        return id;
    }
}
