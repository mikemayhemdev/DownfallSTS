package guardian.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import guardian.GuardianMod;
import guardian.actions.DoubleStatsAction;
import guardian.patches.AbstractCardEnum;


public class OmegaCannon extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("OmegaCannon");
    public static final String NAME;
    public static String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/OmegaCannon.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final CardStrings cardStrings;

    //TUNING CONSTANTS

    private static final int COST = 5;
    private static final int DAMAGE = 15;
    private static final int UPGRADE_BONUS = 5;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;

    //END TUNING CONSTANTS
    private int prevDiscount = 0;

    public OmegaCannon() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        //this.sockets.add(GuardianMod.socketTypes.RED);


    }

    @Override
    public void applyPowers() {

        this.costForTurn += this.prevDiscount;

        super.applyPowers();
        if (!AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) return;
        if (AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount < 0) return;

        int str = AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
        if (str < 0) str = -str;
        this.prevDiscount = str;

        if (this.costForTurn - this.prevDiscount < 0) this.prevDiscount = this.costForTurn;

        this.costForTurn = this.costForTurn - this.prevDiscount;
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        this.prevDiscount = 0;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        this.prevDiscount = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
    }


    public AbstractCard makeCopy() {
        return new OmegaCannon();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_BONUS);
        }


    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}


