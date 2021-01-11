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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.actions.TackleSelfDamageAction;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.SlimedPower;
import slimebound.powers.TackleBuffPower;
import slimebound.powers.TackleDebuffPower;


public class ViciousTackle extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:ViciousTackle";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/tendrilstrike.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final CardStrings cardStrings;
    private static final int COST = 2;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }


    public ViciousTackle() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        tags.add(SlimeboundMod.TACKLE);

        this.baseDamage = 16;
        this.baseSelfDamage = this.selfDamage = 3;

    }

    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        int bonus = 0;
        if (mo != null) {
            if (mo.hasPower(SlimedPower.POWER_ID)) {

                bonus = mo.getPower(SlimedPower.POWER_ID).amount;
            }

            if (bonus > 0) {
                this.isDamageModified = true;
            }
        }

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
        addToBot(new TackleSelfDamageAction(new DamageInfo(p, selfDamage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));
        //AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p,p,TackleBuffPower.POWER_ID));


    }

    @Override
    public void triggerOnGlowCheck() {
        slimedGlowCheck();
    }

    public AbstractCard makeCopy() {

        return new ViciousTackle();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

            upgradeDamage(4);
            //this.rawDescription = UPGRADED_DESCRIPTION;
            //this.initializeDescription();


        }

    }
}


