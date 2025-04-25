package slimebound.cards;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.*;
import slimebound.orbs.ShieldSlime;
import slimebound.patches.AbstractCardEnum;


public class SplitLeeching extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:SplitLeeching";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/splitshield.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public SplitLeeching() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);

       // this.baseBlock = 8;
        this.magicNumber = this.baseMagicNumber = 1;
        //this.exhaust = true;
        SlimeboundMod.loadJokeCardImage(this, "SplitLeeching.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EnergyToPikeAction(2));
        addToBot(new BuffPikeStrengthAction(magicNumber));

    }

    public AbstractCard makeCopy() {
        return new SplitLeeching();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);

        }
    }
}

