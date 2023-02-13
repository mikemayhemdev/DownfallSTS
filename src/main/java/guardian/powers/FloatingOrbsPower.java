package guardian.powers;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.cards.OrbSlam;
import guardian.vfx.FloatingOrbsEffect;


public class FloatingOrbsPower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:FloatingOrbsPower";
    private static final float yOffset = -70F * Settings.scale;
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    public FloatingOrbsEffect orbVFX;


    public FloatingOrbsPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.setImage("FloatingOrbsPower84.png", "FloatingOrbsPower32.png");
        this.type = POWER_TYPE;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
        updateDescription();
    }


    public void updateDescription() {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        this.orbVFX = new FloatingOrbsEffect(this.owner, this.owner.hb.cX, this.owner.hb.cY + yOffset);
        addToBot(new VFXAction(this.orbVFX));
    }

    @Override
    public void onAfterCardPlayed(AbstractCard card) {
        if (card.cost == 0 || card.freeToPlayOnce) {
            flash();
            orbVFX.attackAnim();
            addToBot(new DamageRandomEnemyAction(new DamageInfo(owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }
}



