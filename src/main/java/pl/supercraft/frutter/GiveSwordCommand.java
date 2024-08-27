package pl.supercraft.frutter;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class GiveSwordCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public GiveSwordCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Tworzenie miecza
            ItemStack sword = new ItemStack(Material.WOODEN_SWORD);
            ItemMeta meta = sword.getItemMeta();
            if (meta != null) {
                meta.setDisplayName("Zeus Sword");
                // Dodaj tagi NBT
                meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "Zeus"), PersistentDataType.STRING, "True");
                meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "delay"), PersistentDataType.INTEGER, 5); // Opóźnienie 5 sekund
                sword.setItemMeta(meta);
            }

            // Dodanie miecza do ekwipunku gracza
            player.getInventory().addItem(sword);
            player.sendMessage("Otrzymałeś miecz Zeus z opóźnieniem 5 sekund!");

            return true;
        } else {
            sender.sendMessage("Ta komenda może być używana tylko przez graczy.");
            return false;
        }
    }
}
