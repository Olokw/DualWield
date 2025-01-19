package net.olokw.dualwield.Utils;

import org.bukkit.Material;

public class ItemsConfig {
    public boolean isTemModelData() {
        return temModelData;
    }

    public void setTemModelData(boolean temModelData) {
        this.temModelData = temModelData;
    }

    public int getModelData() {
        return modelData;
    }

    public void setModelData(int modelData) {
        this.modelData = modelData;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public boolean isTrueDamage() {
        return trueDamage;
    }

    public void setTrueDamage(boolean trueDamage) {
        this.trueDamage = trueDamage;
    }

    public int getColdown() {
        return coldown;
    }

    public void setColdown(int coldown) {
        this.coldown = coldown;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }

    private boolean unbreakable;
    private boolean temModelData;
    private int modelData;
    private Material material;
    private boolean trueDamage;
    private double damage;
    private int coldown;
    private int delay;
}
