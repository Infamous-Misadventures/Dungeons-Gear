package com.infamous.dungeons_gear.registry;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.enchantments.armor.*;
import com.infamous.dungeons_gear.enchantments.melee_ranged.*;
import com.infamous.dungeons_gear.enchantments.melee.*;
import com.infamous.dungeons_gear.enchantments.ranged.*;
import com.infamous.dungeons_gear.enchantments.types.ModDamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.enchantments.lists.MeleeEnchantmentList.*;
import static com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList.*;
import static com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList.*;
import static com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList.*;
public class EnchantmentInit {
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onEnchantmentsRegistry(final RegistryEvent.Register<Enchantment> effectRegistryEvent) {
            effectRegistryEvent.getRegistry().registerAll(
                    ANIMA_CONDUIT = new AnimaConduitEnchantment().setRegistryName(location("anima_conduit")),
                    ENIGMA_RESONATOR = new EnigmaResonatorEnchantment().setRegistryName(location("enigma_resonator")),
                    FREEZING = new FreezingEnchantment().setRegistryName(location("freezing")),
                    GRAVITY = new GravityEnchantment().setRegistryName(location("gravity")),
                    POISON_CLOUD = new PoisonCloudEnchantment().setRegistryName(location("poison_cloud")),
                    DYNAMO = new DynamoEnchantment().setRegistryName(location("dynamo")),

                    ARTIFACT_SYNERGY = new ArtifactSynergyEnchantment().setRegistryName(location("artifact_synergy")),
                    BUSY_BEE = new BusyBeeEnchantment().setRegistryName(location("busy_bee")),
                    CHAINS = new ChainsEnchantment().setRegistryName(location("chains")),
                    COMMITTED = new CommittedEnchantment().setRegistryName(location("committed")),
                    CRITICAL_HIT = new CriticalHitEnchantment().setRegistryName(location("critical_hit")),
                    ECHO = new EchoEnchantment().setRegistryName(location("echo")),
                    EXPLODING = new ExplodingEnchantment().setRegistryName(location("exploding")),
                    GUARDING_STRIKE = new GuardingStrikeEnchantment().setRegistryName(location("guarding_strike")),
                    ILLAGERS_BANE = new ModDamageEnchantment(Enchantment.Rarity.UNCOMMON, 3, EquipmentSlotType.MAINHAND).setRegistryName(location("illagers_bane")),
                    LEECHING = new LeechingEnchantment().setRegistryName(location("leeching")),
                    PAIN_CYCLE = new PainCycleEnchantment().setRegistryName(location("pain_cycle")),
                    PROSPECTOR = new ProspectorEnchantment().setRegistryName(location("prospector")),
                    RADIANCE = new RadianceEnchantment().setRegistryName(location("radiance")),
                    RAMPAGING = new RampagingEnchantment().setRegistryName(location("rampaging")),
                    RUSHDOWN = new RushdownEnchantment().setRegistryName(location("rushdown")),
                    SHOCKWAVE = new ShockwaveEnchantment().setRegistryName(location("shockwave")),
                    SOUL_SIPHON = new SoulSiphonEnchantment().setRegistryName(location("soul_siphon")),
                    STUNNING = new StunningEnchantment().setRegistryName(location("stunning")),
                    SWIRLING = new SwirlingEnchantment().setRegistryName(location("swirling")),
                    THUNDERING = new ThunderingEnchantment().setRegistryName(location("thundering")),
                    WEAKENING = new WeakeningEnchantment().setRegistryName(location("weakening")),

                    ACCELERATE = new AccelerateEnchantment().setRegistryName(location("accelerate")),
                    BONUS_SHOT = new BonusShotEnchantment().setRegistryName(location("bonus_shot")),
                    BURST_BOWSTRING = new BurstBowstringEnchantment().setRegistryName(location("burst_bowstring")),
                    CHAIN_REACTION = new ChainReactionEnchantment().setRegistryName(location("chain_reaction")),
                    COOLDOWN_SHOT = new CooldownShotEnchantment().setRegistryName(location("cooldown_shot")),
                    FUSE_SHOT = new FuseShotEnchantment().setRegistryName(location("fuse_shot")),
                    GROWING = new GrowingEnchantment().setRegistryName(location("growing")),
                    REPLENISH = new ReplenishEnchantment().setRegistryName(location("replenish")),
                    RADIANCE_SHOT = new RadianceShotEnchantment().setRegistryName(location("radiance_shot")),
                    RICOCHET = new RicochetEnchantment().setRegistryName(location("ricochet")),
                    SUPERCHARGE = new SuperchargeEnchantment().setRegistryName(location("supercharge")),
                    TEMPO_THEFT = new TempoTheftEnchantment().setRegistryName(location("tempo_theft")),
                    //UNCHANTING = new UnchantingEnchantment().setRegistryName(location("unchanting")),
                    WILD_RAGE = new WildRageEnchantment().setRegistryName(location("wild_rage")),


                    ALTRUISTIC = new AltruisticEnchantment().setRegistryName(location("altruistic")),
                    BAG_OF_SOULS = new BagOfSoulsEnchantment().setRegistryName(location("bag_of_souls")),
                    BURNING = new BurningEnchantment().setRegistryName(location("burning")),
                    BEAST_BOSS = new BeastBossEnchantment().setRegistryName(location("beast_boss")),
                    BEAST_BURST = new BeastBurstEnchantment().setRegistryName(location("beast_burst")),
                    BEAST_SURGE = new BeastSurgeEnchantment().setRegistryName(location("beast_surge")),
                    CHILLING = new ChillingEnchantment().setRegistryName(location("chilling")),
                    COOLDOWN = new CooldownEnchantment().setRegistryName(location("cooldown")),
                    COWARDICE = new CowardiceEnchantment().setRegistryName(location("cowardice")),
                    DEATH_BARTER = new DeathBarterEnchantment().setRegistryName(location("death_barter")),
                    DEFLECT = new DeflectEnchantment().setRegistryName(location("deflect")),
                    ELECTRIFIED = new ElectrifiedEnchantment().setRegistryName(location("electrified")),
                    EXPLORER = new ExplorerEnchantment().setRegistryName(location("explorer")),
                    FINAL_SHOUT = new FinalShoutEnchantment().setRegistryName(location("final_shout")),
                    FIRE_FOCUS = new FireFocusEnchantment().setRegistryName(location("fire_focus")),
                    FIRE_TRAIL = new FireTrailEnchantment().setRegistryName(location("fire_trail")),
                    FOOD_RESERVES = new FoodReservesEnchantment().setRegistryName(location("food_reserves")),
                    FORTUNE_OF_THE_SEA = new FortuneOfTheSeaEnchantment().setRegistryName(location("fortune_of_the_sea")),
                    FRENZIED = new FrenziedEnchantment().setRegistryName(location("frenzied")),
                    GRAVITY_PULSE = new GravityPulseEnchantment().setRegistryName(location("gravity_pulse")),
                    HEALTH_SYNERGY = new HealthSynergyEnchantment().setRegistryName(location("health_synergy")),
                    LIGHTNING_FOCUS = new LightningFocusEnchantment().setRegistryName(location("lightning_focus")),
                    LUCKY_EXPLORER = new LuckyExplorerEnchantment().setRegistryName(location("lucky_explorer")),
                    POISON_FOCUS = new PoisonFocusEnchantment().setRegistryName(location("poison_focus")),
                    POTION_BARRIER = new PotionBarrierEnchantment().setRegistryName(location("potion_barrier")),
                    RECKLESS = new RecklessEnchantment().setRegistryName(location("reckless")),
                    RECYCLER = new RecyclerEnchantment().setRegistryName(location("recycler")),
                    RUSH = new RushEnchantment().setRegistryName(location("rush")),
                    SOUL_FOCUS = new SoulFocusEnchantment().setRegistryName(location("soul_focus")),
                    SNOWBALL = new SnowballEnchantment().setRegistryName(location("snowball")),
                    SPEED_SYNERGY = new SpeedSynergyEnchantment().setRegistryName(location("speed_synergy")),
                    SURPRISE_GIFT = new SurpriseGiftEnchantment().setRegistryName(location("surprise_gift")),
                    SWIFTFOOTED = new SwiftfootedEnchantment().setRegistryName(location("swiftfooted")),
                    TUMBLEBEE = new TumblebeeEnchantment().setRegistryName(location("tumblebee"))
                    );

            putImpactRangedEnchantmentsInMap();
        }

        private static void putImpactRangedEnchantmentsInMap() {
            //rangedEnchantmentMap.put(ACCELERATE, "Accelerate");
            //rangedEnchantmentToStringMap.put(BONUS_SHOT, "BonusShot");
            rangedEnchantmentToStringMap.put(CHAIN_REACTION, "ChainReaction");
            //rangedEnchantmentMap.put(FUSE_SHOT, "FuseShot");
            rangedEnchantmentToStringMap.put(GROWING, "Growing");
            rangedEnchantmentToStringMap.put(REPLENISH, "Replenish");
            rangedEnchantmentToStringMap.put(RADIANCE_SHOT, "Radiance");
            //rangedEnchantmentTagMap.put(RAPID_FIRE, "RapidFire");
            rangedEnchantmentToStringMap.put(RICOCHET, "Ricochet");
            rangedEnchantmentToStringMap.put(SUPERCHARGE, "Supercharge");
            rangedEnchantmentToStringMap.put(TEMPO_THEFT, "TempoTheft");
            //rangedEnchantmentMap.put(UNCHANTING, UNCHANTING.getRegistryName());
            rangedEnchantmentToStringMap.put(ANIMA_CONDUIT, "AnimaConduit");
            rangedEnchantmentToStringMap.put(ENIGMA_RESONATOR, "EnigmaResonator");
            rangedEnchantmentToStringMap.put(GRAVITY, "Gravity");
            rangedEnchantmentToStringMap.put(POISON_CLOUD, "PoisonCloud");
        }
        private static ResourceLocation location(String name) {
            return new ResourceLocation(DungeonsGear.MODID, name);
        }
    }
}
