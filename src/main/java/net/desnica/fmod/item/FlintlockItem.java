package net.desnica.fmod.item;

import net.desnica.fmod.entity.CopperProjectileEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import java.util.function.Predicate;

public class FlintlockItem extends RangedWeaponItem {

    public FlintlockItem(Settings settings) {
        super(settings);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            int useTime = this.getMaxUseTime(stack) - remainingUseTicks;
            float pullProgress = getPullProgress(useTime);
            if (pullProgress < 0.1f) return;

            ItemStack ammo = player.getProjectileType(stack);
            if (!ammo.isEmpty() || player.getAbilities().creativeMode) {
                if (!world.isClient) {
                    CopperProjectileEntity projectile = new CopperProjectileEntity(world, player);
                    projectile.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, pullProgress * 3.0f, 1.0f);

                    if (pullProgress >= 1.0f) {
                        projectile.setCritical(true);
                    }
                    projectile.setDamage(projectile.getDamage() + pullProgress * 3.0);
                    world.spawnEntity(projectile);
                }

                world.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH,
                        SoundCategory.PLAYERS,
                        1.0f,
                        0.8f / (world.getRandom().nextFloat() * 0.4f + 0.8f)
                );

                player.incrementStat(Stats.USED.getOrCreateStat(this));
                if (!player.getAbilities().creativeMode) {
                    ammo.decrement(1);
                    if (ammo.isEmpty()) {
                        player.getInventory().removeOne(ammo);
                    }
                }
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        boolean hasAmmo = !user.getProjectileType(stack).isEmpty() || user.getAbilities().creativeMode;

        if (hasAmmo) {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(stack);
        }
        return TypedActionResult.fail(stack);
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return stack -> stack.getItem() == ModItems.COPPER_BULLET;
    }

    @Override
    public int getRange() {
        return 30;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    private static float getPullProgress(int useTicks) {
        float progress = (float) useTicks / 5.0f;
        progress = (progress * progress + progress * 2.0f) / 3.0f;
        return Math.min(progress, 1.0f);
    }
}