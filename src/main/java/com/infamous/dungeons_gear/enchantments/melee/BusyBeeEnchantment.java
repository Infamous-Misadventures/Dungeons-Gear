package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.capabilities.summoning.ISummonable;
import com.infamous.dungeons_gear.capabilities.summoning.ISummoner;
import com.infamous.dungeons_gear.capabilities.summoning.SummonableProvider;
import com.infamous.dungeons_gear.capabilities.summoning.SummonerProvider;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.goals.BeeFollowOwnerGoal;
import com.infamous.dungeons_gear.goals.BeeOwnerHurtByTargetGoal;
import com.infamous.dungeons_gear.goals.BeeOwnerHurtTargetGoal;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.items.WeaponList.*;

@Mod.EventBusSubscriber(modid= MODID)
public class BusyBeeEnchantment extends Enchantment {

    public BusyBeeEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onBusyBeeKill(LivingDeathEvent event){
        if(event.getSource().getImmediateSource() instanceof AbstractArrowEntity) return;
        if(event.getSource().getTrueSource() instanceof LivingEntity){
            LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
            LivingEntity victim = event.getEntityLiving();
            if(attacker instanceof PlayerEntity){
                PlayerEntity playerEntity = (PlayerEntity)attacker;
                ItemStack mainhand = playerEntity.getHeldItemMainhand();
                boolean uniqueWeaponFlag = mainhand.getItem() == BEE_STINGER;
                if(ModEnchantmentHelper.hasEnchantment(mainhand, MeleeEnchantmentList.BUSY_BEE)){
                    int busyBeeLevel = EnchantmentHelper.getEnchantmentLevel(MeleeEnchantmentList.RAMPAGING, mainhand);
                    float busyBeeRand = playerEntity.getRNG().nextFloat();
                    float busyBeeChance = 0.1F + busyBeeLevel * 0.01F;
                    if(busyBeeRand <= busyBeeChance) {
                        createBusyBee(victim, playerEntity);
                    }
                if(uniqueWeaponFlag){
                    float rampagingRand = playerEntity.getRNG().nextFloat();
                    if(rampagingRand <= 0.2F) {
                        createBusyBee(victim, playerEntity);
                    }
                    }
                }
            }
        }
    }


    private static void createBusyBee(LivingEntity victim, PlayerEntity playerEntity) {
        ISummoner summonerCap = playerEntity.getCapability(SummonerProvider.SUMMONER_CAPABILITY).orElseThrow(IllegalStateException::new);
        BeeEntity beeEntity = EntityType.BEE.create(playerEntity.world);
        if (beeEntity!= null) {
            if(summonerCap.addBusyBeeBee(beeEntity.getUniqueID())){
                beeEntity.setLocationAndAngles((double)victim.getPosX() + 0.5D, (double)victim.getPosY() + 0.05D, (double)victim.getPosZ() + 0.5D, 0.0F, 0.0F);

                beeEntity.goalSelector.addGoal(2, new BeeFollowOwnerGoal(beeEntity, 2.1D, 10.0F, 2.0F, false));

                beeEntity.targetSelector.addGoal(1, new BeeOwnerHurtByTargetGoal(beeEntity));
                beeEntity.targetSelector.addGoal(2, new BeeOwnerHurtTargetGoal(beeEntity));
                beeEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(beeEntity, LivingEntity.class, 5, false, false,
                        (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));

                playerEntity.world.playSound((PlayerEntity)null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_BEE_LOOP, SoundCategory.AMBIENT, 64.0F, 1.0F);
                playerEntity.world.addEntity(beeEntity);

                ISummonable summonable = beeEntity.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);
                summonable.setSummoner(playerEntity.getUniqueID());
            }
            else {
                beeEntity.remove();
            }
        }
    }
}
