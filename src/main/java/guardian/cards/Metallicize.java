package guardian.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;

import static guardian.GuardianMod.makeBetaCardPath;

public class Metallicize extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("Metallicize");
    private static final CardStrings cardStrings;

    public Metallicize() {
        super(ID, cardStrings.NAME, GuardianMod.getResourcePath("cards/Metallicize.png"), 1, cardStrings.DESCRIPTION, CardType.POWER, AbstractCardEnum.GUARDIAN, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = magicNumber = 4;
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("Metallicize.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }

    public AbstractCard makeCopy() {
        return new Metallicize();
    }

    static {
        if(  Settings.language == Settings.GameLanguage.ZHS  ||  Settings.language == Settings.GameLanguage.ZHT  ){
            cardStrings = CardCrawlGame.languagePack.getCardStrings(GuardianMod.makeID("Metallicize"));
        }else{
            cardStrings = CardCrawlGame.languagePack.getCardStrings(GuardianMod.makeID("Metallicize"));
        }
    }
}
