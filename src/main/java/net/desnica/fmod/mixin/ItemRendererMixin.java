package net.desnica.fmod.mixin;

import net.desnica.fmod.Fmod;
import net.desnica.fmod.item.ModItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useCustomModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.FIXED) {
            if (stack.isOf(ModItems.STEEL_SWORD)) {

                return ((ItemRendererAccessor) this).fmod$getModels().getModelManager().getModel(new ModelIdentifier(Fmod.MOD_ID, "steel_sword_3d", "inventory"));
            }
            if (stack.isOf(ModItems.FLINTLOCK)) {

                return ((ItemRendererAccessor) this).fmod$getModels().getModelManager().getModel(new ModelIdentifier(Fmod.MOD_ID, "flintlock", "inventory"));
            }
            if (stack.isOf(ModItems.KNIFE)) {

                return ((ItemRendererAccessor) this).fmod$getModels().getModelManager().getModel(new ModelIdentifier(Fmod.MOD_ID, "knife_3d", "inventory"));
            }
            if (stack.isOf(ModItems.POISONED_KNIFE)) {

                return ((ItemRendererAccessor) this).fmod$getModels().getModelManager().getModel(new ModelIdentifier(Fmod.MOD_ID, "poisoned_knife_3d", "inventory"));
            }
            if (stack.isOf(ModItems.WITHERED_KNIFE)) {

                return ((ItemRendererAccessor) this).fmod$getModels().getModelManager().getModel(new ModelIdentifier(Fmod.MOD_ID, "withered_knife_3d", "inventory"));
            }
        }
        return value;
    }
}