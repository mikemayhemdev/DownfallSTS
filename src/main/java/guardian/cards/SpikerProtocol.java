package guardian.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import guardian.GuardianMod;
import guardian.stances.DefensiveMode;
import guardian.patches.AbstractCardEnum;
import guardian.powers.DontLeaveDefensiveModePower;
import guardian.powers.SpikerProtocolPower;

public class SpikerProtocol extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("SpikerProtocol");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/spikyScales.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;

    //TUNING CONSTANTS
    private static final int THORNS = 4;
    private static final int UPGRADE_THORNS = 2;
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

    public SpikerProtocol() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = THORNS;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpikerProtocolPower(p, magicNumber)));
        if (p.stance instanceof DefensiveMode) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, magicNumber), magicNumber));
        }
        brace(10);
    }

    public AbstractCard makeCopy() {
        return new SpikerProtocol();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_THORNS);
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


