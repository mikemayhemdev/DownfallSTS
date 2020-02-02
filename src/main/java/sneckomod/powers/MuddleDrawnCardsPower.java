package sneckomod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;
import theHexaghost.HexaMod;
import theHexaghost.util.TextureLoader;

public class MuddleDrawnCardsPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = SneckoMod.makeID("MuddleDrawnCardsPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Enhance84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Enhance32.png");

    public MuddleDrawnCardsPower(final int amount) {
        this.name = "Cheating";
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;// 39
        if (this.amount == 0) {// 41
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));// 42
        }
    }// 52


    @Override
    public void onCardDraw(AbstractCard card) {
        flash();
        addToTop(new ReducePowerAction(owner, owner, this, 1));
        addToTop(new MuddleAction(card));
    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = "#yMuddle the next card you draw.";
        else
            description = "#yMuddle the next #b" + amount + " cards you draw."  ;
    }

    @Override
    public AbstractPower makeCopy() {
        return new MuddleDrawnCardsPower(amount);
    }
}