package sneckomod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.AbstractDynamicCard;
import sneckomod.SneckoMod;
import sneckomod.SneckoMod;
import downfall.util.TextureLoader;
import sneckomod.cards.TyphoonFang;
import sneckomod.relics.D8;

public class BlunderGuardPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = SneckoMod.makeID("BlunderGuardPower");

    private static final Texture tex84 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/BurnVenom84.png");
    private static final Texture tex32 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/BurnVenom32.png");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BlunderGuardPower(final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        this.loadRegion("deva");

        this.updateDescription();
    }

    public boolean isOverflowActive(AbstractCard source) {
        boolean OVERFLOW = false; // Reset overflow state

        // Only check for overflow if the card has the OVERFLOW tag
        if (source.hasTag(SneckoMod.OVERFLOW)) {
            // Check if there are more than 5 cards in hand
            if (AbstractDungeon.player.hand.size() > 5 || (AbstractDungeon.player.hasPower(CheatPower.POWER_ID))) {
                OVERFLOW = true;
            }

            // If the card purges on use, immediately return false
            if ((source instanceof TyphoonFang && source.purgeOnUse)) {
                return false; // If the card purges on use, it cannot cause overflow
            }

            // Check for the D8 relic
            if (AbstractDungeon.player.hasRelic(D8.ID)) {
                D8 d8Relic = (D8) AbstractDungeon.player.getRelic(D8.ID);
                if (d8Relic != null && d8Relic.card != null) {
                    if (d8Relic.card.uuid.equals(source.uuid)) {
                        OVERFLOW = true; // Set overflow if the D8 card is the same as the source card
                    }
                }
            }
        }

        return OVERFLOW; // Return true or false
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        // Check if the played card has OVERFLOW active and is not purged
        if (!isOverflowActive(card) || card.purgeOnUse) {
            return; // Exit if OVERFLOW is not active or the card is purged
        }
            flash();
            addToTop(new GainBlockAction(owner, amount, false));
        }


    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new BlunderGuardPower(amount);
    }
}