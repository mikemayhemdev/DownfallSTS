package sneckomod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sneckomod.SneckoMod;
import sneckomod.TheSnecko;
import theHexaghost.HexaMod;
import theHexaghost.util.TextureLoader;

public class SerpentMindPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = SneckoMod.makeID("SerpentMindPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Enhance84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Enhance32.png");

    public SerpentMindPower(final int amount) {
        this.name = "Serpent Mind";
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
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.color != TheSnecko.Enums.SNECKO_CYAN) {
            flash();
            addToBot(new DrawCardAction(amount));
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = "Whenever you play an #yOffclass card, draw #b" + amount + " card.";
        else
            description = "Whenever you play an #yOffclass card, draw #b" + amount + " cards.";
    }

    @Override
    public AbstractPower makeCopy() {
        return new SerpentMindPower(amount);
    }
}