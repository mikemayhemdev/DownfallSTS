package sneckomod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sneckomod.SneckoMod;
import downfall.util.TextureLoader;

import java.util.HashSet;
import java.util.Set;

import static hermit.util.Wiz.applyToSelf;

public class SerpentMindPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = SneckoMod.makeID("SerpentMindPower");

    private static final Texture tex84 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/Exotic84.png");
    private static final Texture tex32 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/Exotic32.png");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public int getUniqueSuitsPlayedThisTurn() {
        Set<AbstractCard.CardColor> uniqueColors = new HashSet<>(); // another one of these

        for (AbstractCard card : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            // they still don't count because I said so. Sandtag convinced me that status and curse should count.
            if (
                //(card.type != AbstractCard.CardType.STATUS &&
                //        card.type != AbstractCard.CardType.CURSE &&
                    !(card.color == AbstractCard.CardColor.COLORLESS && card.rarity == AbstractCard.CardRarity.SPECIAL)) {

                uniqueColors.add(card.color);
            }
        }

        return uniqueColors.size(); // number colors played per turn hopefully
    }

    public SerpentMindPower(final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        applyToSelf(new StrengthPower(owner, amount*getUniqueSuitsPlayedThisTurn())); // wow boring nerf really cool (it's not cool)
    }


    @Override
    public void updateDescription() {
        if (amount == 1)
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        else
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SerpentMindPower(amount);
    }
}