package slimebound.cards;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeSpawnAction;
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
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public SplitLeeching() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int bonus = 0;
        if (upgraded) bonus = this.magicNumber;
        AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new ShieldSlime(), false, true, 0, bonus));

    }

    public AbstractCard makeCopy() {
        return new SplitLeeching();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();

            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();

        }
    }
}

