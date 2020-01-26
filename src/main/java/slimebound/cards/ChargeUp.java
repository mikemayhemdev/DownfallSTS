package slimebound.cards;



import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import slimebound.SlimeboundMod;
import slimebound.powers.NextTurnGainStrengthPower;


public class ChargeUp extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:ChargeUp";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/chargeup.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;

    private static final int COST = 0;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;

    public ChargeUp() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);

        tags.add(SlimeboundMod.STUDY_GUARDIAN);
        tags.add(SlimeboundMod.STUDY);
        this.baseBlock = 8;
        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_GUARDIAN_DESTROY"));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, this.block), this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnGainStrengthPower(p, p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new ChargeUp();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
           // upgradeMagicNumber(1);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}


