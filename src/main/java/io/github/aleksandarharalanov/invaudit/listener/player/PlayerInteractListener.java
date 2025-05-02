package io.github.aleksandarharalanov.invaudit.listener.player;

import io.github.aleksandarharalanov.invaudit.core.ItemAuditor;
import io.github.aleksandarharalanov.invaudit.util.auth.AccessUtil;
import io.github.aleksandarharalanov.invaudit.util.misc.ColorUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class PlayerInteractListener extends PlayerListener {

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (AccessUtil.hasPermission(player, "invaudit.bypass")) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        Material type = block.getType();
        boolean isContainer = (type == Material.CHEST ||
                type == Material.FURNACE ||
                type == Material.DISPENSER);
        if (!isContainer) return;

        if (ItemAuditor.hasIllegalItems(player)) {
            player.sendMessage(ColorUtil.translateColorCodes(
                    "&c[InvAudit] Can't open containers while carrying illegal items."));
            event.setCancelled(true);
        }
    }
}
