package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.ui.AwakenButton.awaken;
import static awakenedOne.util.Wiz.atb;

public class ToTheLimit extends AbstractAwakenedCard {
    public final static String ID = makeID(ToTheLimit.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public ToTheLimit() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        this.exhaust = true;
        this.tags.add(AwakenedOneMod.CHANT);
        loadJokeCardImage(this, makeBetaCardPath(ToTheLimit.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (isTrig_chant()) {
            chant();
        }
    }

    @Override
    public void chant() {
        atb(new GainEnergyAction(magicNumber));
        checkOnChant();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = isChantActiveGlow(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}