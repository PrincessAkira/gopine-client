package net.gopine.modules.draggable;

public abstract class DraggableElement {

    private ScreenPos pos;

    public DraggableElement(ScreenPos pos) {
        this.pos = pos;
    }

    public void onRender(ScreenPos pos) {

    }

    public void onDummyRender(ScreenPos pos) {

    }

}
