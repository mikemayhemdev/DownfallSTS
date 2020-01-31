package sneckomod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sneckomod.SneckoMod;
import sneckomod.cards.SoulRoll;
import theHexaghost.util.TextureLoader;

public class UnlimitedRollsPower extends AbstractPower implements CloneablePowerInterface, NonStackablePower {

    public static final String POWER_ID = SneckoMod.makeID("UnlimitedRollsPower");

    private static final Texture tex84 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/Enhance84.png");
    private static final Texture tex32 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/Enhance32.png");

    private boolean bruh;

    public UnlimitedRollsPower(boolean upgraded) {
        this.name = "Unlimited Rolls";
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = -1;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        bruh = upgraded;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    public void atStartOfTurnPostDraw() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {// 27
            this.flash();// 28

            AbstractCard card = new SoulRoll();
            if (bruh) card.upgrade();
            card.exhaust = true;
            card.rawDescription = card.rawDescription += " NL Exhaust.";

            this.addToBot(new MakeTempCardInHandAction(card, 1, false));// 30 32 33
        }
    }

    @Override
    public void updateDescription() {
        if (bruh) {
            description = "At the start of your turn, add a #ySoul #yRoll+ into your hand with #yExhaust.";
        } else
            description = "At the start of your turn, add a #ySoul #yRoll into your hand with #yExhaust.";
    }

    @Override
    public AbstractPower makeCopy() {
        return new UnlimitedRollsPower(bruh);
    }
}