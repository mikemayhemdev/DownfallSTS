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

    public HungryTackle() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        tags.add(SlimeboundMod.TACKLE);


        this.baseDamage = this.originalDamage = 10;
        this.baseSelfDamage = this.selfDamage = 3;

        this.upgradeDamage = 3;

        this.magicNumber = this.baseMagicNumber = 1;

        this.upgradeSelfDamage(this.baseSelfDamage);

    }

    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        int bonus = 0;
        if (player.hasPower(TackleBuffPower.POWER_ID)) {
            bonus = player.getPower(TackleBuffPower.POWER_ID).amount;
        }
        if (mo != null) {
            if (mo.hasPower(TackleDebuffPower.POWER_ID)) {
                bonus = bonus + mo.getPower(TackleDebuffPower.POWER_ID).amount;
            }
        }
        return tmp + bonus;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(p, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.selfDamage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));
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

            upgradeDamage(upgradeDamage);

            // this.rawDescription = UPGRADED_DESCRIPTION;
            //this.initializeDescription();
            //upgradeMagicNumber(1);

        }

    }
}


