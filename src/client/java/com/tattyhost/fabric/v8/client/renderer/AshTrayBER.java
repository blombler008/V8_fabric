package com.tattyhost.fabric.v8.client.renderer;

import com.tattyhost.fabric.v8.V8;
import com.tattyhost.fabric.v8.blocks.custom.AshTrayBlockEntity;
import com.tattyhost.fabric.v8.client.model.AshTrayModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class AshTrayBER implements BlockEntityRenderer<AshTrayBlockEntity> {
    private final AshTrayModel model;
    private BlockEntityRendererFactory.Context context;
    public AshTrayBER(BlockEntityRendererFactory.Context ctx) {
        model = new AshTrayModel(ctx.getLayerModelPart(AshTrayModel.LAYER));
        this.context = ctx;
    }

    @Override
    public void render(AshTrayBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {

        int level = entity.getAshLevel();
        // scaleY should be between 0.1 and 1.0, depending on the ash level
        // 0 level = 0.1, 8 level = 1.0
        float scaleY = 0.1f + (level / 8.0f) * 0.9f;


        matrices.push();
        matrices.translate(0.5, 0, 0.5);
        model.setAshScale(scaleY);
        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(AshTrayModel.TEXTURE)), light, overlay);
        matrices.pop();
    }
}
