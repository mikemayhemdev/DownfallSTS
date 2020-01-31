package guardian.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import guardian.GuardianMod;
import guardian.actions.SwitchToDefenseModeAction;
import guardian.patches.AbstractCardEnum;
import guardian.powers.DefenseModePower;
import guardian.powers.DefensiveModeBuffsPower;

public class EvasiveProtocol extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("EvasiveProtocol");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/slickScales.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;

    //TUNING CONSTANTS
    private static final int DEX = 2;
    private static final int DEFMODETURNS = 2;
    private static final int UPGRADE_DEFMODETURNS = 1;
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

    public EvasiveProtocol() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = DEFMODETURNS;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        if (p.hasPower(DefensiveModeBuffsPower.POWER_ID)) {
            ((DefensiveModeBuffsPower) p.getPower(DefensiveModeBuffsPower.POWER_ID)).dexterity += DEX;
            p.getPower(DefensiveModeBuffsPower.POWER_ID).flash();
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DefensiveModeBuffsPower(p, p, 0, DEX, 0, 0, 0)));
        }
        if (p.hasPower(DefenseModePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, DEX), DEX));
        }
        for (int i = 0; i < this.magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new SwitchToDefenseModeAction(p));

        }

    }

    public AbstractCard makeCopy() {
        return new EvasiveProtocol();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DEFMODETURNS);
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


