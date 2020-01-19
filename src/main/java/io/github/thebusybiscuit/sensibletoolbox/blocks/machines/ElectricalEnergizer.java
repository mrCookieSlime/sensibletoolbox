package io.github.thebusybiscuit.sensibletoolbox.blocks.machines;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

import io.github.thebusybiscuit.sensibletoolbox.api.SensibleToolbox;
import io.github.thebusybiscuit.sensibletoolbox.api.items.AbstractIOMachine;
import io.github.thebusybiscuit.sensibletoolbox.api.recipes.CustomRecipeManager;
import io.github.thebusybiscuit.sensibletoolbox.api.recipes.SimpleCustomRecipe;
import io.github.thebusybiscuit.sensibletoolbox.api.util.STBUtil;
import io.github.thebusybiscuit.sensibletoolbox.items.components.EnergizedGoldDust;
import io.github.thebusybiscuit.sensibletoolbox.items.components.EnergizedGoldIngot;
import io.github.thebusybiscuit.sensibletoolbox.items.components.EnergizedIronDust;
import io.github.thebusybiscuit.sensibletoolbox.items.components.EnergizedIronIngot;
import io.github.thebusybiscuit.sensibletoolbox.items.components.EnergizedQuartz;
import io.github.thebusybiscuit.sensibletoolbox.items.components.GoldDust;
import io.github.thebusybiscuit.sensibletoolbox.items.components.IronDust;
import io.github.thebusybiscuit.sensibletoolbox.items.components.MachineFrame;
import io.github.thebusybiscuit.sensibletoolbox.items.components.SimpleCircuit;

public class ElectricalEnergizer extends AbstractIOMachine {
	
    private static final MaterialData md = STBUtil.makeColouredMaterial(Material.STAINED_CLAY, DyeColor.GRAY);

    public ElectricalEnergizer() {
    }

    public ElectricalEnergizer(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public void addCustomRecipes(CustomRecipeManager crm) {
        crm.addCustomRecipe(new SimpleCustomRecipe(this, new ItemStack(Material.IRON_INGOT), new EnergizedIronIngot().toItemStack(1), 60 * 20));
        crm.addCustomRecipe(new SimpleCustomRecipe(this, new ItemStack(Material.GOLD_INGOT), new EnergizedGoldIngot().toItemStack(1), 60 * 20));
        crm.addCustomRecipe(new SimpleCustomRecipe(this, new ItemStack(Material.QUARTZ), new EnergizedQuartz().toItemStack(1), 60 * 20));
        crm.addCustomRecipe(new SimpleCustomRecipe(this, new IronDust().toItemStack(1), new EnergizedIronDust().toItemStack(1), 60 * 20));
        crm.addCustomRecipe(new SimpleCustomRecipe(this, new GoldDust().toItemStack(1), new EnergizedGoldDust().toItemStack(1), 60 * 20));
    }

    @Override
    public int getProgressItemSlot() {
        return 12;
    }

    @Override
    public int getProgressCounterSlot() {
        return 3;
    }

    @Override
    public ItemStack getProgressIcon() {
        return new ItemStack(Material.FLINT_AND_STEEL);
    }

    @Override
    public int[] getInputSlots() {
        return new int[]{10};
    }

    @Override
    public int[] getOutputSlots() {
        return new int[]{14};
    }

    @Override
    public int[] getUpgradeSlots() {
        return new int[]{41, 42, 43, 44};
    }

    @Override
    public int getUpgradeLabelSlot() {
        return 40;
    }

    @Override
    protected void onMachineStartup() {
        if (SensibleToolbox.getPluginInstance().getConfigCache().isNoisyMachines()) {
            getLocation().getWorld().playSound(getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 0.5f);
        }
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "Electrical Energizer";
    }

    @Override
    public String[] getLore() {
        return new String[]{"Allows you to energize certain Items"};
    }

    @Override
    public Recipe getRecipe() {
        SimpleCircuit sc = new SimpleCircuit();
        MachineFrame mf = new MachineFrame();
        EnergizedGoldIngot gold = new EnergizedGoldIngot();
        EnergizedQuartz quartz = new EnergizedQuartz();
        registerCustomIngredients(sc, mf, gold, quartz);
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        recipe.shape("QQQ", "IFI", "RGR");
        recipe.setIngredient('I', sc.getMaterialData());
        recipe.setIngredient('F', mf.getMaterialData());
        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('G', gold.getMaterialData());
        recipe.setIngredient('Q', quartz.getMaterialData());
        return recipe;
    }

    @Override
    public int getEnergyCellSlot() {
        return 36;
    }

    @Override
    public int getChargeDirectionSlot() {
        return 37;
    }

    @Override
    public int getInventoryGUISize() {
        return 45;
    }

    @Override
    public int getMaxCharge() {
        return 10000;
    }

    @Override
    public int getChargeRate() {
        return 50;
    }
}