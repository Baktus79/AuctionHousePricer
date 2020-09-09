package no.vestlandetmc.auctionhousepricer;

import org.bukkit.plugin.java.JavaPlugin;

import no.vestlandetmc.auctionhousepricer.config.ItemPrices;

public class AhPricerPlugin extends JavaPlugin {

	private static AhPricerPlugin instance;

	public static AhPricerPlugin getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;

		this.getCommand("ahpreload").setExecutor(new ReloadCommand());
		this.getServer().getPluginManager().registerEvents(new Listener(), this);
		ItemPrices.initialize();
	}

}
