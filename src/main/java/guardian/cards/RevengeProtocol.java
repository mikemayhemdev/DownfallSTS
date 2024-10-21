package guardian.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;
import guardian.powers.BracePerTurnPower;

import static guardian.GuardianMod.makeBetaCardPath;

public class RevengeProtocol extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("RevengeProtocol");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/automayhem.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;

    public RevengeProtocol() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = 5;
        this.socketCount = 0;
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("RevengeProtocol.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
//        addToBot(new ApplyPowerAction(p, p, new RevengePower(p, p, magicNumber), magicNumber));
//        if (p.stance instanceof DefensiveMode) {
//            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
//        }
        addToBot(new ApplyPowerAction(p, p, new BracePerTurnPower(p, secondaryM)));
    }

    public AbstractCard makeCopy() {
        return new RevengeProtocol();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
//            upgradeMagicNumber(1);
//            upgradeSecondaryM(1);
        }
    }

    public void updateDescription() {
//        if (this.socketCount > 0) {
//            if (upgraded && UPGRADED_DESCRIPTION != null) {
//                this.rawDescription = this.updateGemDescription(UPGRADED_DESCRIPTION, true);
//            } else {
//                this.rawDescription = this.updateGemDescription(DESCRIPTION, true);
//            }
//        }
        this.initializeDescription();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}