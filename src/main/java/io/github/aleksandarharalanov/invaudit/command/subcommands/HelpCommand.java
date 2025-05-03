package io.github.aleksandarharalanov.invaudit.command.subcommands;

import io.github.aleksandarharalanov.invaudit.util.auth.AccessUtil;
import io.github.aleksandarharalanov.invaudit.util.log.LogUtil;
import io.github.aleksandarharalanov.invaudit.util.misc.ColorUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class HelpCommand {

    private HelpCommand() {}

    public static void sendHelp(CommandSender sender) {
        String[] messages = {
                "&bInvAudit commands:",
                "&e/ia &7- Displays this content.",
                "&e/ia about &7- About InvAudit."
        };
        String[] staffMessages = {
                "&bInvAudit staff commands:",
                "&e/ia reload &7- Reload InvAudit config.",
                "&e/ia toggle &7- Toggle InvAudit plugin.",
                "&e/ia timer <ticks> &7- Alter clear timer for illegal items.",
                "&e/ia audit <ID|ID:Data> &7- Add/remove illegal items."
        };

        for (String message : messages) {
            if (sender instanceof Player) {
                sender.sendMessage(ColorUtil.translateColorCodes(message));
            } else {
                LogUtil.logConsoleInfo(message.replaceAll("&.", ""));
            }
        }

        for (String staffMessage : staffMessages) {
            if (!(sender instanceof Player)) {
                LogUtil.logConsoleInfo(staffMessage.replaceAll("&.", ""));
                continue;
            }

            if (AccessUtil.hasPermission(sender, "invaudit.config")) {
                sender.sendMessage(ColorUtil.translateColorCodes(staffMessage));
            }
        }
    }
}
