package io.github.aleksandarharalanov.invaudit.core;

import io.github.aleksandarharalanov.invaudit.InvAudit;
import io.github.aleksandarharalanov.invaudit.util.auth.AccessUtil;
import io.github.aleksandarharalanov.invaudit.util.log.LogUtil;
import io.github.aleksandarharalanov.invaudit.util.misc.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public final class ItemAuditor {

    private static int clearTaskId = -1;

    private ItemAuditor() {}

    public static void startClearTask() {
        if (!ConfigManager.getEnabled()) return;

        cancelClearTask();

        long timer = ConfigManager.getTimerTicks();
        if (timer <= 0) {
            LogUtil.logConsoleWarning("[InvAudit] Invalid audit timer in config. Defaulting to 100L ticks.");
            timer = 100L;
        }

        LogUtil.logConsoleInfo("[InvAudit] Audit task started.");
        clearTaskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(
                InvAudit.getInstance(), ItemAuditor::audit, 0L, timer);
    }

    public static void stopClearTask() {
        if (clearTaskId != -1) {
            cancelClearTask();
            LogUtil.logConsoleInfo("[InvAudit] Audit task stopped.");
        } else {
            LogUtil.logConsoleInfo("[InvAudit] Audit task is not running.");
        }
    }

    private static void cancelClearTask() {
        if (clearTaskId != -1) {
            Bukkit.getServer().getScheduler().cancelTask(clearTaskId);
            clearTaskId = -1;
        }
    }

    private static void audit() {
        Set<Integer> audits = new HashSet<>(ConfigManager.getAudits());
        if (audits.isEmpty()) {
            return;
        }

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (AccessUtil.hasPermission(player, "invaudit.bypass")) {
                continue;
            }

            boolean audited = false;
            for (int i = 0; i < player.getInventory().getSize(); i++) {
                ItemStack item = player.getInventory().getItem(i);
                if (item == null || item.getType() == Material.AIR || item.getTypeId() == 0) {
                    continue;
                }

                if (audits.contains(item.getTypeId())) {
                    player.getInventory().clear(i);
                    audited = true;
                    LogUtil.logConsoleInfo(String.format(
                            "[InvAudit] Removed %dx of ID %d from player %s.",
                            item.getAmount(), item.getTypeId(), player.getName()));
                }
            }

            if (audited) {
                player.sendMessage(ColorUtil.translateColorCodes("&c[InvAudit] Inventory cleared of illegal items."));
            }
        }
    }

    public static boolean hasIllegalItems(Player player) {
        Set<Integer> audits = new HashSet<>(ConfigManager.getAudits());
        Set<Integer> seen = new HashSet<>();

        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null || item.getTypeId() == 0 || item.getType() == Material.AIR) continue;
            int id = item.getTypeId();
            if (seen.contains(id)) continue;
            seen.add(id);

            if (audits.contains(id)) return true;
        }

        return false;
    }
}
