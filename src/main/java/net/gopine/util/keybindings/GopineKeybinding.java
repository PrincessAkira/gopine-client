package net.gopine.util.keybindings;

public abstract class GopineKeybinding {

    public String name;
    public int keyCode;

    public GopineKeybinding(String name, int keyCode) {
        this.name = name;
        this.keyCode = keyCode;
    }

    /**
     * The code performed on key clicked
     * @author MatthewTGM| MatthewTGM#4058
     * @since b1.0
     */
    public void onClick() {

    }

}