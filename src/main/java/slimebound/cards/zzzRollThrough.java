package slimebound.cards;



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.SlimedPower;


public class zzzRollThrough extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:zzzRollThrough";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/rollthrough.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());


    public zzzRollThrough() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.baseDamage = 9;

        this.isMultiDamage = true;


    }


    public void use(AbstractPlayer p, AbstractMonster m) {

        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            logger.info("Searching for Slimed targets.");

            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {

                logger.info("Looking at:." + monster.name);
                if ((!monster.isDead) && (!monster.isDying)) {

                    logger.info("Passed dead check.");
                    if (monster.hasPower(SlimedPower.POWER_ID)) {
                        logger.info("Found Slimed target, adding attack action.");
                        AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

                    }


                }
            }
        }

    }


    public AbstractCard makeCopy() {

        return new zzzRollThrough();

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
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }
}


