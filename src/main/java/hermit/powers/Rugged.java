package hermit.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.HermitMod;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makePowerPath;

public class Rugged extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = HermitMod.makeID("Rugged");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("rugged_p.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("rugged.png"));


    public Rugged(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        priority = 6;

        type = PowerType.BUFF;

        if (this.amount <= 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0) {
            this.flash();
            this.addToTop(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
            return Math.min(damageAmount,2);
        } else {
            return damageAmount;
        }
    }

    @Override
    public void updateDescription() {
        if (amount > 1)
            description = DESCRIPTIONS[0] + " #b" + amount + DESCRIPTIONS[1];
        else
            description = DESCRIPTIONS[0] + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new Rugged(owner, amount);
    }
}
