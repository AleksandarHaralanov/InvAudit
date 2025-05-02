package io.github.aleksandarharalanov.invaudit.command.subcommands;

import io.github.aleksandarharalanov.invaudit.InvAudit;
import io.github.aleksandarharalanov.invaudit.core.ItemAuditor;
import io.github.aleksandarharalanov.invaudit.util.auth.AccessUtil;
import io.github.aleksandarharalanov.invaudit.util.misc.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!AccessUtil.hasPermission(sender, "invaudit.config",
                "[InvAudit] You don't have permission to reload the config.")) {
            return true;
        }

        if (sender instanceof Player) {
            sender.sendMessage(ColorUtil.translateColorCodes("&a[InvAudit] Configurations reloaded."));
        }

        ItemAuditor.stopClearTask();
        InvAudit.getConfig().loadAndLog();
        ItemAuditor.startClearTask();

        return true;
    }
}