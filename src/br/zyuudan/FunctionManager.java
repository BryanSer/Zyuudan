/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package br.zyuudan;

import Br.API.Scripts.ScriptLoader;
import Br.API.Utils;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-12-18
 */
public final class FunctionManager {

    public static final ZFuncType WEAK = new ZFuncType(Double.class);
    public static final ZFuncType DAMAGE = new ZFuncType(Double.class);
    public static final ZFuncType FALL = new ZFuncType(Double.class);

    public static class Environment {
        private Player luncher;
        private LivingEntity hit;
        private Weapon weapon;
        
        /**
         * 目前已飞行的距离
         * @return
         */
        public double length(){
            throw new UnsupportedOperationException();
        }
    }
    
    public static class FunctionInfo{
        private String func;
        private Object[] args;
        public FunctionInfo(String info){
            String s[] = info.split("\\|");
            this.func = s[0];
            String v[] = s[1].split(",");
            args = new Object[v.length];
            for (int i = 0; i < v.length; i++) {
                String str = v[i];
                try {
                    double d = Double.parseDouble(str);
                    args[i] = d;
                } catch (Exception e) {
                    args[i] = str;
                }
            }
        }
    }

    public static void load() {
        File folder = Data.Plugin.getDataFolder();
        folder = new File(folder, File.separator + "Function" + File.separator);
        if (!folder.exists()) {
            folder.mkdirs();
            try {
                Utils.saveResource(Data.Plugin, "func_Weak.js", folder);
                Utils.saveResource(Data.Plugin, "func_Damage.js", folder);
                Utils.saveResource(Data.Plugin, "func_Fall.js", folder);
            } catch (IOException ex) {
                Logger.getLogger(FunctionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        File weak = new File(folder, "func_Weak.js");
        NashornScriptEngine weak_js = ScriptLoader.evalAsUTF8(Data.Plugin, weak);
        FunctionManager.WEAK.setScriptEngine(weak_js);
    }

    public static class ZFuncType<R> {

        private Class<R> returnType;

        private NashornScriptEngine scriptEngine;
        private Map<String,Function<Object[],R>> registerFunctions = new HashMap<>();

        public void setScriptEngine(NashornScriptEngine scriptEngine) {
            this.scriptEngine = scriptEngine;
        }

        public R invoke(String func, Environment env, Object... args) {
            Function<Object[], R> f = registerFunctions.get(func);
            if(f != null){
                return f.apply(args);
            }
            try {
                return (R) scriptEngine.invokeFunction(func, env, args);
            } catch (ScriptException ex) {
                Logger.getLogger(FunctionManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(FunctionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

        public Class<R> getReturnType() {
            return returnType;
        }

        private ZFuncType(Class<R> returntype) {
            this.returnType = returntype;
        }
    }

    private FunctionManager() {
    }
}
