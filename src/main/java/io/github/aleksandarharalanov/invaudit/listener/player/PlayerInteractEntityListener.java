package io.github.aleksandarharalanov.invaudit.listener.player;

import io.github.aleksandarharalanov.invaudit.core.ItemAuditor;
import io.github.aleksandarharalanov.invaudit.util.auth.AccessUtil;
import io.github.aleksandarharalanov.invaudit.util.misc.ColorUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerListener;

public class PlayerInteractEntityListener extends PlayerListener {

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (AccessUtil.hasPermission(player, "invaudit.bypass")) return;

        Entity entity = event.getRightClicked();
        if (entity == null) return;
        if (!(entity instanceof StorageMinecart)) return;

        if (ItemAuditor.hasIllegalItems(player)) {
            player.sendMessage(ColorUtil.translateColorCodes(
                    "&c[InvAudit] Can't open containers while carrying illegal items."));
            event.setCancelled(true);
        }
    }
}
