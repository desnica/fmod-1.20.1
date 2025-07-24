package net.desnica.fmod.entity;

import net.desnica.fmod.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class CopperProjectileEntity extends PersistentProjectileEntity{

    public CopperProjectileEntity(EntityType<? extends CopperProjectileEntity> type, World world) {
        super(type, world);
        this.setDamage(3.0);
        this.pickupType = PickupPermission.ALLOWED;
    }

    public CopperProjectileEntity(World world, LivingEntity owner) {
        super(ModEntities.COPPER_PROJECTILE, owner, world);
        this.setPosition(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
        this.setDamage(3.0);
        this.pickupType = PickupPermission.ALLOWED;
    }

    @Override
    public ItemStack asItemStack() {
        return new ItemStack(ModItems.COPPER_BULLET);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        // No additional data needed
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient && this.inGround && this.inGroundTime > 200) {
            this.discard(); // Despawn after 10 seconds in ground
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient && hitResult.getType() != HitResult.Type.MISS) {
            this.setPierceLevel((byte) 0);
        }
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public boolean isCritical() {
        return super.isCritical();
    }

    @Override
    public boolean isAttackable() {
        return true;
    }

}