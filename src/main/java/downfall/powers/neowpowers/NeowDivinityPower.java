package downfall.powers.neowpowers;
import charbosses.bosses.AbstractCharBoss;
import charbosses.vfx.EnemyDivinityParticleEffect;
import charbosses.vfx.EnemyStanceAuraEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;

public class NeowDivinityPower extends AbstractPower {
    public static final String POWER_ID = "downfall:NeowDivinity";
    private static final PowerStrings powerStrings;
    private static long sfxId = -1L;
    private boolean justapplied;

    protected float angle; protected float particleTimer = 0.0F; protected float particleTimer2 = 0.0F;

    public NeowDivinityPower(AbstractCreature owner) {
        this.name = powerStrings.NAME;
        this.ID = "downfall:NeowDivinity";
        this.owner = owner;
        this.updateDescription();
        this.loadRegion("mantra");
        this.type = PowerType.BUFF;
        justapplied = true;


    }


    @Override
    public void onRemove() {
        super.onRemove();
         CardCrawlGame.sound.stop("STANCE_LOOP_DIVINITY", sfxId);
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        if (!justapplied) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }else{
            justapplied = false;
        }
    }

    public void playApplyPowerSfx() {
         CardCrawlGame.sound.play("STANCE_ENTER_DIVINITY");
         sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_DIVINITY");
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    @Override
    public float atDamageGive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type) {
        if (type == com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL) {
            return damage * 3.0F;
        }
        return damage;
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("downfall:NeowDivinity");
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        if (!com.megacrit.cardcrawl.core.Settings.DISABLE_EFFECTS) {
             this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                    this.particleTimer = 0.2F;
                   AbstractDungeon.effectsQueue.add(new EnemyDivinityParticleEffect());
                 }
           }
          this.particleTimer2 -= Gdx.graphics.getDeltaTime();
           if (this.particleTimer2 < 0.0F) {
                this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
              AbstractDungeon.effectsQueue.add(new EnemyStanceAuraEffect("Divinity"));
             }
    }
}
