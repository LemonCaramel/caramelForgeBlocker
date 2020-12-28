package moe.caramel.forgeblocker.utils;

public class Constants {

    /* Plugin Info */
    public static final int PLUGIN_CONFIG_VERSION = 1;
    public static final String PLUGIN_INFO_NAME = "caramelForgeBlocker";
    public static final String PLUGIN_MAIN_COMMAND = "caramelforgeblocker";
    public static final String[] PLUGIN_COMMAND = {"caramelforgeblocker:caramelforgeblocker",
            "caramelforgeblocker:caramelforge", "caramelforgeblocker:cfb", "caramelforgeblocker",
            "caramelforge", "cfb"};

    /* Plugin Update Channel */
    public static final String PLUGIN_CHANNEL_STABLE = "STABLE";
    public static final String PLUGIN_CHANNEL_BETA = "BETA";
    public static final String PLUGIN_CHANNEL_CUTTINGEDGE = "CUTTINGEDGE";

    /* Plugin Message Channel */
    public final static String MESSAGE_CHANNEL_BRAND_NEWER = "minecraft:brand";
    public final static String MESSAGE_CHANNEL_BRAND_OLDER = "MC|Brand";
    public final static String MESSAGE_CHANNEL_FMLHS = "FML|HS";

    /* Config */
    public static final String CONFIG_SPIGOT_BUNGEE = "settings.bungeecord";
    public static final String CONFIG_PLUGIN_PACKET = "blocker-check-packet";
    public static final String CONFIG_PLUGIN_BYPASS = "blocker-permission-bypass";
    public static final String CONFIG_PLUGIN_KICKMESSAGE = "blocker-kick-message";

    /* FML */
    public static final String HOSTNAME_FML = "\0FML\0";
    public static final String HOSTNAME_FML2 = "\0FML2\0";
    public static final String BRAND_FML = "fml";
    public static final String BRAND_FORGE = "forge";

    /* Permission */
    public static final String PERM_BYPASS_PLUGIN = "caramel.blocker.bypass";
    public static final String PERM_COMMAND_PLUGIN = "caramel.blocker.command";

    /* ETC Message */
    public static final String ETC_KICK_MESSAGE_PREFIX = "§c§lcaramelForgeBlocker\n";

}
