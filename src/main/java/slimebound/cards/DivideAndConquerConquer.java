package slimebound.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.orbs.SpawnedSlime;
import slimebound.patches.AbstractCardEnum;


public class DivideAndConquerConquer extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:DivideAndConquerConquer";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/conquer.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 0;
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

    public DivideAndConquerConquer() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);


        this.baseDamage = originalDamage = 10;
        this.magicNumber = this.baseMagicNumber = 2;


        this.exhaust = true;
        this.selfRetain = true;

    }

    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        int bonus = 0;
        for (AbstractOrb o : player.orbs)
            if (o instanceof SpawnedSlime) {
                bonus += this.magicNumber;
            }
        return tmp + bonus;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction(AbstractDungeon.player));

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));


    }

    public AbstractCard makeCopy() {

        return new DivideAndConquerConquer();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
        }

    }
}


