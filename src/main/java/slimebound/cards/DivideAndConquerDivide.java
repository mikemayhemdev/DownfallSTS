package slimebound.cards;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeSpawnAction;
import slimebound.orbs.AttackSlime;
import slimebound.orbs.PoisonSlime;
import slimebound.patches.AbstractCardEnum;


public class DivideAndConquerDivide extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:DivideAndConquerDivide";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/splitbruisertoxic.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;
    public static int originalDamage;
    public static int originalBlock;
    public static int upgradeDamage;
    public static int upgradeSelfDamage;
    private static int baseSelfDamage;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }


    public DivideAndConquerDivide() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);

        this.exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
            Boolean o = AbstractDungeon.cardRng.randomBoolean();
            if (o) {
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new AttackSlime(), false, true));
            } else {
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new PoisonSlime(), false, true));

            }

        } else {
            AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new AttackSlime(), false, true));
            AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new PoisonSlime(), false, true));
        }


    }

    public AbstractCard makeCopy() {

        return new DivideAndConquerDivide();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();

        }

    }
}


