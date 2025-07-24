package net.desnica.fmod;

import net.desnica.fmod.entity.CopperProjectileEntity;
import net.desnica.fmod.entity.ModEntities;
import net.desnica.fmod.screen.BFurnaceScreen;
import net.desnica.fmod.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class FmodClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(ModEntities.COPPER_PROJECTILE, CustomProjectileRenderer::new);


        HandledScreens.register(ModScreenHandlers.BFURNACE_SCREEN_HANDLER, BFurnaceScreen::new);
    }

    private static class CustomProjectileRenderer extends EntityRenderer<CopperProjectileEntity> {
        private final ItemRenderer itemRenderer;

        public CustomProjectileRenderer(EntityRendererFactory.Context context) {
            super(context);
            this.itemRenderer = context.getItemRenderer();
        }

        @Override
        public void render(CopperProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
            matrices.push();

            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yaw + 90.0F));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(entity.getPitch()));

            matrices.scale(0.5F, 0.5F, 0.5F);

            ItemStack stack = entity.asItemStack();
            itemRenderer.renderItem(stack, ModelTransformationMode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), entity.getId());
            matrices.pop();
        }

        @Override
        public Identifier getTexture(CopperProjectileEntity entity) {
            return new Identifier(Fmod.MOD_ID, "textures/item/copper_bullet");
        }
    }
}