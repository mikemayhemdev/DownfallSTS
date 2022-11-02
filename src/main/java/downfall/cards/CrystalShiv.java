package downfall.cards;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AccuracyPower;
import downfall.downfallMod;

public class CrystalShiv extends CustomCard {
    public static final String ID = downfallMod.makeID("CrystalShiv");
    public static final String NAME;
    public static final String IMG_PATH = "cards/crystalShiv.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 0;
    private static final int DAMAGE = 4;

    //TUNING CONSTANTS
    private static final int UPGRADE_BONUS = 2;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;
    public static String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;

    //END TUNING CONSTANTS

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }


    public CrystalShiv() {
        super(ID, NAME, "guardianResources/GuardianImages/cards/CrystalShiv.png", COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);

        this.baseDamage = DAMAGE;

        if ((AbstractDungeon.player != null) && (AbstractDungeon.player.hasPower(AccuracyPower.POWER_ID))) {
            this.baseDamage = (DAMAGE + AbstractDungeon.player.getPower(AccuracyPower.POWER_ID).amount);
        } else {
            this.baseDamage = DAMAGE;
        }


        this.exhaust = true;

        //GuardianMod.loadJokeCardImage(this, makeBetaCardPath("CrystalShiv.png"));
    }

    @Override
    public void applyPowers() {
        if ((AbstractDungeon.player != null) && (AbstractDungeon.player.hasPower(AccuracyPower.POWER_ID))) {
            this.baseDamage = (DAMAGE + AbstractDungeon.player.getPower(AccuracyPower.POWER_ID).amount);
        }
        super.applyPowers();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

    }

    public AbstractCard makeCopy() {
        return new CrystalShiv();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_BONUS);
        }


    }

}


