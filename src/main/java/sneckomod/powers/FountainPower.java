package sneckomod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;
import sneckomod.cards.TyphoonFang;
import sneckomod.powers.VenomDebuff;
import sneckomod.relics.D8;

public class FountainPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = SneckoMod.makeID("FountainPower"); // Correct ID for the power
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/FountainPower84.png");
    private static final Texture tex32 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/FountainPower32.png");

    public FountainPower(int amount) {
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
    public void onUseCard(AbstractCard card, UseCardAction action) {
        // Check if the played card has Overflow active
        if (isOverflowActive(card)) {
            AbstractMonster randomTarget = getRandomAliveMonster();
            if (randomTarget != null) {
                this.addToBot(new ApplyPowerAction(randomTarget, this.owner, new VenomDebuff(randomTarget, this.amount), this.amount));

                // Check if the player has Toxic Personality power and the target does not have Artifact
                if (AbstractDungeon.player.hasPower(ToxicPersonalityPower.POWER_ID) && !randomTarget.hasPower("Artifact")) {
                    ToxicPersonalityPower toxicPersonalityPower =
                            (ToxicPersonalityPower) AbstractDungeon.player.getPower(ToxicPersonalityPower.POWER_ID);

                    if (toxicPersonalityPower != null) {
                        toxicPersonalityPower.onActivateCall(randomTarget);
                    }
                }
            }
        }
    }
    private boolean isOverflowActive(AbstractCard card) {
        boolean overflowActive = false;

        // Check if the card has the OVERFLOW tag
        if (card.hasTag(SneckoMod.OVERFLOW)) {
            // Check if there are more than 5 cards in hand
            if (AbstractDungeon.player.hand.size() > 5 || AbstractDungeon.player.hasPower("CheatPower")) {
                overflowActive = true; // Overflow condition met
            }

            // Check if the card is a TyphoonFang or purges on use
            if (card instanceof TyphoonFang && card.purgeOnUse) {
                overflowActive = false; // If it purges on use, overflow cannot activate
            }

            // Check for D8 relic
            if (AbstractDungeon.player.hasRelic(D8.ID)) {
                D8 d8Relic = (D8) AbstractDungeon.player.getRelic(D8.ID);
                if (d8Relic != null && d8Relic.card != null) {
                    if (d8Relic.card.uuid.equals(card.uuid)) {
                        overflowActive = true; // Set overflow if the D8 card is the same as the source card
                    }
                }
            }
        }

        return overflowActive; // Return the overflow state
    }

    // Helper function to get a random alive monster
    private AbstractMonster getRandomAliveMonster() {
        AbstractMonster randomTarget = AbstractDungeon.getMonsters().getRandomMonster(true);
        return (randomTarget != null && randomTarget.isDeadOrEscaped()) ? null : randomTarget;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new FountainPower(this.amount);
    }
}
