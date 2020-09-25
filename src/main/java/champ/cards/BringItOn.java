package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BringItOn extends AbstractChampCard {

    public final static String ID = makeID("BringItOn");

    //stupid intellij stuff skill, self, common

    private static final int MAGIC = 15;

    public BringItOn() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        baseBlock = 15;
        exhaust = true;
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //finisher();
        applyToSelf(new CounterPower(magicNumber));
        blck();
        fatigue(5);
        if (upgraded) {
            if (dcombo()) exhaust = false;
        }
        finisher();
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}