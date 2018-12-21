/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package br.zyuudan.accessory;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-11-14
 */
public abstract class Accessory {

    protected String Name;
    protected String DisplayName;
    protected int Item;
    protected short Dur;
    protected List<String> Lore = new ArrayList<>();

    protected Accessory(ConfigurationSection config) {
        this.Name = config.getString("Name");
        this.DisplayName = ChatColor.translateAlternateColorCodes('&', config.getString("DisplayName"));
        config.getStringList("Lore")
                .stream()
                .map(s -> ChatColor.translateAlternateColorCodes('&', s))
                .forEach(Lore::add);
        String item = config.getString("Item");
        if (item.contains(":")) {
            String s[] = item.split(":");

        } else {

        }
    }

    public String getName() {
        return Name;
    }
}
