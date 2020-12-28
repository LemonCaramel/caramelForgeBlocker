package moe.caramel.forgeblocker.events;

import moe.caramel.forgeblocker.Main;
import moe.caramel.forgeblocker.utils.Constants;
import moe.caramel.forgeblocker.utils.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.net.InetAddress;

public class PlayerHandshakeEvent implements Listener {

    private final Main plugin;

    public PlayerHandshakeEvent(Main plugin) {
        this.plugin = plugin;
    }

    // We can use Paper's Handshake Event,
    // but we didn't use it for Spigot compatibility.
    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        if (plugin.stopDetect) return;
        InetAddress address = event.getAddress();
        if (plugin.kickPlayer.contains(address)) {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            event.setKickMessage(Constants.ETC_KICK_MESSAGE_PREFIX
                    + Util.colorTranslate(plugin.getConfig().getString(Constants.CONFIG_PLUGIN_KICKMESSAGE)));
            plugin.kickPlayer.remove(address);
        }
    }

}
