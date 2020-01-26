package slimebound.cards;



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import slimebound.SlimeboundMod;


public class FaceSlap extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:FaceSlap";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/faceslap.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final CardStrings cardStrings;
    private static final int COST = 0;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;


    public FaceSlap() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);
        tags.add(SlimeboundMod.STUDY_CHAMP);
        tags.add(SlimeboundMod.STUDY);

        this.baseDamage = 7;
        this.magicNumber = this.baseMagicNumber = 2;

        this.exhaust = true;


    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(m, p));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));

    }


    public AbstractCard makeCopy() {

        return new FaceSlap();

    }


    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

           // upgradeMagicNumber(1);
            upgradeDamage(3);

        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;

    }
}


