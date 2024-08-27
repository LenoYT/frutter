package pl.supercraft.frutter;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerDamageListener implements Listener {

    private final JavaPlugin plugin;

    public PlayerDamageListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            Entity target = event.getEntity();  // Cel ataku
            ItemStack itemInHand = attacker.getInventory().getItemInMainHand();

            if (itemInHand.getType() == Material.WOODEN_SWORD) {
                ItemMeta meta = itemInHand.getItemMeta();

                if (meta != null && "True".equals(meta.getPersistentDataContainer().get(new NamespacedKey(plugin, "Zeus"), PersistentDataType.STRING))) {
                    int delay = meta.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin, "delay"), PersistentDataType.INTEGER, 0);

                    if (delay > 0) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                int remainingDelay = meta.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin, "delay"), PersistentDataType.INTEGER, 0);

                                if (remainingDelay > 0) {
                                    meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "delay"), PersistentDataType.INTEGER, remainingDelay - 1);
                                    itemInHand.setItemMeta(meta);
                                }

                                if (remainingDelay == 1) {
                                    // Wywołanie komendy do stworzenia błyskawicy
                                    double x = target.getLocation().getX();
                                    double y = target.getLocation().getY();
                                    double z = target.getLocation().getZ();
                                    String command = "execute run summon lightning_bolt " + x + " " + y + " " + z;

                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
                                }
                            }
                        }.runTaskTimer(plugin, 0L, 20L); // Uruchamiaj co 1 sekundę
                    }
                }
            }
        }
    }
}
