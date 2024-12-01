package guardian.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;
import guardian.powers.NextTurnGainTemporaryStrengthPower;

import static guardian.GuardianMod.makeBetaCardPath;

public class ChargeUp extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("ChargeUp");
    public static final String IMG_PATH = GuardianMod.getResourcePath("cards/chargeup.png");
    private static final CardStrings cardStrings;

    public ChargeUp() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.SKILL, AbstractCardEnum.GUARDIAN, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 7;
        this.baseMagicNumber = this.magicNumber = 2;
        this.socketCount = 1;
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("ChargeUp.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        addToBot(new SFXAction("MONSTER_GUARDIAN_DESTROY"));
        addToBot(new GainBlockAction(p, block));
        addToBot(new ApplyPowerAction(p, p, new NextTurnGainTemporaryStrengthPower(p, magicNumber)));
        super.useGems(p, m);
    }

    public AbstractCard makeCopy() {
        return new ChargeUp();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(2);
            upgradeMagicNumber(1);
        }
    }

    public void updateDescription() {
        if (this.socketCount > 0) {
            if (upgraded && cardStrings.UPGRADE_DESCRIPTION != null) {
                this.rawDescription = this.updateGemDescription(cardStrings.UPGRADE_DESCRIPTION, true);
            } else {
                this.rawDescription = this.updateGemDescription(cardStrings.DESCRIPTION, true);
            }
        }
        this.initializeDescription();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


