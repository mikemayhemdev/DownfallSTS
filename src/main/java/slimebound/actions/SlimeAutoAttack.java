package slimebound.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.orbs.SpawnedSlime;
import slimebound.powers.SlimedPower;
import slimebound.vfx.SlimeIntentEffect;


public class SlimeAutoAttack extends AbstractGameAction {

    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.2F;
    private AbstractCreature owner;
    private int damage;
    private int debuffamount;
    private int block;
    private AbstractPower p;
    private AttackEffect AE;
    private SpawnedSlime slime;
    private boolean beamVFX;
    private boolean CultistBuff;
    private boolean appliesPoison;
    private boolean appliesSlimed;
    private boolean appliesWeak;
    private boolean hitsAll;


    public SlimeAutoAttack(AbstractCreature player, Integer damage, AttackEffect AE, SpawnedSlime slime, boolean appliesPoison, boolean appliesSlimed, boolean appliesWeak, Integer debuffamount, boolean beamVFX, int block, boolean CultistBuff) {

        this.owner = player;
        this.actionType = ActionType.POWER;
        this.attackEffect = AttackEffect.POISON;
        this.duration = 0.01F;
        this.debuffamount = debuffamount;
        this.AE = AE;
        this.damage = damage;
        this.slime = slime;
        this.block = block;
        this.beamVFX = beamVFX;
        this.CultistBuff = CultistBuff;
        this.appliesPoison = appliesPoison;
        this.appliesSlimed = appliesSlimed;
        this.appliesWeak = appliesWeak;
        this.hitsAll = false;

    }

    public SlimeAutoAttack(AbstractCreature player, Integer damage, AttackEffect AE, SpawnedSlime slime, boolean appliesPoison, boolean appliesSlimed, boolean appliesWeak, Integer debuffamount, boolean beamVFX, int block, boolean CultistBuff, boolean hitsall) {

        this.owner = player;
        this.actionType = ActionType.POWER;
        this.attackEffect = AttackEffect.POISON;
        this.duration = 0.01F;
        this.debuffamount = debuffamount;
        this.AE = AE;
        this.damage = damage;
        this.slime = slime;
        this.block = block;
        this.beamVFX = beamVFX;
        this.CultistBuff = CultistBuff;
        this.appliesPoison = appliesPoison;
        this.appliesSlimed = appliesSlimed;
        this.appliesWeak = appliesWeak;
        this.hitsAll = hitsall;

    }

    public void update() {

        logger.info("Starting auto attack");
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
            return;
        }
        logger.info("Finding target");
        float speedTime = 0.2F / (float) AbstractDungeon.player.orbs.size();

        if (Settings.FAST_MODE) {

            speedTime = 0.10F;

        }
        AbstractCreature mo = AbstractDungeon.getMonsters().getRandomMonster(true);
        if (mo != null) {
            if (this.CultistBuff) {
                CardCrawlGame.sound.playA("VO_CULTIST_1A", .3f);
                AbstractDungeon.actionManager.addToTop(new SlimeAutoCultistBuff(1, this.slime));

            }

            if (this.block > 0)
                AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));

            if (this.hitsAll) {
                AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(this.damage, true, true), DamageInfo.DamageType.THORNS, AttackEffect.POISON));

            } else {
                AbstractDungeon.actionManager.addToTop(new DamageAction(mo,
                        new DamageInfo(AbstractDungeon.player, this.damage, DamageInfo.DamageType.THORNS),
                        AE));
            }

            if (this.appliesPoison)
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new PoisonPower(mo, AbstractDungeon.player, this.debuffamount), this.debuffamount, true, AbstractGameAction.AttackEffect.POISON));
            if (this.appliesSlimed)
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new SlimedPower(mo, AbstractDungeon.player, this.debuffamount), this.debuffamount, true, AttackEffect.NONE));
            if (this.appliesWeak)
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, this.debuffamount, false), this.debuffamount, true, AbstractGameAction.AttackEffect.NONE));

            AbstractDungeon.actionManager.addToTop(new VFXAction(new SlimeIntentEffect(slime.intentImage, slime, speedTime), speedTime));
            if (slime.movesToAttack) {
                //.actionManager.addToTop(new VFXAction(new SlimeIntentMovementEffect(slime, speedTime), speedTime));
                slime.useFastAttackAnimation();

            }
            if (this.beamVFX) {
                CardCrawlGame.sound.playA("ATTACK_MAGIC_BEAM_SHORT", -.2f);
                AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(slime.cX + 3 * Settings.scale, slime.cY + 22 * Settings.scale, mo.hb.cX, mo.hb.cY)));
            }

            //logger.info("Targetng " + mo.name);

        }


        this.isDone = true;
    }
}

