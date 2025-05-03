package io.github.aleksandarharalanov.invaudit.command.subcommands;

import io.github.aleksandarharalanov.invaudit.core.ConfigManager;
import io.github.aleksandarharalanov.invaudit.util.auth.AccessUtil;
import io.github.aleksandarharalanov.invaudit.util.log.LogUtil;
import io.github.aleksandarharalanov.invaudit.util.misc.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class AuditCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!AccessUtil.hasPermission(sender, "invaudit.config",
                "[InvAudit] You don't have permission to modify the config.")) {
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ColorUtil.translateColorCodes("&c[InvAudit] Missing ID argument."));
            return true;
        }

        String input = args[1];

        if (!input.matches("^\\d+(?::\\d+)?$")) {
            sender.sendMessage(ColorUtil.translateColorCodes("&c[InvAudit] Invalid format. Use ID or ID:Data."));
            return true;
        }

        String[] parts = input.split(":");
        int id = Integer.parseInt(parts[0]);

        if (!isValidId(id)) {
            sender.sendMessage(ColorUtil.translateColorCodes("&c[InvAudit] Invalid item ID."));
            return true;
        }

        Set<String> audits = new HashSet<>(ConfigManager.getAudits());
        boolean added = audits.add(input);

        if (!added) {
            audits.remove(input);
        }

        String action = added ? "Added" : "Removed";
        String color = added ? "&a" : "&c";
        String message = String.format("%s[InvAudit] %s ID %s %s audit list.",
                color, action, input, added ? "to" : "from");

        if (sender instanceof Player) {
            sender.sendMessage(ColorUtil.translateColorCodes(message));
        }

        LogUtil.logConsoleInfo(ColorUtil.stripColorCodes(ColorUtil.translateColorCodes(message)));

        ConfigManager.setAudits(new ArrayList<>(audits));
        return true;
    }

    private static boolean isValidId(int id) {
        return isBetween(id, 1, 96)
                || isBetween(id, 256, 359)
                || isBetween(id, 2256, 2257);
    }

    private static boolean isBetween(int value, int min, int max) {
        return value >= min && value <= max;
    }
}
