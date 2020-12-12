package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BringItOn extends AbstractChampCard {

    public final static String ID = makeID("BringItOn");

    //stupid intellij stuff skill, self, common

    private static final int MAGIC = 10;

    public BringItOn() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        baseBlock = 10;
        exhaust = true;
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //finisher();
        blck();
        applyToSelf(new CounterPower(magicNumber));

        finisher();
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = (upgraded && dcombo()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeBlock(3);
        upgradeMagicNumber(3);
    }
}