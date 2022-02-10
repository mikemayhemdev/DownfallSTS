package hermit.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import hermit.HermitMod;
import hermit.patches.EndOfTurnPatch;
import hermit.patches.VigorPatch;
import hermit.powers.BigShotPower;
import hermit.powers.ComboPower;
import hermit.powers.Concentration;
import hermit.util.TextureLoader;

import java.util.ArrayList;

public abstract class AbstractHermitCard extends CustomCard {

    // Custom Abstract Cards can be a bit confusing. While this is a simple base for simply adding a second magic number,
    // if you're new to modding I suggest you skip this file until you know what unique things that aren't provided
    // by default, that you need in your own cards.

    // In this example, we use a custom Abstract Card in order to define a new magic number. From here on out, we can
    // simply use that in our cards, so long as we put "extends AbstractDynamicCard" instead of "extends CustomCard" at the start.
    // In simple terms, it's for things that we don't want to define again and again in every single card we make.

    static ArrayList<AbstractCard> allCards = new ArrayList();

    static boolean doneInit = false;
    public boolean trig_deadon = false;

    public static void init() {
        doneInit=true;
    }

    public int defaultSecondMagicNumber;        // Just like magic number, or any number for that matter, we want our regular, modifiable stat
    public int defaultBaseSecondMagicNumber;    // And our base stat - the number in it's base state. It will reset to that by default.
    public boolean upgradedDefaultSecondMagicNumber; // A boolean to check whether the number has been upgraded or not.
    public boolean isDefaultSecondMagicNumberModified; // A boolean to check whether the number has been modified or not, for coloring purposes. (red/green)

    public AbstractHermitCard(final String id,
                              final String name,
                              final String img,
                              final int cost,
                              final String rawDescription,
                              final CardType type,
                              final CardColor color,
                              final CardRarity rarity,
                              final CardTarget target) {

        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        // Set all the things to their default values.
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isDefaultSecondMagicNumberModified = false;

    }

    public void displayUpgrades() { // Display the upgrade - when you click a card to upgrade it
        super.displayUpgrades();
        if (upgradedDefaultSecondMagicNumber) { // If we set upgradedDefaultSecondMagicNumber = true in our card.
            defaultSecondMagicNumber = defaultBaseSecondMagicNumber; // Show how the number changes, as out of combat, the base number of a card is shown.
            isDefaultSecondMagicNumberModified = true; // Modified = true, color it green to highlight that the number is being changed.
        }

    }

    public void upgradeDefaultSecondMagicNumber(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        defaultBaseSecondMagicNumber += amount; // Upgrade the number by the amount you provide in your card.
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber; // Set the number to be equal to the base value.
        upgradedDefaultSecondMagicNumber = true; // Upgraded = true - which does what the above method does.
    }

    @Override
    public void resetAttributes()
    {
        super.resetAttributes();

        this.trig_deadon = false;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard original = super.makeStatEquivalentCopy();

        ((AbstractHermitCard)original).trig_deadon = this.trig_deadon;

        return original;
    }

    public boolean isDeadOn()
    {
        boolean conc = AbstractDungeon.player.hasPower(Concentration.POWER_ID);

        double hand_pos = (AbstractDungeon.player.hand.group.indexOf(this)+0.5);
        double hand_size = (AbstractDungeon.player.hand.size());
        double relative = Math.abs(hand_pos-hand_size/2);

        if (relative<1 || conc)
        {
            this.trig_deadon = true;
        }

        return (trig_deadon);
    }

    public boolean isDeadOnPos()
    {
        boolean conc = AbstractDungeon.player.hasPower(Concentration.POWER_ID);

        double hand_pos = (AbstractDungeon.player.hand.group.indexOf(this)+0.5);
        double hand_size = (AbstractDungeon.player.hand.size());
        double relative = Math.abs(hand_pos-hand_size/2);

        return (relative<1 || conc);
    }

    protected boolean onDeadOn()
    {
        if (AbstractDungeon.player.hasPower(BigShotPower.POWER_ID)) {
            VigorPatch.isActive=true;
        }
        if (AbstractDungeon.player.hasPower(ComboPower.POWER_ID)) {
            if (!doneInit)
                init();
            AbstractDungeon.player.getPower(ComboPower.POWER_ID).flash();

            for (int i = 0; i < AbstractDungeon.player.getPower(ComboPower.POWER_ID).amount; i++) {
                this.addToBot(new MakeTempCardInHandAction(hermit.HermitMod.deadList.getRandomCard(true).makeCopy(), 1));
            }
        }

        EndOfTurnPatch.deadon_counter++;

        return true;
    }

    public String betaArtPath;
    @Override
    protected Texture getPortraitImage() {
        if (Settings.PLAYTESTER_ART_MODE || UnlockTracker.betaCardPref.getBoolean(this.cardID, false)) {
            if (this.textureImg == null) {
                return null;
            } else {
                if (betaArtPath != null) {
                    int endingIndex = betaArtPath.lastIndexOf(".");
                    String newPath = betaArtPath.substring(0, endingIndex) + "_p" + betaArtPath.substring(endingIndex);
                    newPath = "hermitResources/images/betacards/" + newPath;
                    System.out.println("Finding texture: " + newPath);

                    Texture portraitTexture;
                    portraitTexture = TextureLoader.getTexture(newPath);

                    return portraitTexture;
                }
            }
        }
        return super.getPortraitImage();
    }

    public static class Enums {
        @SpireEnum
        public static AbstractCard.CardTags DEADON;
    }


}