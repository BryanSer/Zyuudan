/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package br.zyuudan;

import java.util.List;
import java.util.Random;
import org.bukkit.configuration.ConfigurationSection;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-12-1
 */
public final class Matrix {

    private static Random RANDOM = new Random();

    private double[][] values;
    private int center_x;
    private int center_y;
    private double totalWeight;

    public Matrix(List<List<Double>> value) {
        this.setValues(value);
        this.center_y = values.length / 2 - 1;
        this.center_x = values[0].length / 2 - 1;
    }

    public Matrix(int x, int y) {
        this.center_x = x;
        this.center_y = y;
    }

    public void setValues(List<List<Double>> value) {
        values = value
                .stream()
                .map(t
                        -> t
                        .stream()
                        .mapToDouble(Double::doubleValue)
                        .toArray()
                )
                .toArray(double[][]::new);
    }

    public static Matrix readMatrix(ConfigurationSection config, String path) {
        if (config.isConfigurationSection(path)) {
            ConfigurationSection cs = config.getConfigurationSection(path);
            List<Integer> center = cs.getIntegerList("center");
            List<List<Double>> values = (List<List<Double>>) cs.getList("values");
            Matrix m = new Matrix(center.get(0), center.get(1));
            m.setValues(values);
            if (!m.isValid()) {
                throw new IllegalArgumentException("配置的矩阵错误, 路径:" + config.getCurrentPath() + "." + path);
            }
            return m;
        }
        Matrix m = new Matrix((List<List<Double>>) config.getList(path));

        if (!m.isValid()) {
            throw new IllegalArgumentException("配置的矩阵错误, 路径:" + config.getCurrentPath() + "." + path);
        }
        return m;
    }

    public double at(int x, int y) {
        return values[y][x];
    }

    public double atCenter() {
        return values[center_y][center_x];
    }

    private boolean isValid() {
        if (center_x < 0 || center_y < 0) {
            return false;
        }
        int height = values.length;
        int width = values[0].length;
        for (double[] v : values) {
            for (double t : v) {
                if (t < 0) {
                    return false;
                }
            }
            if (v.length != width) {
                return false;
            }
        }
        return center_x < width && center_y < height;
    }

    public int[] randomOffset() {
        int[] random = random();
        return new int[]{random[0] - center_x, random[1] - center_y};
    }

    public int[] random() {
        double r = RANDOM.nextDouble() * totalWeight;
        for (int i = 0; i < values.length; i++) {
            double[] value = values[i];
            for (int j = 0; j < value.length; j++) {
                double t = value[j];
                r -= t;
                if (r <= 0) {
                    return new int[]{j, i};
                }
            }
        }
        throw new IllegalStateException("随机状态出错");

    }
}
