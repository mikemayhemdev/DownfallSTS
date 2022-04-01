/*
package downfall.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.downfallMod;
import downfall.monsters.NeowBossFinal;
import downfall.util.TextureLoader;

public class NeowStayAt1HpPower extends AbstractPower {
    public static final String POWER_ID = downfallMod.makeID("NeowStayAt1Hp");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowRez84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowRez32.png"));

    private boolean firstTurn;

    public NeowStayAt1HpPower(final AbstractCreature owner, int stack) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.amount = stack;
        this.name = NAME;

        this.updateDescription();

        firstTurn = true;
        this.canGoNegative = false;
    }

    private boolean hasTriggeredThisTurn = false;

    @Override
    public void atStartOfTurn() {
        hasTriggeredThisTurn = false;
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (owner.currentHealth - damageAmount < 1) {
            if (owner instanceof NeowBossFinal && !hasTriggeredThisTurn) {
                hasTriggeredThisTurn = true;
                ((NeowBossFinal)owner).setIntentToSleepEtc();
            }
            flash();
            return owner.currentHealth - 1;
        }
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}

 */