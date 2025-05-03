package io.github.aleksandarharalanov.invaudit.listener.player;

import io.github.aleksandarharalanov.invaudit.core.ItemAuditor;
import io.github.aleksandarharalanov.invaudit.util.auth.AccessUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupItemListener extends PlayerListener {

    @Override
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if (AccessUtil.hasPermission(player, "invaudit.bypass")) return;
        if (ItemAuditor.isAuditedItem(event.getItem().getItemStack())) event.setCancelled(true);
    }
}
