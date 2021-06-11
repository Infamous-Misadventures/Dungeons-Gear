package com.infamous.dungeons_gear.enchantments.melee;

import com.infamous.dungeons_gear.capabilities.summoning.ISummonable;
import com.infamous.dungeons_gear.capabilities.summoning.ISummoner;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.goals.BeeFollowOwnerGoal;
import com.infamous.dungeons_gear.goals.BeeOwnerHurtByTargetGoal;
import com.infamous.dungeons_gear.goals.BeeOwnerHurtTargetGoal;
import com.infamous.dungeons_gear.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
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
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class BusyBeeEnchantment extends DungeonsEnchantment {

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
                boolean uniqueWeaponFlag = hasBusyBeeBuiltIn(mainhand);
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

    private static boolean hasBusyBeeBuiltIn(ItemStack mainhand) {
        return mainhand.getItem() instanceof IMeleeWeapon && ((IMeleeWeapon) mainhand.getItem()).hasBusyBeeBuiltIn(mainhand);
    }


    private static void createBusyBee(LivingEntity victim, PlayerEntity playerEntity) {
        ISummoner summonerCap = CapabilityHelper.getSummonerCapability(playerEntity);
        BeeEntity beeEntity = EntityType.BEE.create(playerEntity.world);
        if (beeEntity!= null && summonerCap != null) {
            ISummonable summonable = CapabilityHelper.getSummonableCapability(beeEntity);
            if(summonable != null && summonerCap.addBusyBeeBee(beeEntity.getUniqueID())){

                summonable.setSummoner(playerEntity.getUniqueID());

                createBee(victim, playerEntity, beeEntity);
            }
            else {
                beeEntity.remove();
            }
        }
    }

    private static void createBee(LivingEntity victim, PlayerEntity playerEntity, BeeEntity beeEntity) {
        beeEntity.setLocationAndAngles((double)victim.getPosX() + 0.5D, (double)victim.getPosY() + 0.05D, (double)victim.getPosZ() + 0.5D, 0.0F, 0.0F);

        beeEntity.goalSelector.addGoal(2, new BeeFollowOwnerGoal(beeEntity, 2.1D, 10.0F, 2.0F, false));

        beeEntity.targetSelector.addGoal(1, new BeeOwnerHurtByTargetGoal(beeEntity));
        beeEntity.targetSelector.addGoal(2, new BeeOwnerHurtTargetGoal(beeEntity));
        beeEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(beeEntity, LivingEntity.class, 5, false, false,
                (entityIterator) -> entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity)));

        SoundHelper.playCreatureSound(playerEntity, SoundEvents.ENTITY_BEE_LOOP);
        playerEntity.world.addEntity(beeEntity);
    }
}
