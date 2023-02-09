package guardian.cards;


import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.actions.ReduceRightMostStasisAction;
import guardian.patches.AbstractCardEnum;

public class TemporalShield extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("TemporalShield");
    public static final String IMG_PATH = GuardianMod.getResourcePath("cards/temporalShield.png");
    private static final CardStrings cardStrings;

    public TemporalShield() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.SKILL, AbstractCardEnum.GUARDIAN, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 8;
        this.socketCount = 0;
        updateDescription();
        loadGemMisc();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new ReduceRightMostStasisAction());
        super.use(p, m);
        this.useGems(p, m);
    }

    public AbstractCard makeCopy() {
        return new TemporalShield();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


