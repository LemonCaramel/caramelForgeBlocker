package moe.caramel.forgeblocker.events;

import moe.caramel.forgeblocker.utils.Constants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.Arrays;

public class PlayerCommandEvent implements Listener {

    public static class MC1_12 implements Listener {

        @EventHandler(priority = EventPriority.MONITOR)
        public void onTabComplete(TabCompleteEvent event) {
            String cmd = event.getBuffer();
            if (cmd.contains(" ")) return;
            cmd = cmd.substring(1);

            for (String commands : Constants.PLUGIN_COMMAND)
                if (commands.startsWith(cmd) && !event.getCompletions().contains(commands))
                    event.getCompletions().add("/" + commands);
        }

    }

    public static class MC1_13 implements Listener {

        @EventHandler(priority = EventPriority.MONITOR)
        public void onPlayerCommandSend(PlayerCommandSendEvent event) {
            event.getCommands().addAll(Arrays.asList(Constants.PLUGIN_COMMAND));
        }

    }

}