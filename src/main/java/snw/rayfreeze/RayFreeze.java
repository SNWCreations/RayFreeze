package snw.rayfreeze;

import org.bukkit.plugin.java.JavaPlugin;

public final class RayFreeze extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        new Calculator().start();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
