package awakenedOne.cards.tokens.spells;

import awakenedOne.effects.InflameNoSound;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import hermit.actions.SoundAction;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToEnemy;
import static awakenedOne.util.Wiz.applyToSelf;

public class BurningStudy extends AbstractSpellCard {
    public final static String ID = makeID(BurningStudy.class.getSimpleName());
    // intellij stuff skill, self, , , , , 2, 1

    public BurningStudy() {
        super(ID, 1, CardType.SKILL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 1;
        this.setBackgroundTexture("awakenedResources/images/512/bg_power_awakened.png", "awakenedResources/images/1024/bg_power_awakened.png");
        loadJokeCardImage(this, makeBetaCardPath(BurningStudy.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToTop(new SoundAction(makeID("FIRESPELL")));
        this.addToBot(new VFXAction(p, new InflameNoSound(p), 1.0F));
        applyToSelf(new StrengthPower(p, magicNumber));
        Wiz.forAllMonstersLiving(q -> applyToEnemy(q, new WeakPower(q, secondMagic, false)));
    }

    public void upp() {
        upgradeMagicNumber(1);
        upgradeSecondMagic(1);
    }
}