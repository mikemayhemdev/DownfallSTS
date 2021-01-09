package guardian.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;
import guardian.powers.StunnedPower;


public class HyperBeam_Guardian extends AbstractGuardianCard {
    public static final String ID = "Guardian:HyperBeam_Guardian";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/hyperbeamGuardian.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 3;

    //TUNING CONSTANTS
    private static final int DAMAGE = 36;
    private static final int UPGRADE_DAMAGE = 4;
    private static final int STRENGTHMULTIPLIER = 4;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;
    public static String UPGRADED_DESCRIPTION;

    //END TUNING CONSTANTS

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }

    public HyperBeam_Guardian() {

        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseDamage = DAMAGE;

        this.tags.add(GuardianMod.BEAM);
        this.isMultiDamage = true;
        this.magicNumber = this.baseMagicNumber = 2;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();


    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        int bonus = 0;
        if (player.hasPower(StrengthPower.POWER_ID) && this.magicNumber > 1) {
            bonus = player.getPower(StrengthPower.POWER_ID).amount * (this.magicNumber - 1);
        }
        return tmp + calculateBeamDamage() + bonus;
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, float tmp) {
        int bonus = 0;
        if (player.hasPower(StrengthPower.POWER_ID)) {
            bonus = player.getPower(StrengthPower.POWER_ID).amount * (this.magicNumber + 1);
        }
        return tmp + calculateBeamDamage() + bonus;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StunnedPower(p), 1));
        super.useGems(p, m);
    }

    public AbstractCard makeCopy() {

        return new HyperBeam_Guardian();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(2);

        }
    }

    public void updateDescription() {

        if (this.socketCount > 0) {
            if (upgraded && UPGRADED_DESCRIPTION != null) {
                this.rawDescription = this.updateGemDescription(UPGRADED_DESCRIPTION, true);
            } else {
                this.rawDescription = this.updateGemDescription(DESCRIPTION, true);
            }
        }
        this.initializeDescription();
    }
}


