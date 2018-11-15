/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package br.zyuudan;

import Br.API.Utils;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-11-14
 */
public class FileManager {

    public static void init() {
        if (!Data.Plugin.getDataFolder().exists()) {
            Data.Plugin.getDataFolder().mkdir();
        }
        File folder = new File(Data.Plugin.getDataFolder(), File.separator + "zyuu" + File.separator);
        if (!folder.exists()) {
            folder.mkdir();
        }
        try {
            Utils.OutputFile(Data.Plugin, "example.yml", folder);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        load(folder);
    }

    public static void load(File f) {
        if (f.isDirectory()) {
            for (File t : f.listFiles()) {
                load(t);
            }
            return;
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        Object get = config.get("Base.Spread");
        System.out.println(get.getClass());
        if (get instanceof List) {
            List<List<Integer>> list = (List<List<Integer>>) get;
            for (List<Integer> l : list) {
                System.out.println(l);
            }
        }
    }
}
