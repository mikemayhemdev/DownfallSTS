package slimebound.cards;



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.SlimedPower;
import slimebound.powers.TackleBuffPower;
import slimebound.powers.TackleDebuffPower;


public class RollThrough extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:RollThrough";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "cards/bodyblow.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int UPGRADE_BONUS = 3;


    public RollThrough() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.baseDamage = 5;
        this.magicNumber=this.baseMagicNumber=2;
        this.selfDamage= 2;
        this.isMultiDamage=true;


    }



    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if ((!monster.isDead) && (!monster.isDying)) {

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new TackleDebuffPower(monster, p,this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));


            }

        }
    }




    public AbstractCard makeCopy() {

        return new RollThrough();

    }


    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();
            upgradeDamage(3);

        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }
}


