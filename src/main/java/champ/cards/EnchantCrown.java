package champ.cards;

import champ.ChampMod;
import champ.powers.EnergizedDurationPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnchantCrown extends AbstractChampCard {

    public final static String ID = makeID("EnchantCrown");

    //stupid intellij stuff skill, self, rare

    public EnchantCrown() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        tags.add(ChampMod.FINISHER);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new EnergizedDurationPower(3));
        if (upgraded)
            if (gcombo()) exhaust = false;
        finisher();
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = (upgraded && gcombo()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}