package evilWithin.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import evilWithin.EvilWithinMod;
import theHexaghost.HexaMod;
import theHexaghost.util.TextureLoader;

public class TransformDrawnCardsPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = EvilWithinMod.makeID("TransformDrawnCardsPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Enhance84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Enhance32.png");

    public TransformDrawnCardsPower(final AbstractCreature owner, final int amount) {
        this.name = "Transformed!!";
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        this.amount -= 1;
        if (amount >= 0) {
            if (this.amount == 0)
                addToTop(new RemoveSpecificPowerAction(owner, owner, this));
            else {
                flash();
                addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                addToBot(new MakeTempCardInHandAction(AbstractDungeon.returnTrulyRandomCardInCombat()));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = "#yTransform the next #b" + amount + " cards you draw.";
    }

    @Override
    public AbstractPower makeCopy() {
        return new TransformDrawnCardsPower(owner, amount);
    }
}