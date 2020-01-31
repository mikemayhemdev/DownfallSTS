package guardian.powers;


import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.cards.OrbSlam;
import guardian.vfx.FloatingOrbsEffect;


public class FloatingOrbsPower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:FloatingOrbsPower";
    private static final float yOffset = -70F * Settings.scale;
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    public FloatingOrbsEffect orbVFX;


    public FloatingOrbsPower(AbstractCreature owner) {

        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 1;
        this.setImage("FloatingOrbsPower84.png", "FloatingOrbsPower32.png");
        this.type = POWER_TYPE;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }


    public void updateDescription() {
        if (this.amount > 0) {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];

        } else {
            this.description = DESCRIPTIONS[0];

        }
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        this.orbVFX = new FloatingOrbsEffect(this.owner, this.owner.hb.cX, this.owner.hb.cY + yOffset);
        AbstractDungeon.actionManager.addToBottom(new VFXAction(this.orbVFX));
    }

    public void atStartOfTurn() {

        flash();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new OrbSlam(), this.amount, false));


    }


}



