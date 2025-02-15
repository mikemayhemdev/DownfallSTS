package guardian.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.actions.BraceAction;

import static guardian.GuardianMod.makeBetaCardPath;

public class GearUp extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("GearUp");
    public static final String IMG_PATH = GuardianMod.getResourcePath("cards/GearUp.png");
    private static final CardStrings cardStrings;

    public GearUp() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseMagicNumber = magicNumber = 10;
        this.selfRetain = true;
        this.exhaust = true;
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("GearUp.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BraceAction(magicNumber));
        this.useGems(p, m);
    }

    public AbstractCard makeCopy() {
        return new GearUp();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(5);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
