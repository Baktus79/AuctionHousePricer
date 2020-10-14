package no.vestlandetmc.auctionhousepricer;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import com.spawnchunk.auctionhouse.events.ListItemEvent;
import com.spawnchunk.auctionhouse.modules.ListingType;

import no.vestlandetmc.auctionhousepricer.config.ItemPrices;

public class Listener implements org.bukkit.event.Listener {

	@EventHandler
	public void auctions(ListItemEvent e) {
		if(e.getType() == ListingType.PLAYER_LISTING) {
			final float listPrice = e.getPrice();
			final Material listMat = e.getItem().getType();
			final Player player = (Player) e.getSeller();
			final int itemAmount = e.getItem().getAmount();
			final double realListPrice = listPrice / itemAmount;
			double limitPrice = 0;
			final Map<Enchantment, Integer> enchantments = e.getItem().getEnchantments();

			if(listMat.equals(Material.ENCHANTED_BOOK)) {
				final EnchantmentStorageMeta meta = (EnchantmentStorageMeta) e.getItem().getItemMeta();
				final Map<Enchantment, Integer> enchantList = meta.getStoredEnchants();

				for(final Enchantment enchant : enchantList.keySet()) {
					if(ItemPrices.ENCHANTPRICES.containsKey(enchant)) {
						final double enchantPrice = ItemPrices.ENCHANTPRICES.get(enchant);

						if(ItemPrices.ENCHANT_MULTIPLIER.containsKey(enchant)) {
							final double enchant_level = enchantList.get(enchant);
							final double multiplier = ItemPrices.ENCHANT_MULTIPLIER.get(enchant) * enchant_level;

							limitPrice = limitPrice + (enchantPrice * multiplier);
						} else { limitPrice = limitPrice + enchantPrice; }
					}
				}
			}

			for(final Enchantment enchant : enchantments.keySet()) {
				if(ItemPrices.ENCHANTPRICES.containsKey(enchant)) {
					final double enchantPrice = ItemPrices.ENCHANTPRICES.get(enchant);

					if(ItemPrices.ENCHANT_MULTIPLIER.containsKey(enchant)) {
						final double enchant_level = e.getItem().getEnchantmentLevel(enchant);
						final double multiplier = ItemPrices.ENCHANT_MULTIPLIER.get(enchant) * enchant_level;

						limitPrice = limitPrice + (enchantPrice * multiplier);
					} else { limitPrice = limitPrice + enchantPrice; }
				}
			}

			if(ItemPrices.ITEMPRICES.containsKey(listMat)) {
				limitPrice = limitPrice + ItemPrices.ITEMPRICES.get(listMat);

				if(realListPrice < limitPrice) {
					MessageHandler.sendMessage(player,
							"&cThe minimum amount cannot be lower than the market price.",
							"&cThe marked price is set to&b: $" + balFormat(limitPrice * itemAmount),
							"&cYour price per item: &b$" + balFormat(listPrice));

					e.setCancelled(true);

				}
			}
		}
	}

	private static String balFormat(double amount) {
		final DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		final DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

		symbols.setGroupingSeparator(',');
		formatter.setDecimalFormatSymbols(symbols);

		return formatter.format(amount);
	}
}
