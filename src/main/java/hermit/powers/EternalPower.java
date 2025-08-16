package hermit.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.HermitMod;
import hermit.patches.PreDrawPatch;
import hermit.util.TextureLoader;

public class EternalPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = HermitMod.makeID("EternalPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static int total = 4;

    private static final Texture tex84 = TextureLoader.getTexture("hermitResources/images/powers/power_eternal_p.png");
    private static final Texture tex32 = TextureLoader.getTexture("hermitResources/images/powers/power_eternal.png");

    public EternalPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        total = amount;
        this.amount = amount;
        this.source = source;

        priority = 99;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if(!PreDrawPatch.DRAWN_DURING_TURN && total>0){
            if(card.cost == -2) return;
            total--;

            if (card.costForTurn > 0) {
                card.costForTurn = Math.max(card.costForTurn - this.amount,0);
                card.isCostModifiedForTurn = true;
                card.superFlash(Color.GOLD.cpy());
            }
        }
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new EternalPower(owner, source, amount);
    }
}
