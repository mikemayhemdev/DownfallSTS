package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class ToTheLimit extends AbstractAwakenedCard {
    public final static String ID = makeID(ToTheLimit.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public ToTheLimit() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.currentHealth * 2 > p.maxHealth) {
            applyToSelf(new StrengthPower(p, magicNumber));
            //this.exhaust = false;
        }
        else {
            applyToSelf(new RitualPower(p, secondMagic, true));
            this.exhaust = true;
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = AbstractDungeon.player.currentHealth * 2 < AbstractDungeon.player.maxHealth ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeSecondMagic(1);
    }
}