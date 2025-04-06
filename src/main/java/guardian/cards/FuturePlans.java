package guardian.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;
import guardian.powers.FuturePlansPower;

import static guardian.GuardianMod.makeBetaCardPath;

public class FuturePlans extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("FuturePlans");
    public static final String IMG_PATH = GuardianMod.getResourcePath("cards/futurePlans.png");
    private static final CardStrings cardStrings;

    public FuturePlans() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.POWER, AbstractCardEnum.GUARDIAN, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = this.magicNumber = 1;
        this.socketCount = 0;
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("FuturePlans.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        addToBot(new ApplyPowerAction(p, p, new FuturePlansPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new FuturePlans();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isInnate = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
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


