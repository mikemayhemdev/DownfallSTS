package awakenedOne.cards.tokens;

import awakenedOne.cards.AbstractAwakenedCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import static awakenedOne.AwakenedOneMod.*;

public class Ceremony extends AbstractAwakenedCard {
    public final static String ID = makeID(Ceremony.class.getSimpleName());
    // intellij stuff attack, enemy, special, 3, 2, , , ,

    public Ceremony() {
        super(ID, 0, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        selfRetain = true;
        this.baseSecondMagic = 2;
        this.secondMagic = this.baseSecondMagic;
        loadJokeCardImage(this, makeBetaCardPath(ID + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VFXAction(p, new InflameEffect(p), .1F));
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
       // applyToSelf(new StrengthOverTurnsPower(1, this.magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}