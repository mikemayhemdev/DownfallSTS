package slimebound.cards;



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;


public class DisruptingSlam extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:DisruptingSlam";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/violentstop.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final CardStrings cardStrings;
    private static final int COST = 1;


    public DisruptingSlam() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.baseDamage = 6;
        this.isMultiDamage = true;
        this.magicNumber = this.baseMagicNumber = 1;


    }


    public void use(AbstractPlayer p, AbstractMonster m) {


        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {


                if ((!monster.isDead) && (!monster.isDying)) {

                    if (monster != null) {
                        if (monster.intent == AbstractMonster.Intent.ATTACK || monster.intent == AbstractMonster.Intent.ATTACK_BUFF || monster.intent == AbstractMonster.Intent.ATTACK_DEBUFF || monster.intent == AbstractMonster.Intent.ATTACK_DEFEND) {
                            //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new WeakPower(monster, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));

                        } else {
                            com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(monster, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

                        }
                    }


                }
            }
        }

    }


    public AbstractCard makeCopy() {

        return new DisruptingSlam();

    }


    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(1);

        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }
}


