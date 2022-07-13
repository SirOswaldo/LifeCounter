package com.realxode.api.item;

import com.realxode.lifecounter.LifeCounter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

import static com.realxode.api.chat.ChatUtil.translate;

public class ItemBuilder {

    protected Material material;
    protected int amount = 1;
    protected short durability;
    protected String owner;

    protected Player player;
    protected int currentLevel = 0;

    protected String persistentDataKey;
    protected String persistentDataValue;

    protected String name;
    protected List<String> lore = new ArrayList<>();
    protected List<String> enchantments = new ArrayList<>();

    protected boolean hasGlow = false;

    public ItemBuilder(Material material) {
        this.material = material;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setDurability(short durability) {
        this.durability = durability;
        return this;
    }

    public ItemBuilder setName(String name) {
        this.name = translate(name);
        return this;
    }

    public ItemBuilder setEnchantments(List<String> enchantmets) {
        for (String enchantment : enchantmets) {
            this.enchantments.add(enchantment);
        }
        return this;
    }

    public ItemBuilder setLore(String... lines) {
        for (String line : lines) {
            lore.add(translate(line));

        }
        return this;
    }

    public ItemBuilder setLore(List<String> lines) {
        for (String line : lines) {
            lore.add(translate(line));
        }
        return this;
    }

    public ItemBuilder replaceInLore(String regular, String replacement) {
        for (int i = 0; i < lore.size(); i++) {
            lore.set(i, translate(lore.get(i).replace(regular, replacement)));
        }
        return this;
    }

    public ItemBuilder addLore(String line) {
        lore.add(translate(line));
        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public ItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder setGlow(boolean value) {
        this.hasGlow = value;
        return this;
    }

    public ItemBuilder setPersistentData(String key, String value) {
        this.persistentDataKey = key;
        this.persistentDataValue = value.replace("%level%", String.valueOf(currentLevel));
        return this;
    }

    public ItemBuilder setOwner(String ownerName) {
        this.owner = ownerName;
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material, amount);

        if (durability > 0) {
            itemStack.setDurability(durability);
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (name != null) {
            itemMeta.setDisplayName(translate(name));
        }

        if (lore.size() > 0) {
            itemMeta.setLore(lore);
        }
        if (persistentDataKey != null) {
            itemMeta.getPersistentDataContainer().set(new NamespacedKey(LifeCounter.getInstance(), persistentDataKey), PersistentDataType.STRING, persistentDataValue);
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
