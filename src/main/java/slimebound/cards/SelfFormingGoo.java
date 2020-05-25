package slimebound.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.SelfFormingGooPower;
import slimebound.powers.SelfFormingGooPowerPlus;


public class SelfFormingGoo extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:SelfFormingGoo";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/straygoop.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public SelfFormingGoo() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SelfFormingGooPower(p, 1), 1));
        if (upgraded) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SelfFormingGooPowerPlus(p, 1), 1));

    }

    public AbstractCard makeCopy() {
        return new SelfFormingGoo();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            isInnate = true;
            rawDescription = UPGRADED_DESCRIPTION;

        }
    }
}

