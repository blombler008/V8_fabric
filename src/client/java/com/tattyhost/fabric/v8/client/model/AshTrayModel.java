// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.tattyhost.fabric.v8.client.model;

import com.tattyhost.fabric.v8.V8;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

public class AshTrayModel extends Model {

	public static final EntityModelLayer LAYER = new EntityModelLayer(V8.id("ashtray"), "main");
	public static final Identifier TEXTURE = V8.id("textures/entity/ash_tray.png");


	private final ModelPart ashtray;
	private final ModelPart ash;
	public AshTrayModel(ModelPart root) {
		super(root, RenderLayer::getEntityTranslucent);
		this.ash = root.getChild("ash");
		this.ashtray = root.getChild("ashtray");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData ashtray = modelPartData.addChild("ashtray", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -2.0F, -5.0F, 10.0F, 1.0F, 10.0F, new Dilation(0.0F))
				.uv(20, 23).cuboid(-5.0F, -1.0F, -5.0F, 1.0F, 2.0F, 9.0F, new Dilation(0.0F))
				.uv(32, 11).cuboid(4.0F, -1.0F, -4.0F, 1.0F, 2.0F, 9.0F, new Dilation(0.0F))
				.uv(0, 34).cuboid(-5.0F, -1.0F, 4.0F, 9.0F, 2.0F, 1.0F, new Dilation(0.0F))
				.uv(20, 37).cuboid(-5.0F, 1.0F, -4.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
				.uv(40, 0).cuboid(1.0F, 1.0F, -5.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 37).cuboid(-5.0F, 1.0F, 1.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F))
				.uv(28, 37).cuboid(4.0F, 1.0F, 1.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
				.uv(10, 37).cuboid(4.0F, 1.0F, -5.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F))
				.uv(36, 37).cuboid(-5.0F, 1.0F, -5.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(40, 2).cuboid(-4.0F, 1.0F, 4.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(36, 39).cuboid(1.0F, 1.0F, 4.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(20, 34).cuboid(-4.0F, -1.0F, -5.0F, 9.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 0.0F));

		ModelPartData ash = modelPartData.addChild("ash", ModelPartBuilder.create().uv(0, 11).cuboid(-4.0F, 0.1F, -4.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	public void setAshScale(float scaleY) {
//		this.ash.scale(new Vector3f(0.0F, scaleY, 0.0F));
		this.ash.yScale = scaleY;
	}
}