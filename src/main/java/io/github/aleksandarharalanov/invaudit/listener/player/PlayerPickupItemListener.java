package io.github.aleksandarharalanov.invaudit.listener.player;

import io.github.aleksandarharalanov.invaudit.core.ConfigManager;
import io.github.aleksandarharalanov.invaudit.util.auth.AccessUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.HashSet;
import java.util.Set;

public class PlayerPickupItemListener extends PlayerListener {

    @Override
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if (AccessUtil.hasPermission(player, "invaudit.bypass")) return;

        int itemId = event.getItem().getItemStack().getTypeId();
        Set<Integer> audits = new HashSet<>(ConfigManager.getAudits());
        if (audits.contains(itemId)) event.setCancelled(true);
    }
}
