package net.desnica.fmod;

import net.desnica.fmod.item.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class BulletHud {
    public static final Identifier HOTBAR_TEXTURE = Identifier.of(Fmod.MOD_ID, "textures/gui/hotbar_texture.png");
    
    public void renderBullet(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.options.hudHidden || client.player == null || client.world == null) {
            return;
        }

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        int bulletCount = 0;
        ItemStack bulletStack = ItemStack.EMPTY;

        for (int i = 0; i < client.player.getInventory().size(); i++) {
            ItemStack stack = client.player.getInventory().getStack(i);
            if (!stack.isEmpty() && stack.isOf(ModItems.COPPER_BULLET)) {
                bulletCount += stack.getCount();
                bulletStack = stack.copy();
            }
        }
        ItemStack helditem = client.player.getMainHandStack();
        if (bulletCount > 0 && !bulletStack.isEmpty() && helditem.getItem() == ModItems.FLINTLOCK) {
            int iconSize = 22;
            int spacing = 2;
            int bulletX = (screenWidth / 2) + 95;
            int bulletY = screenHeight - 22;

            context.drawTexture(HOTBAR_TEXTURE, bulletX, bulletY, 0, 0, iconSize, iconSize, iconSize, iconSize);
            context.drawItem(bulletStack, bulletX + 2, bulletY + 3);
            

            String countText = String.valueOf(bulletCount);
            context.drawText(client.textRenderer, countText, bulletX + 12, bulletY + 13, 0xFFFFFF, true);
        }
    }
}