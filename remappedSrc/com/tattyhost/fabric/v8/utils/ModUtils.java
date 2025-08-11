package com.tattyhost.fabric.v8.utils;

import net.minecraft.block.Block;
import net.minecraft.util.shape.VoxelShape;

public class ModUtils {

    public static VoxelShape[] shapeOnHeights(double heightMax, double heightMin, int maxAge, boolean linear) {

        VoxelShape[] shapes = new VoxelShape[maxAge+1];
        for(int j = 0; j <= maxAge; ++j) {
            double height = heightMin;
            double lin = linear ? (heightMin + (heightMax - heightMin) * (double)j / (double)maxAge) : (heightMax * (double)j / (double)maxAge);

            if(lin > heightMin) height = lin;
            if(height > heightMax) height = heightMax;

            height = Math.floor(height);

            shapes[j] = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, height, 16.0D);
//            LogUtils.getLogger().info("Shape " + j + " = " + shapes[j]);
        }
        return shapes;
    }

}
