/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package br.zyuudan.accessory;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-11-14
 */
public enum AccessorySlot {
    Muzzle("枪口"),
    Grip("握把"),
    Barrel("枪管"),
    SlideRail("滑轨"),
    Sight("瞄准镜"),
    Buttstock("枪托"),
    Magazine("弹匣"),
    Ammunition("弹药"),
    Trigger("扳机");
    private String DisplayName;
    private Class<? extends Accessory> BaseClass;
    private Map<String, Accessory> RegisteredAccessory = new HashMap<>();

    private AccessorySlot(String DisplayName) {
        this.DisplayName = DisplayName;
    }

    private AccessorySlot(String DisplayName, Class<? extends Accessory> BaseClass) {
        this.DisplayName = DisplayName;
        this.BaseClass = BaseClass;
    }

    public Class<? extends Accessory> getBaseClass() {
        return BaseClass;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public Accessory getAccessory(String name) {
        return RegisteredAccessory.get(name);
    }

    public <T extends Accessory> void registerAccessory(Class<T> cls) {
        if(BaseClass.isAssignableFrom(cls)){
            try {
                T t = cls.newInstance();
                RegisteredAccessory.put(t.getName(), t);
            } catch (InstantiationException ex) {
                Logger.getLogger(AccessorySlot.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(AccessorySlot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
