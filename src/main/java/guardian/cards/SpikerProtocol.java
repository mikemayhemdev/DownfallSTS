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
import downfall.actions.ForceWaitAction;
import guardian.GuardianMod;
import guardian.powers.BracePerTurnPower;
import guardian.powers.EvasiveProtocolPower;
import guardian.stances.DefensiveMode;
import guardian.patches.AbstractCardEnum;
import guardian.powers.DontLeaveDefensiveModePower;
import guardian.powers.SpikerProtocolPower;
import hermit.actions.ReduceDebuffsAction;

import static guardian.GuardianMod.makeBetaCardPath;

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
    private static final int THORNS = 2;
    private static final int UPGRADE_THORNS = 1;
    private static final int SOCKETS = 0;
    private static final int BRACE_PER_TURN = 3;
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
        this.secondaryM = BRACE_PER_TURN;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("SpikerProtocol.png"));

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int moda = 999;
        int mods = 0;
        super.use(p, m);


        if (AbstractDungeon.player.hasPower("Guardian:ModeShiftPower")) {
            moda = AbstractDungeon.player.getPower("Guardian:ModeShiftPower").amount;
        }

      //  if (((moda - mods) <= 0)) {
       //     AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, magicNumber), magicNumber));



        if (!this.upgraded) {
            brace(6);
            mods = 6;
        }

        if (this.upgraded) {
            brace(9);
            mods = 9;
        }
        // }
        if (p.stance instanceof DefensiveMode) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, magicNumber), magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpikerProtocolPower(p, magicNumber)));
       // AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BracePerTurnPower(p, this.secondaryM)));
    }

    public AbstractCard makeCopy() {
        return new SpikerProtocol();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_THORNS);
            this.rawDescription = UPGRADED_DESCRIPTION;
            updateDescription();
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


