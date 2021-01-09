package slimebound.cards;


import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;


public class Schlurp extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:Schlurp";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/schlurp.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }


    public Schlurp() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = 2;
        this.cardsToPreview = new Lick();
        baseBlock = block = 6;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));

        for (int i = 0; i < this.magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Lick()));

        }


    }

    public AbstractCard makeCopy() {

        return new Schlurp();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

            upgradeMagicNumber(1);

        }

    }
}


