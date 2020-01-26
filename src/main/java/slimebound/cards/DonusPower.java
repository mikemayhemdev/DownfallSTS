package slimebound.cards;



import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import slimebound.SlimeboundMod;


public class DonusPower extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:DonusPower";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/circleofpower.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;

    private static final int COST = 0;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;

    public DonusPower() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);
        tags.add(SlimeboundMod.STUDY_SHAPES);
        tags.add(SlimeboundMod.STUDY);

        this.magicNumber = this.baseMagicNumber = 9;

        this.exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
      if (upgraded) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, 1), 1));

        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK).makeCopy();
        // c.modifyCostForTurn(this.magicNumber * -1);
        c.setCostForTurn(c.cost + (this.magicNumber * -1));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction(c, true));


    }

    public AbstractCard makeCopy() {
        return new DonusPower();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();

            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}


