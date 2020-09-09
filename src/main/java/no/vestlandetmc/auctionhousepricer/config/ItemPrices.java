package no.vestlandetmc.auctionhousepricer.config;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import no.vestlandetmc.auctionhousepricer.AhPricerPlugin;

public class ItemPrices extends ConfigHandler {

	private final AhPricerPlugin plugin = AhPricerPlugin.getInstance();

	private ItemPrices(String fileName) {
		super(fileName);
	}

	public static HashMap<Material, Double> ITEMPRICES = new HashMap<>();
	public static HashMap<Enchantment, Double> ENCHANTPRICES = new HashMap<>();
	public static HashMap<Enchantment, Double> ENCHANT_MULTIPLIER = new HashMap<>();

	private void onLoad() {
		ITEMPRICES.clear();
		ENCHANTPRICES.clear();

		if(contains("items")) {
			for(final String p : getConfigurationSection("items").getKeys(false)) {
				final Material mat = Material.getMaterial(p.toUpperCase());
				final double price = getDouble("items." + p);

				if(mat == null) {
					plugin.getLogger().warning("[" + plugin.getDescription().getPrefix() + "] " + p.toUpperCase() + " is not a valid material.");
					continue;
				}

				ITEMPRICES.put(mat, price);
			}
		}

		if(contains("enchantments")) {
			for(final String p : getConfigurationSection("enchantments").getKeys(false)) {
				final Enchantment enchant = Enchantment.getByKey(NamespacedKey.minecraft(p.toLowerCase()));
				final double price = getDouble("enchantments." + p + ".price");

				if(enchant == null) {
					plugin.getLogger().warning("[" + plugin.getDescription().getPrefix() + "] " + p.toLowerCase() + " is not a valid enchantment.");
					continue;
				}

				if(contains("enchantments." + p + ".multiplier")) { ENCHANT_MULTIPLIER.put(enchant, getDouble("enchantments." + p + ".multiplier")); }

				ENCHANTPRICES.put(enchant, price);
			}
		}
	}

	public static void initialize() {
		(new ItemPrices("itemprices.yml")).onLoad();
	}
}
