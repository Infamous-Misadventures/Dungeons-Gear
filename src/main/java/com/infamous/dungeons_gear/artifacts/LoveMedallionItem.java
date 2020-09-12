package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.armor.BattleRobeItem;
import com.infamous.dungeons_gear.armor.EvocationRobeItem;
import com.infamous.dungeons_gear.armor.GuardsArmorItem;
import com.infamous.dungeons_gear.goals.LoverHurtByTargetGoal;
import com.infamous.dungeons_gear.goals.LoverHurtTargetGoal;
import com.infamous.dungeons_gear.interfaces.IArtifact;
import com.infamous.dungeons_gear.items.ArtifactList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_gear.utilties.AbilityUtils.isPetOfAttacker;

public class LoveMedallionItem extends Item implements IArtifact {
    public LoveMedallionItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        World world = playerIn.getEntityWorld();

        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(playerIn.getPosX() - 5, playerIn.getPosY() - 5, playerIn.getPosZ() - 5,
                playerIn.getPosX() + 5, playerIn.getPosY() + 5, playerIn.getPosZ() + 5), (nearbyEntity) -> {
            return nearbyEntity instanceof MonsterEntity && !isPetOfAttacker(playerIn, nearbyEntity) && nearbyEntity.isAlive() && nearbyEntity.isNonBoss();
        });

        int maxLovers = 3;
        int loverCount = 0;
        for(LivingEntity nearbyEntity : nearbyEntities){
            if(nearbyEntity instanceof MonsterEntity){
                MonsterEntity mobEntity = (MonsterEntity) nearbyEntity;
                PROXY.spawnParticles(nearbyEntity, ParticleTypes.HEART);
                    mobEntity.targetSelector.addGoal(0, new LoverHurtByTargetGoal(mobEntity, playerIn));
                    mobEntity.targetSelector.addGoal(1, new LoverHurtTargetGoal(mobEntity, playerIn));
                    loverCount++;
                    if(loverCount == maxLovers) break;
            }
        }

        if(!playerIn.isCreative()){
            itemstack.damageItem(1, playerIn, (entity) -> {
                entity.sendBreakAnimation(handIn);
            });
        }



        setArtifactCooldown(playerIn, itemstack.getItem(), 600);
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    public Rarity getRarity(ItemStack itemStack){
        return Rarity.RARE;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == ArtifactList.LOVE_MEDALLION){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "A spell radiates from this trinket, enchanting those nearby into a trance where they must protect the holder of the medallion at all costs."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Turn up to three hostile mobs into allies for ten seconds before they disappear."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "30 Seconds Cooldown"));
        }
    }
}
