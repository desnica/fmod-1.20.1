package net.desnica.fmod.block.entity;

import net.desnica.fmod.block.custom.BFurnaceBlock;
import net.desnica.fmod.item.ModItems;
import net.desnica.fmod.screen.BFurnaceScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BFurnaceBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);

    private static final int INPUT_SLOT_IRON = 0;
    private static final int OUTPUT_SLOT_STEEL = 1;
    private static final int INPUT_SLOT_CHARCOAL = 2;
    private static final int OUTPUT_SLOT_GUNPOWDER = 3;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 150;

    public BFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BFURNACE_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> BFurnaceBlockEntity.this.progress;
                    case 1 -> BFurnaceBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> BFurnaceBlockEntity.this.progress = value;
                    case 1 -> BFurnaceBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("bfurnace.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("bfurnace.progress");
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Burning Furnace");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new BFurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            if (state.get(BFurnaceBlock.LIT)) {
                // Add flame particles at the front of the furnace
                Direction facing = state.get(BFurnaceBlock.FACING);
                double x = pos.getX() + 0.5;
                double y = pos.getY() + 0.5;
                double z = pos.getZ() + 0.5;
                if (facing == Direction.NORTH) z -= 0.5;
                if (facing == Direction.SOUTH) z += 0.5;
                if (facing == Direction.EAST) x += 0.5;
                if (facing == Direction.WEST) x -= 0.5;
                world.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.05, 0.0);
            }
            return;
        }
        boolean wasBurning = state.get(BFurnaceBlock.LIT);
        boolean stateChanged = false;

        if (isOutputSlotsEmptyOrReceivable()) {
            if (this.hasRecipe()) {
                this.increaseCraftProgress();
                stateChanged = true;

                if (!wasBurning) {
                    world.setBlockState(pos, state.with(BFurnaceBlock.LIT, true), 3);
                }

                if (hasCraftingFinished()) {
                    this.craftItem();
                    this.resetProgress();
                }
            } else {
                this.resetProgress();
                if (wasBurning) {
                    world.setBlockState(pos, state.with(BFurnaceBlock.LIT, false), 3);
                    stateChanged = true;
                }
            }
        } else {
            this.resetProgress();
            if (wasBurning) {
                world.setBlockState(pos, state.with(BFurnaceBlock.LIT, false), 3);
                stateChanged = true;
            }
        }

        if (stateChanged) {
            markDirty(world, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void craftItem() {
        this.removeStack(INPUT_SLOT_IRON, 1); // Remove 1 iron ingot
        this.removeStack(INPUT_SLOT_CHARCOAL, 1); // Remove 1 charcoal

        ItemStack steelResult = new ItemStack(ModItems.STEEL_INGOT);
        ItemStack gunpowderResult = new ItemStack(Items.GUNPOWDER);

        this.setStack(OUTPUT_SLOT_STEEL, new ItemStack(
                steelResult.getItem(),
                getStack(OUTPUT_SLOT_STEEL).getCount() + steelResult.getCount()
        ));

        this.setStack(OUTPUT_SLOT_GUNPOWDER, new ItemStack(
                gunpowderResult.getItem(),
                getStack(OUTPUT_SLOT_GUNPOWDER).getCount() + gunpowderResult.getCount()
        ));
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        ItemStack steelResult = new ItemStack(ModItems.STEEL_INGOT);
        ItemStack gunpowderResult = new ItemStack(Items.GUNPOWDER);

        boolean hasIronInput = getStack(INPUT_SLOT_IRON).getItem() == Items.IRON_INGOT;
        boolean hasCharcoalInput = getStack(INPUT_SLOT_CHARCOAL).getItem() == Items.CHARCOAL;

        return hasIronInput &&
                hasCharcoalInput &&
                canInsertAmountIntoOutputSlot(steelResult, OUTPUT_SLOT_STEEL) &&
                canInsertItemIntoOutputSlot(steelResult.getItem(), OUTPUT_SLOT_STEEL) &&
                canInsertAmountIntoOutputSlot(gunpowderResult, OUTPUT_SLOT_GUNPOWDER) &&
                canInsertItemIntoOutputSlot(gunpowderResult.getItem(), OUTPUT_SLOT_GUNPOWDER);
    }

    private boolean canInsertItemIntoOutputSlot(Item item, int slot) {
        return this.getStack(slot).getItem() == item || this.getStack(slot).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result, int slot) {
        return this.getStack(slot).getCount() + result.getCount() <= getStack(slot).getMaxCount();
    }

    private boolean isOutputSlotsEmptyOrReceivable() {
        return (this.getStack(OUTPUT_SLOT_STEEL).isEmpty() ||
                this.getStack(OUTPUT_SLOT_STEEL).getCount() < this.getStack(OUTPUT_SLOT_STEEL).getMaxCount()) &&
                (this.getStack(OUTPUT_SLOT_GUNPOWDER).isEmpty() ||
                        this.getStack(OUTPUT_SLOT_GUNPOWDER).getCount() < this.getStack(OUTPUT_SLOT_GUNPOWDER).getMaxCount());
    }
}