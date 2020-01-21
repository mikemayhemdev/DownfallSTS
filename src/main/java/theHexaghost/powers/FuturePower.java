package theHexaghost.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theHexaghost.HexaMod;
import theHexaghost.util.OnAdvanceSubscriber;
import theHexaghost.util.TextureLoader;

public class FuturePower extends TwoAmountPower implements CloneablePowerInterface, OnAdvanceSubscriber {

    public static final String POWER_ID = HexaMod.makeID("FuturePower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Key_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Key_power32.png");

    public FuturePower(final int amount, final int amount2) {
        this.name = "Future";
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.amount2 = amount2;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void onAdvance() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, amount), amount));// 44
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, amount), amount));// 46
        addToBot(new DrawCardAction(AbstractDungeon.player, amount2));
    }

    @Override
    public void updateDescription() {
        if (amount2 == 1)
            description = "Whenever you #yAdvance, gain #b" + amount + " #yMomentary #yStrength and draw #b" + amount2 + " card.";
        else
            description = "Whenever you #yAdvance, gain #b" + amount + " #yMomentary #yStrength and draw #b" + amount2 + " cards.";
    }

    @Override
    public AbstractPower makeCopy() {
        return new FuturePower(amount, amount2);
    }
}