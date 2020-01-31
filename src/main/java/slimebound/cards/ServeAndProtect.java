package slimebound.cards;


import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;


public class ServeAndProtect extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:ServeAndProtect";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/formablockade.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 0;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }


    public ServeAndProtect() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);

        this.exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractCard c = new ServeAndProtectServe();
        if (upgraded) c.upgrade();

        AbstractCard c2 = new ServeAndProtectProtect();
        if (upgraded) c2.upgrade();

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c2));

    }

    public AbstractCard makeCopy() {
        return new ServeAndProtect();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();

            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();


        }
    }
}


