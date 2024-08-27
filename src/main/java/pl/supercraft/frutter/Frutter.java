package pl.supercraft.frutter;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Frutter extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Starting Plugin Frutter");

        // Rejestracja komendy
        Objects.requireNonNull(this.getCommand("givesword")).setExecutor(new GiveSwordCommand(this));

        // Rejestracja listenerów
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling Plugin Frutter");
    }
}
