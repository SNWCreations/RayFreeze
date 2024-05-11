package snw.rayfreeze;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class Calculator extends BukkitRunnable {
    private static final PotionEffect SLOW;

    static {
        SLOW = new PotionEffect(
                PotionEffectType.SLOW,
                PotionEffect.INFINITE_DURATION,
                255,
                false, false, false
        );
    }

    private boolean helmetIsOf(Player player, String expected) {
        final ItemStack helmet = player.getInventory().getHelmet();
        if (helmet == null) {
            return false;
        }
        final String key = helmet.getType().getKey().toString();
        return key.equals(expected);
    }

    private boolean shouldCalc(Player player) {
        return helmetIsOf(player, "rfm:yanjing");
    }

    private boolean validHit(Player player) {
        return helmetIsOf(player, "minecraft:oak_button");
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!shouldCalc(player)) {
                continue;
            }
            final World world = player.getWorld();
            final Location where = player.getLocation();
            final Vector vector = player.getEyeLocation().getDirection().normalize();
            final RayTraceResult result = world.rayTraceEntities(where, vector, 20);
            final Entity hitEntity = result.getHitEntity();
            if (hitEntity instanceof Player hitPlayer) {
                if (validHit(hitPlayer)) {
                    hitPlayer.addPotionEffect(SLOW);
                }
            }
        }
    }

    public void start() {
        runTaskTimer(JavaPlugin.getPlugin(RayFreeze.class), 10L, 10L);
    }
}
