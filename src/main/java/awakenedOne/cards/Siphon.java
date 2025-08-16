package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.*;

public class Siphon extends AbstractAwakenedCard {
    public final static String ID = makeID(Siphon.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public Siphon() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 7;
        magicNumber = baseMagicNumber = 2;
        this.tags.add(AwakenedOneMod.CHANT);
        loadJokeCardImage(this, makeBetaCardPath(Siphon.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        if (isTrig_chant()) {
            this.addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -magicNumber), -magicNumber));
            if (m != null && !m.hasPower("Artifact")) {
                this.addToBot(new ApplyPowerAction(m, p, new GainStrengthPower(m, magicNumber), magicNumber));
                this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
                this.addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, magicNumber), magicNumber));
            }
            chant();
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = isChantActiveGlow(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void chant() {
        checkOnChant();
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}