package net.gopine.modules.impl;

import net.gopine.modules.Module;
import net.gopine.modules.ModuleCategory;

public class OverlayToggleModule extends Module {
    public static boolean overlayToggled = false;

    public OverlayToggleModule(String name, ModuleCategory category, boolean toggled, boolean hud) {
        super("Overlay toggle module", ModuleCategory.RENDERING, true, false);
    }
}
