package slimebound.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.SlimedPower;


public class Equalize extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:Equalize";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/retaliate.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }

    public int missingHealth;
    private boolean isACopy;


    public Equalize() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.baseDamage = 8;
        this.magicNumber = this.baseMagicNumber = 4;
        this.exhaust = true;
        tags.add(CardTags.HEALING);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        logger.info("max health: " + p.maxHealth + ", current health: " + p.currentHealth);
        if (m.hasPower(SlimedPower.POWER_ID) && !this.isACopy) {
            //this.isACopy = true;
            AbstractCard tmp = this.makeStatEquivalentCopy();
            Equalize tmpEq = (Equalize) tmp;

            tmpEq.isACopy = true;
            AbstractDungeon.player.limbo.addToBottom(tmpEq);
            tmpEq.current_x = this.current_x;
            tmpEq.current_y = this.current_y;
            tmpEq.target_x = (Settings.WIDTH / 2.0F - 300.0F * Settings.scale);
            tmpEq.target_y = (Settings.HEIGHT / 2.0F);
            tmpEq.freeToPlayOnce = true;

            //if (m != null) {
            tmpEq.calculateCardDamage(m);


            tmpEq.purgeOnUse = true;
            AbstractDungeon.actionManager.cardQueue.add(new com.megacrit.cardcrawl.cards.CardQueueItem(tmpEq, m, this.energyOnUse));

        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
    }

    public AbstractCard makeCopy() {

        return new Equalize();

    }

    public void triggerOnGlowCheck() {
        slimedGlowCheck();
    }// 68

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();
            upgradeDamage(4);
            upgradeMagicNumber(2);


        }

    }
}


