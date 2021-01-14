package downfall.powers.neowpowers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;import downfall.downfallMod; import charbosses.powers.bossmechanicpowers.AbstractBossMechanicPower;
import theHexaghost.util.TextureLoader;

public class UnbridledRage extends AbstractBossMechanicPower {
    public static final String POWER_ID = downfallMod.makeID("NeowUnbridledRage");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowWatcher184.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowWatcher132.png"));
    private boolean active;

    public UnbridledRage(final AbstractCreature owner) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.name = NAME;

        this.updateDescription();

        active = false;
        this.canGoNegative = false;
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (!active) {
            if (owner.currentHealth <= owner.maxHealth / 2) {
                active = true;
                addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 6), 6));
            }
        }
        return super.onLoseHp(damageAmount);
    }

    @Override
    public int onHeal(int healAmount) {
        if (active) {
            if (owner.currentHealth > owner.maxHealth / 2) {
                active = false;
                addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -6), -6));
            }
        }
        return super.onHeal(healAmount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}