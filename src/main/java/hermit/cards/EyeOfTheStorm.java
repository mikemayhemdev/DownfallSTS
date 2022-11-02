package hermit.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.HermitMod;
import hermit.actions.EyeOfTheStormAction;
import hermit.characters.hermit;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class EyeOfTheStorm extends AbstractDynamicCard {

    //
    /*
     * SNAPSHOT: Deals 12/16 damage, Dead-On makes it free.
     */


    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(EyeOfTheStorm.class.getSimpleName());
    public static final String IMG = makeCardPath("eye_of_the_storm.png");

    // /TEXT DECLARATION/
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = downfallMod.Enums.COLOR_YELLOW;

    private static final int COST = 1;


    // /STAT DECLARATION/

    public EyeOfTheStorm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        magicNumber = baseMagicNumber = 0;
        loadJokeCardImage(this, "eye_of_the_storm.png");
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new EyeOfTheStormAction(p, this, getLogicalCardCost(this)));
        /*
        if (upgraded)
            this.addToBot(new DrawCardAction(1));
        if (isDeadOn()) {
            onDeadOn();

            this.addToBot(new EyeOfTheStormAction(p,this, getLogicalCardCost(this)));
            this.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, SnipePower.POWER_ID, 1));
        }
        */
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
//         if (isDeadOnPos()) {
//             this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
//         }
    }

    private static int getLogicalCardCost(AbstractCard ccc) {
        if (ccc.costForTurn > 0 && !ccc.freeToPlayOnce) {
            return ccc.costForTurn;
        }
        return 0;
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}
