package theHexaghost.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import theHexaghost.HexaMod;
import theHexaghost.util.TextureLoader;

public class GiftsFromTheDeadPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = HexaMod.makeID("GiftsFromTheDeadPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/GiftsFromTheDead84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/GiftsFromTheDead32.png");

    public GiftsFromTheDeadPower(final int amount) {
        this.name = "Ghostly Gifts";
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (card.isEthereal) {
            addToBot(new ApplyPowerAction(owner, owner, new EnergizedBluePower(owner, amount), amount));
        }
    }

    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Whenever an #yEthereal card is #yExhausted, gain ");

        for (int i = 0; i < this.amount; ++i) {
            sb.append("[E] ");
        }
        sb.append(" next turn.");
        this.description = sb.toString();
    }

    @Override
    public AbstractPower makeCopy() {
        return new GiftsFromTheDeadPower(amount);
    }
}