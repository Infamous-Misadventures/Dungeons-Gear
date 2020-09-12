package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.armor.BattleRobeItem;
import com.infamous.dungeons_gear.armor.EvocationRobeItem;
import com.infamous.dungeons_gear.armor.GuardsArmorItem;
import com.infamous.dungeons_gear.interfaces.IArtifact;
import com.infamous.dungeons_gear.items.ArtifactList;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.infamous.dungeons_gear.utilties.AbilityUtils.spawnShieldingCloudAtPos;

public class TotemOfShieldingItem extends Item implements IArtifact {
    public TotemOfShieldingItem(Properties properties) {
        super(properties);
    }

    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        World world = itemUseContext.getWorld();
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            ItemStack itemUseContextItem = itemUseContext.getItem();
            PlayerEntity itemUseContextPlayer = itemUseContext.getPlayer();
            Hand itemUseContextHand = itemUseContext.getHand();
            BlockPos itemUseContextPos = itemUseContext.getPos();
            Direction itemUseContextFace = itemUseContext.getFace();
            BlockState blockState = world.getBlockState(itemUseContextPos);

            BlockPos blockPos;
            if (blockState.getCollisionShape(world, itemUseContextPos).isEmpty()) {
                blockPos = itemUseContextPos;
            } else {
                blockPos = itemUseContextPos.offset(itemUseContextFace);
            }
            if(itemUseContextPlayer != null) {

                spawnShieldingCloudAtPos(itemUseContextPlayer, blockPos, 100);
                if(!itemUseContextPlayer.isCreative()){
                    itemUseContextItem.damageItem(1, itemUseContextPlayer, (entity) -> {
                        entity.sendBreakAnimation(itemUseContextHand);
                    });
                }
                setArtifactCooldown(itemUseContextPlayer, itemUseContextItem.getItem(), 400);
            }
        }
        return ActionResultType.CONSUME;
    }

    public Rarity getRarity(ItemStack itemStack){
        return Rarity.RARE;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == ArtifactList.TOTEM_OF_SHIELDING){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "This totem radiates powerful energy that bursts forth as a protective shield around those near it."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "This totem has mystical powers that shield those around it from projectiles."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "5 Seconds Duration"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "20 Seconds Cooldown"));
        }
    }
}
