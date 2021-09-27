package com.infamous.dungeons_gear.goals;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.GameRules;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public class MappedHurtByTargetGoal extends TargetGoal {
    private static final EntityPredicate HURT_BY_TARGETING = (new EntityPredicate()).allowUnseeable().ignoreInvisibilityTesting();
    private boolean entityCallsForHelp;
    /** Store the previous revengeTimer value */
    private int revengeTimerOld;
    private final Class<?>[] excludedReinforcementTypes;
    private Class<?>[] toIgnoreAlert;

    public MappedHurtByTargetGoal(CreatureEntity creatureIn, Class<?>... classes) {
        super(creatureIn, true);
        this.excludedReinforcementTypes = classes;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        int i = this.mob.getLastHurtByMobTimestamp();
        LivingEntity livingentity = this.mob.getLastHurtByMob();
        if (i != this.revengeTimerOld && livingentity != null) {
            if (livingentity.getType() == EntityType.PLAYER
                    && this.mob.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                return false;
            } else {
                for(Class<?> oclass : this.excludedReinforcementTypes) {
                    if (oclass.isAssignableFrom(livingentity.getClass())) {
                        return false;
                    }
                }

                return this.canAttack(livingentity, HURT_BY_TARGETING);
            }
        } else {
            return false;
        }
    }

    public MappedHurtByTargetGoal setCallsForHelp(Class<?>... p_220794_1_) {
        this.entityCallsForHelp = true;
        this.toIgnoreAlert = p_220794_1_;
        return this;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.mob.setTarget(this.mob.getLastHurtByMob());
        this.targetMob = this.mob.getTarget();
        this.revengeTimerOld = this.mob.getLastHurtByMobTimestamp();
        this.unseenMemoryTicks = 300;
        if (this.entityCallsForHelp) {
            this.alertOthers();
        }

        super.start();
    }

    protected void alertOthers() {
        double d0 = this.getFollowDistance();
        AxisAlignedBB axisalignedbb = AxisAlignedBB.unitCubeFromLowerCorner(this.mob.position()).inflate(d0, 10.0D, d0);
        List<MobEntity> list = this.mob.level.getLoadedEntitiesOfClass(this.mob.getClass(), axisalignedbb);
        Iterator iterator = list.iterator();

        while(true) {
            MobEntity mobentity;
            while(true) {
                if (!iterator.hasNext()) {
                    return;
                }

                mobentity = (MobEntity)iterator.next();
                if (this.mob != mobentity
                        && mobentity.getTarget() == null
                        && (!(this.mob instanceof TameableEntity)
                            || ((TameableEntity)this.mob).getOwner() == ((TameableEntity)mobentity).getOwner())
                        && !mobentity.isAlliedTo(this.mob.getLastHurtByMob())) {
                    if (this.toIgnoreAlert == null) {
                        break;
                    }

                    boolean flag = false;

                    for(Class<?> oclass : this.toIgnoreAlert) {
                        if (mobentity.getClass() == oclass) {
                            flag = true;
                            break;
                        }
                    }

                    if (!flag) {
                        break;
                    }
                }
            }

            this.setAttackTarget(mobentity, this.mob.getLastHurtByMob());
        }
    }

    protected void setAttackTarget(MobEntity mobIn, LivingEntity targetIn) {
        mobIn.setTarget(targetIn);
    }
}
