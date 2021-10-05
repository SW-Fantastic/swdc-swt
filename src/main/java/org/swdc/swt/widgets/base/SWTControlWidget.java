package org.swdc.swt.widgets.base;

import groovy.lang.Closure;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.swdc.swt.actions.*;
import org.swdc.swt.widgets.SWTWidget;
import org.swdc.swt.widgets.Stage;

public abstract class SWTControlWidget<T extends Control> extends SWTWidget<T>
        implements Controlable,MouseAcceptable,MouseTrackable,
                   MouseMovable,MouseWheelable,KeyListeneable {

    private ControlProperty controlProperty = new ControlProperty();
    private Closure sizeClosure;
    private Closure moveClosure;

    private MouseProperty mouseProperty = new MouseProperty();
    private Closure mouseUpClosure;
    private Closure mouseDownClosure;
    private Closure mouseDBClickClosure;


    private MouseTrackProperty trackProperty = new MouseTrackProperty();
    private Closure mouseEnterClosure;
    private Closure mouseExitClosure;
    private Closure mouseHoverClosure;

    private MouseMoveProperty mouseMoveProperty = new MouseMoveProperty();

    private MouseWheelProperty wheelProperty = new MouseWheelProperty();

    private KeyProperty keyProperty = new KeyProperty();
    private Closure keyPressClosure;
    private Closure keyReleaseClosure;

    @Override
    public void ready() {
        super.ready();
        T widget = this.getWidget();
        if (widget != null) {

            widget.addControlListener(controlProperty.dispatcher());
            controlProperty.manage(this);

            widget.addMouseListener(mouseProperty.dispatcher());
            mouseProperty.manage(this);

            widget.addMouseTrackListener(trackProperty.dispatcher());
            trackProperty.manage(this);

            widget.addMouseMoveListener(mouseMoveProperty.dispatcher());
            mouseMoveProperty.manage(this);

            widget.addMouseWheelListener(wheelProperty.dispatcher());
            wheelProperty.manage(this);

            widget.addKeyListener(keyProperty.dispatcher());
            keyProperty.manage(this);
        }
    }

    @Override
    public void onResize(Closure closure) {
        this.sizeClosure = closure;
        this.controlProperty.closure(sizeClosure,moveClosure);
    }

    @Override
    public void onResize(String method) {
        this.controlProperty.setResizedMethod(method);
    }

    @Override
    public void onMove(Closure closure) {
        this.moveClosure = closure;
        this.moveClosure.setDelegate(this);
        this.moveClosure.setResolveStrategy(Closure.DELEGATE_ONLY);
        this.controlProperty.closure(sizeClosure,moveClosure);
    }

    @Override
    public void onMove(String method) {
        this.controlProperty.setMoveMethod(method);
    }

    @Override
    public void onMouseUp(String method) {
        mouseProperty.setMouseUpMethod(method);
    }

    @Override
    public void onMouseUp(Closure closure) {
        this.mouseUpClosure = closure;
        this.mouseUpClosure.setDelegate(this);
        this.mouseUpClosure.setResolveStrategy(Closure.DELEGATE_ONLY);
        mouseProperty.closure(this.mouseUpClosure,this.mouseDownClosure,this.mouseDBClickClosure);
    }

    @Override
    public void onMouseDown(String method) {
        mouseProperty.setMouseDownMethod(method);
    }

    @Override
    public void onMouseDown(Closure closure) {
        this.mouseDownClosure = closure;
        this.mouseDownClosure.setDelegate(this);
        this.mouseDownClosure.setResolveStrategy(Closure.DELEGATE_ONLY);
        mouseProperty.closure(this.mouseUpClosure,this.mouseDownClosure,this.mouseDBClickClosure);
    }

    @Override
    public void onMouseDBClick(String method) {
        mouseProperty.setMouseDoubleClickMethod(method);
    }

    @Override
    public void onMouseDBClick(Closure closure) {

        this.mouseDBClickClosure = closure;
        this.mouseDBClickClosure.setDelegate(this);
        this.mouseDBClickClosure.setResolveStrategy(Closure.DELEGATE_ONLY);

        mouseProperty.closure(this.mouseUpClosure,this.mouseDownClosure,this.mouseDBClickClosure);
    }

    @Override
    public void onMouseEnter(String method) {
        this.trackProperty.setMouseEnterMethod(method);
    }

    @Override
    public void onMouseEnter(Closure closure) {

        this.mouseEnterClosure = closure;
        this.mouseEnterClosure.setDelegate(this);
        this.mouseEnterClosure.setResolveStrategy(Closure.DELEGATE_ONLY);

        this.trackProperty.closure(this.mouseEnterClosure,this.mouseExitClosure,this.mouseHoverClosure);
    }

    @Override
    public void onMouseExit(String method) {
        this.trackProperty.setMouseExitedMethod(method);
    }

    @Override
    public void onMouseExit(Closure closure) {
        this.mouseExitClosure = closure;

        this.mouseExitClosure.setDelegate(this);
        this.mouseExitClosure.setResolveStrategy(Closure.DELEGATE_ONLY);

        this.trackProperty.closure(this.mouseEnterClosure,this.mouseExitClosure,this.mouseHoverClosure);
    }

    @Override
    public void onMouseHover(String method) {
        this.trackProperty.setMouseHoverMethod(method);
    }

    @Override
    public void onMouseHover(Closure closure) {
        this.mouseHoverClosure = closure;
        this.mouseHoverClosure.setDelegate(this);
        this.mouseHoverClosure.setResolveStrategy(Closure.DELEGATE_ONLY);
        this.trackProperty.closure(this.mouseEnterClosure,this.mouseExitClosure,this.mouseHoverClosure);
    }

    @Override
    public void onMouseMove(String method) {
        this.mouseMoveProperty.setMouseMovedMethod(method);
    }

    @Override
    public void onMouseMove(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        this.mouseMoveProperty.closure(closure);
    }

    @Override
    public void onMouseWheel(String method) {
        wheelProperty.setMouseWheelMethod(method);
    }

    @Override
    public void onMouseWheel(Closure closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        wheelProperty.closure(closure);
    }

    @Override
    public void onKeyPressed(String method) {
        this.keyProperty.setKeyPressMethod(method);
    }

    @Override
    public void onKeyRelease(Closure closure) {
        this.keyReleaseClosure = closure;
        this.keyReleaseClosure.setDelegate(this);
        this.keyReleaseClosure.setResolveStrategy(Closure.DELEGATE_ONLY);
        this.keyProperty.closure(this.keyPressClosure, this.keyReleaseClosure);
    }

    @Override
    public void onKeyRelease(String method) {
        this.keyProperty.setKeyReleaseMethod(method);
    }

    @Override
    public void onKeyPressed(Closure closure) {
        this.keyPressClosure = closure;
        this.keyPressClosure.setDelegate(this);
        this.keyPressClosure.setResolveStrategy(Closure.DELEGATE_ONLY);
        this.keyProperty.closure(this.keyPressClosure, this.keyReleaseClosure);
    }
}
