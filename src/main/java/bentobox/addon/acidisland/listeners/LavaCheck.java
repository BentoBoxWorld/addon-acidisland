package bentobox.addon.acidisland.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import bentobox.addon.acidisland.AcidIsland;


public class LavaCheck implements Listener {

    private final AcidIsland addon;


    public LavaCheck(AcidIsland addon) {
        this.addon = addon;
    }

    /**
     * Removes stone generated by lava pouring on water
     *
     * @param e - event
     */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCleanstoneGen(BlockFromToEvent e) {
        // Only do this in AcidIsland over world
        if (!e.getBlock().getWorld().equals(addon.getIslandWorld()) || addon.getSettings().getAcidDamage() <= 0
                // TODO: backward compatibility hack
                || !(e.getToBlock().getType().name().equals("WATER"))) {
            return;
        }
        Material prev = e.getToBlock().getType();
        addon.getServer().getScheduler().runTask(addon.getPlugin(), () -> {
            if (e.getToBlock().getType().equals(Material.STONE)) {
                e.getToBlock().setType(prev);
                e.getToBlock().getWorld().playSound(e.getToBlock().getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1F, 2F);
            }
        });
    }


}
