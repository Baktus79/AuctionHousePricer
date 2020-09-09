package no.vestlandetmc.auctionhousepricer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import no.vestlandetmc.auctionhousepricer.config.ItemPrices;

public class ReloadCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		ItemPrices.initialize();

		if(sender instanceof Player) { MessageHandler.sendMessage((Player) sender, "AuctionHousePricer reloaded"); }
		else { MessageHandler.sendConsole("AuctionHousePricer reloaded"); }

		return true;
	}

}
