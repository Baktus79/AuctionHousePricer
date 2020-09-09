package no.vestlandetmc.auctionhousepricer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageHandler {

	public static void sendMessage(Player player, String... messages) {
		for(final String message : messages) {
			player.sendMessage(colorize(message));
		}
	}

	public static void sendAnnounce(String... messages) {
		for(final Player player : Bukkit.getOnlinePlayers()) {
			for(final String message : messages) {
				player.sendMessage(colorize(message));
			}
		}
	}

	public static void sendConsole(String... messages) {
		for(final String message : messages) {
			AhPricerPlugin.getInstance().getServer().getConsoleSender().sendMessage(colorize(message));
		}
	}

	public static String colorize(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

}
