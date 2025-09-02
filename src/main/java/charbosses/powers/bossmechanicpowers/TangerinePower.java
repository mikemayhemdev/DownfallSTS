package charbosses.powers.bossmechanicpowers;

import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import downfall.downfallMod;
import hermit.util.TextureLoader;

public class TangerinePower extends AbstractPower {
    public static final String POWER_ID = "downfall:TangerinePower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;

    private static final Texture tex84 = TextureLoader.getTexture("expansioncontentResources/images/powers/TangerinePower84.png");
    private static final Texture tex32 = TextureLoader.getTexture("expansioncontentResources/images/powers/TangerinePower32.png");

    public TangerinePower(AbstractCreature owner, final int _amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = _amount;
        this.updateDescription();
        this.type = PowerType.BUFF;

        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    @Override
    public void atEndOfRound() {
        if(this.owner.currentBlock > 0 && !this.owner.hasPower(BlurPower.POWER_ID)){
            addToBot(new LoseBlockAction(this.owner, this.owner, this.owner.currentBlock));
        }
    }

    public void onDeath()
    {
        if (AbstractCharBoss.boss != null) {
            if (AbstractCharBoss.boss.getPower(HermitConcentrationPower.POWER_ID).amount > 0)
            {
                AbstractCharBoss.boss.getPower(HermitConcentrationPower.POWER_ID).stackPower(AbstractCharBoss.boss.getPower(HermitConcentrationPower.POWER_ID).amount * -1);
            }

            addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));
            addToBot(new VFXAction(AbstractCharBoss.boss, new ShockWaveEffect(AbstractCharBoss.boss.hb.cX, AbstractCharBoss.boss.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));
            addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new StrengthPower(AbstractCharBoss.boss, amount), amount));
        }
    }

    public void updateDescription() {
        if (downfallMod.useLegacyBosses || AbstractDungeon.ascensionLevel >= 19) {
            this.description = DESC[0] + amount + DESC[1];
        } else {
            this.description = DESC[2];
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}