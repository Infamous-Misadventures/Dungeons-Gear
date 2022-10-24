package com.infamous.dungeons_gear.entities;

import java.util.EnumSet;

import javax.annotation.Nullable;

import com.infamous.dungeons_gear.ai.goals.ApproachTargetGoal;
import com.infamous.dungeons_gear.ai.goals.LookAtTargetGoal;
import com.infamous.dungeons_gear.registry.ModEntityTypes;
import com.infamous.dungeons_gear.registry.ParticleInit;
import com.infamous.dungeons_gear.registry.SoundEventInit;
import com.infamous.dungeons_gear.utilties.PositionUtils;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SoulWizardEntity extends GolemEntity implements IAnimatable {

	private static final DataParameter<Boolean> DELAYED_FORM = EntityDataManager.defineId(SoulWizardEntity.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> ALIVE_FOR = EntityDataManager.defineId(SoulWizardEntity.class,
			DataSerializers.INT);
	
    public int shootAnimationTick;
    public int shootAnimationLength = 10;
    public int shootAnimationActionPoint = 5;
    public int appearAnimationTick;
    public int appearAnimationLength = 12;
    AnimationFactory factory = new AnimationFactory(this);
    
	public int soundLoopTick;

    public SoulWizardEntity(EntityType<? extends SoulWizardEntity> p_i48555_1_, World p_i48555_2_) {
        super(p_i48555_1_, p_i48555_2_);
        this.moveControl = new FlyingMovementController(this, 20, true);
        this.xpReward = 0;
        this.maxUpStep = 1.0F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new ApproachTargetGoal(this, 5, 1.2D, true));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PlayerEntity.class, 5F, 1.2D, 1.6D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, IronGolemEntity.class, 5F, 1.2D, 1.6D));
        this.goalSelector.addGoal(4, new SoulWizardEntity.ShootAttackGoal(this));
        this.goalSelector.addGoal(5, new LookAtTargetGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, MobEntity.class, 1, false, false, (p_234199_0_) -> {
            return p_234199_0_ instanceof IMob;
         }));
    }
    
    @Override
    public void baseTick() {
    	super.baseTick();
    	this.tickDownAnimTimers();
    	
    	boolean moving = false;
    	if (this.getDeltaMovement().x < -0.05) {
    		moving = true;
    	} else if (this.getDeltaMovement().x <= -0.05) {
    		moving = true;
    	} else if (this.getDeltaMovement().x >= 0.05) {
    		moving = true;
    	} else if (this.getDeltaMovement().z <= -0.05) {
    		moving = true;
    	} else if (this.getDeltaMovement().z >= 0.05) {
    		moving = true;
    	} else {
    		moving = false;
    	}
    	
    	if (moving) {
    		this.level.addParticle(ParticleInit.SOUL_DUST.get(), this.getX(), this.getY(), this.getZ(), 0, 0, 0);
	        if (this.soundLoopTick % 20 == 0) {
	        	this.playSound(SoundEventInit.SOUL_WIZARD_FLY_LOOP.get(), 0.5F, 1.0F);
	        }
    	}
        
        if (!this.level.isClientSide) {
        	
        	if (this.hasDelayedForm()) {
            	this.playSound(SoundEventInit.SOUL_WIZARD_APPEAR.get(), 1.0F, 1.0F);
            	((ServerWorld)level).sendParticles(ParticleInit.SOUL_DUST.get(), this.getX(), this.getY(), this.getZ(), 20, 0.5D, 1.0D, 0.5D, 0.0D);
        		this.appearAnimationTick = this.appearAnimationLength;
        		this.level.broadcastEntityEvent(this, (byte) 4);
        		this.setDelayedForm(false);
        	}
        	
        	this.setAliveFor(this.getAliveFor() + 1);
        	
        	if (this.getAliveFor() >= 800) {
        		this.remove();
        		this.playSound(SoundEventInit.SOUL_WIZARD_PROJECTILE_IMPACT.get(), 1.0F, 0.5F);
        		((ServerWorld)this.level).sendParticles(ParticleInit.SOUL_DUST.get(), this.getX(), this.getY(), this.getZ(), 20, 0.5D, 1.0D, 0.5D, 0.0D);
        	}
        }
    }
    
    @Override
    public void handleEntityEvent(byte p_70103_1_) {
    	if (p_70103_1_ == 4) {
    		this.appearAnimationTick = this.appearAnimationLength;
    	} else if (p_70103_1_ == 11) {
    		this.shootAnimationTick = this.shootAnimationLength;
    	} else {
    		super.handleEntityEvent(p_70103_1_);
    	}
    }
    
    public void tickDownAnimTimers() {

        if (this.shootAnimationTick > 0) {
            this.shootAnimationTick--;
        }

        if (this.appearAnimationTick > 0) {
            this.appearAnimationTick--;
        }
    }
    
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DELAYED_FORM, false);
		this.entityData.define(ALIVE_FOR, 0);
	}
	
	public boolean hasDelayedForm() {
		return this.entityData.get(DELAYED_FORM);
	}

	public void setDelayedForm(boolean setTo) {
		this.entityData.set(DELAYED_FORM, setTo);
	}
	
	public int getAliveFor() {
		return this.entityData.get(ALIVE_FOR);
	}

	public void setAliveFor(int setTo) {
		this.entityData.set(ALIVE_FOR, setTo);
	}

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2, this::predicate));
    }
    
    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
    	
    	boolean moving = false;
    	if (this.getDeltaMovement().x < -0.05) {
    		moving = true;
    	} else if (this.getDeltaMovement().x <= -0.05) {
    		moving = true;
    	} else if (this.getDeltaMovement().x >= 0.05) {
    		moving = true;
    	} else if (this.getDeltaMovement().z <= -0.05) {
    		moving = true;
    	} else if (this.getDeltaMovement().z >= 0.05) {
    		moving = true;
    	} else {
    		moving = false;
    	}
    	
        if (this.appearAnimationTick > 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("soul_wizard_appear", true));
        } else if (this.shootAnimationTick > 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("soul_wizard_attack", true));
        } else if (moving) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("soul_wizard_walk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("soul_wizard_idle", true));
        }
        return PlayState.CONTINUE;
    }
    
    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
    
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.FLYING_SPEED, (double)0.15F).add(Attributes.MOVEMENT_SPEED, (double)0.15F).add(Attributes.ATTACK_DAMAGE, 2.0D).add(Attributes.FOLLOW_RANGE, 15.0D);
     }

     protected PathNavigator createNavigation(World p_218342_) {
    	FlyingPathNavigator flyingpathnavigation = new FlyingPathNavigator(this, p_218342_);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
     }

     public void travel(Vector3d p_218382_) {
        if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {
           if (this.isInWater()) {
              this.moveRelative(0.02F, p_218382_);
              this.move(MoverType.SELF, this.getDeltaMovement());
              this.setDeltaMovement(this.getDeltaMovement().scale((double)0.8F));
           } else if (this.isInLava()) {
              this.moveRelative(0.02F, p_218382_);
              this.move(MoverType.SELF, this.getDeltaMovement());
              this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
           } else {
              this.moveRelative(this.getSpeed(), p_218382_);
              this.move(MoverType.SELF, this.getDeltaMovement());
              this.setDeltaMovement(this.getDeltaMovement().scale((double)0.91F));
           }
        }

        this.calculateEntityAnimation(this, false);
     }

     protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
         return SoundEventInit.SOUL_WIZARD_HURT.get();
     }

     protected SoundEvent getDeathSound() {
         return SoundEventInit.SOUL_WIZARD_PROJECTILE_IMPACT.get();
     }
     
     class ShootAttackGoal extends Goal {
         public SoulWizardEntity mob;
         @Nullable
         public LivingEntity target;

         public ShootAttackGoal(SoulWizardEntity mob) {
             this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
             this.mob = mob;
             this.target = mob.getTarget();
         }

         @Override
         public boolean isInterruptable() {
             return false;
         }

         public boolean requiresUpdateEveryTick() {
             return true;
         }

         @Override
         public boolean canUse() {
             target = mob.getTarget();
             return target != null && mob.distanceTo(target) <= 7.5 && mob.canSee(target) && animationsUseable();
         }

         @Override
         public boolean canContinueToUse() {
             return target != null && !animationsUseable();
         }

         @Override
         public void start() {
             mob.shootAnimationTick = mob.shootAnimationLength;
             mob.level.broadcastEntityEvent(mob, (byte) 11);
         }

         @Override
         public void tick() {
             target = mob.getTarget();

             this.mob.getNavigation().stop();
             this.mob.setDeltaMovement(this.mob.getDeltaMovement().scale(0.5D));

             if (target != null && mob.shootAnimationTick == mob.shootAnimationActionPoint) {
                 Vector3d pos = PositionUtils.getOffsetPos(mob, 0.1, 0.5, 0.1, mob.yBodyRot);
                 double d1 = target.getX() - pos.x;
                 double d2 = target.getY(0.6D) - pos.y;
                 double d3 = target.getZ() - pos.z;
                 SoulWizardOrbEntity soulWizardOrb = new SoulWizardOrbEntity(mob.level, mob, d1, d2, d3);
                 soulWizardOrb.rotateToMatchMovement();
                 soulWizardOrb.moveTo(pos.x, pos.y, pos.z);
                 mob.level.addFreshEntity(soulWizardOrb);
                 mob.playSound(SoundEventInit.SOUL_WIZARD_SHOOT.get(), 1.0F, 1.0F);
             }
         }

         public boolean animationsUseable() {
             return mob.shootAnimationTick <= 0;
         }

     }
}
