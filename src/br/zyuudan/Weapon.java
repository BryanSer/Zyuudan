/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package br.zyuudan;

import br.zyuudan.FunctionManager.FunctionInfo;
import br.zyuudan.accessory.AccessorySlot;
import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-12-18
 */
public class Weapon {

    protected String name;
    protected String displayName;
    protected Base baseValue;
    protected Map<AccessorySlot, List<String>> accessorySlots = new EnumMap<>(AccessorySlot.class);

    public static final class Base {

        private double damage;
        private double weight;
        private BulletType bulletType;
        private double weakenRange;
        private FunctionInfo weakenFunction;
        private FunctionInfo fallFunction;
        private Matrix spread;
        private Matrix recoilMode;
        private double sound;

        public Base(ConfigurationSection config) {
            this.damage = config.getDouble("Damage");
            this.weight = config.getDouble("Weight");
            this.bulletType = BulletType.valueOf(config.getString("BulletType"));
            this.weakenRange = config.getDouble("WeakenRange");
            this.weakenFunction = new FunctionInfo(config.getString("WeakenFunction"));
            this.fallFunction = new FunctionInfo(config.getString("FallFunction"));
            this.spread = Matrix.readMatrix(config, "Spread");
            this.recoilMode = Matrix.readMatrix(config, "RecoilMode");
            this.sound = config.getDouble("Sound");
        }

        public double getDamage() {
            return damage;
        }

        public double getWeight() {
            return weight;
        }

        public BulletType getBulletType() {
            return bulletType;
        }

        public double getWeakenRange() {
            return weakenRange;
        }

        public FunctionInfo getWeakenFunction() {
            return weakenFunction;
        }

        public FunctionInfo getFallFunction() {
            return fallFunction;
        }

        public Matrix getSpread() {
            return spread;
        }

        public Matrix getRecoilMode() {
            return recoilMode;
        }

        public double getSound() {
            return sound;
        }

    }

    public Weapon(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        this.name = config.getString("Name");
        this.displayName = ChatColor.translateAlternateColorCodes('&', config.getString("DisplayName"));
        this.baseValue = new Base(config.getConfigurationSection("Base"));
       
    }

}