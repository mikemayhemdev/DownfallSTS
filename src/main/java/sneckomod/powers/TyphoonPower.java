package sneckomod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;
import sneckomod.cards.TyphoonFang;
import com.megacrit.cardcrawl.core.Settings;

public class TyphoonPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = SneckoMod.makeID("TyphoonPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/Rolls84.png");
    private static final Texture tex32 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/Rolls32.png");

    //coding this SUCKED, was butchered code and even after I decided to use gpt it was still insanely broken for hours
    //making both versions of the power stack and chain properly, not fizzle randomly, not softlock etc.
    //initially, I wanted the typhoon fangs to target the same enemy as the initial card
    //(and selecting a random target if the enemy died or if the card was self targeting / aoe etc.)
    //but it caused terrible issues with both powers stacking properly and enemies dying mid-chain
    //having more than one stack of this at a time is probably going to be very rare anyways, but at least it works?

    public TyphoonPower(int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;  // Store the number of stacks
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    // Overflow check (ensures the card has OVERFLOW tag and correct conditions are met)
    public boolean isOverflowActive(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        return (p.hand.size() >= 4 && card.hasTag(SneckoMod.OVERFLOW)) || (card.cost >= 3 && card.hasTag(SneckoMod.OVERFLOW));
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        // Check if the played card has OVERFLOW active and is not purged
        if (!isOverflowActive(card) || card.purgeOnUse) {
            return; // Exit if OVERFLOW is not active or the card is purged
        }

        // Loop through and play as many TyphoonFang cards as the power's amount (stacks)
        for (int i = 0; i < this.amount; i++) {
            // Create a new instance of TyphoonFang
            AbstractCard tmp = new TyphoonFang();
            tmp.purgeOnUse = true; // Set purge on use
            tmp.freeToPlayOnce = true;

            AbstractMonster randomTarget = getRandomAliveMonster();

            // Continuously attempt to find a valid target if the current one is null or dead
            while (randomTarget == null) {
                randomTarget = getRandomAliveMonster();
                // If all monsters are dead, exit the loop
                if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    break;
                }
            }

            // If no valid target is available (all monsters are dead), break out of the loop entirely
            if (randomTarget == null) {
                break;
            }

            // Add the card to the player's limbo (temporary play area)
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (Settings.WIDTH / 2.0F - 300.0F * Settings.scale);
            tmp.target_y = (Settings.HEIGHT / 2.0F);

            // Calculate the damage for the target
            tmp.calculateCardDamage(randomTarget);

            // Add the card to the action manager's card queue, targeting the selected monster
            AbstractDungeon.actionManager.cardQueue.add(new com.megacrit.cardcrawl.cards.CardQueueItem(tmp, randomTarget, card.energyOnUse));
        }
    }

    // Helper function to get a random alive monster
    private AbstractMonster getRandomAliveMonster() {
        AbstractMonster randomTarget = AbstractDungeon.getMonsters().getRandomMonster(true);
        if (randomTarget != null && randomTarget.isDeadOrEscaped()) {
            randomTarget = null; // Ensure that the selected monster is alive
        }
        return randomTarget;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            addToBot(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        }
        if (this.amount != 1) {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new TyphoonPower(this.amount);
    }
}
