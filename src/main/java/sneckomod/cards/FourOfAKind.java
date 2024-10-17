package sneckomod.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import hermit.cards.AbstractDynamicCard;
import sneckomod.SneckoMod;

public class FourOfAKind extends AbstractSneckoCard {

    public final static String ID = makeID("FourOfAKind");

    // intangible card with a weird condition

    public FourOfAKind() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
        SneckoMod.loadJokeCardImage(this, "FourOfAKind.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new IntangiblePlayerPower(p, 1));
    }


    //LINES 25 - 44 ARE GPT
    // This method determines whether the card can be used
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        // By default, assume the card cannot be used
        boolean canUse = false;

        // Custom condition: check if four unique suits have been played
        if (getUniqueSuitsPlayedThisTurn() == 4) {
            canUse = true; // The card can be used if four unique suits have been played
        }

        // If the card cannot be used, display a custom message
        if (!canUse) {
            this.cantUseMessage = EXTENDED_DESCRIPTION[0]; // Custom message from the card's description file
            return false; // Prevent the card from being played
        }

        // Otherwise, allow the card to be used by calling the superclass method
        return super.canUse(p, m);
    }

    //GLOWING~~~
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (getUniqueSuitsPlayedThisTurn() == 4) {
            this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            selfRetain = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}