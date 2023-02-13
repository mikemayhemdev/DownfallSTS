package hermit.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.HermitMod;
import hermit.actions.HandSelectAction;
import hermit.util.TextureLoader;
import hermit.util.Wiz;

public class AdaptPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = HermitMod.makeID("AdaptPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("hermitResources/images/powers/adapt_p.png");
    private static final Texture tex32 = TextureLoader.getTexture("hermitResources/images/powers/adapt.png");

    public AdaptPower(final AbstractCreature _owner, final AbstractCreature _source, final int _amount) {
        name = NAME;
        ID = POWER_ID;

        owner = _owner;
        amount = _amount;
        source = _source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
            Wiz.atb(new HandSelectAction(this.amount, (c) -> true, list -> {
                for (AbstractCard c : list)
                {
                    Wiz.p().hand.moveToExhaustPile(c);
                    AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.owner, this.owner, 8));
                }
                AbstractDungeon.player.hand.refreshHandLayout();
                list.clear();
            }, null, uiStrings.TEXT[0],false,true,true,false));
    }

    @Override
    public void updateDescription() {
            description = (amount < 2) ?  DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] : DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new AdaptPower(owner, source, amount);
    }
}
