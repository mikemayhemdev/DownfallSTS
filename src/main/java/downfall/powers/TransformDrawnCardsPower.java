package downfall.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.city.Snecko;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.downfallMod;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;
import downfall.util.TextureLoader;

public class TransformDrawnCardsPower extends TwoAmountPower implements CloneablePowerInterface {

    public static final String POWER_ID = downfallMod.makeID("TransformDrawnCardsPower");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/TransformDrawnCards84.png");
    private static final Texture tex32 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/TransformDrawnCards32.png");

    public TransformDrawnCardsPower(final AbstractCreature owner, final int amount) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.name = NAME;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.amount2 = this.amount;

        this.updateDescription();
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (amount2 > 0) {
            flash();
            this.amount2 -= 1;
            addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            addToBot(new MakeTempCardInHandAction(AbstractDungeon.returnTrulyRandomCardInCombat()));
            this.updateDescription();
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + Math.max(0, amount2) + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new TransformDrawnCardsPower(owner, amount);
    }
}