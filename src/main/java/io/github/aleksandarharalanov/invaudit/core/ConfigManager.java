package io.github.aleksandarharalanov.invaudit.core;

import io.github.aleksandarharalanov.invaudit.InvAudit;

import java.util.ArrayList;
import java.util.List;

public final class ConfigManager {

    private ConfigManager() {}

    public static boolean getEnabled() {
        return InvAudit.getConfig().getBoolean("enabled", true);
    }

    public static void setEnabled(boolean bool) {
        InvAudit.getConfig().setProperty("enabled", bool);
        InvAudit.getConfig().save();
    }

    public static int getTimerTicks() {
        return InvAudit.getConfig().getInt("clear-timer-ticks", 100);
    }

    public static void setTimerTicks(int ticks) {
        InvAudit.getConfig().setProperty("clear-timer-ticks", ticks);
        InvAudit.getConfig().save();
    }

    public static List<String> getAudits() {
        return InvAudit.getConfig().getStringList("audit-items", new ArrayList<>());
    }

    public static void setAudits(List<String> audits) {
        InvAudit.getConfig().setProperty("audit-items", audits);
        InvAudit.getConfig().save();
    }
}
