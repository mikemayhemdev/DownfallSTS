package slimebound.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.ReturnRandom0Cost;
import slimebound.actions.TackleSelfDamageAction;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.TackleBuffPower;
import slimebound.powers.TackleDebuffPower;


public class HungryTackle extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:HungryTackle";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/tackle.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }

    public HungryTackle() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        tags.add(SlimeboundMod.TACKLE);


        this.baseDamage = 10;
        baseSelfDamage = this.selfDamage = 3;


        this.magicNumber = this.baseMagicNumber = 1;

    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new TackleSelfDamageAction(new DamageInfo(AbstractDungeon.player, selfDamage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        //  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new ReturnRandom0Cost(this.magicNumber));
        //AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p,p,TackleBuffPower.POWER_ID));
    }

    public AbstractCard makeCopy() {

        return new HungryTackle();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

            upgradeDamage(4);

            // this.rawDescription = UPGRADED_DESCRIPTION;
            //this.initializeDescription();
            //upgradeMagicNumber(1);

        }

    }
}


