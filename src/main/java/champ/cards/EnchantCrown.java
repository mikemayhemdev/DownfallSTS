package champ.cards;

import champ.ChampMod;
import champ.powers.EnergizedDurationPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnchantCrown extends AbstractChampCard {

    public final static String ID = makeID("EnchantCrown");

    //stupid intellij stuff skill, self, rare

    public EnchantCrown() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
    //    tags.add(ChampMod.FINISHER);
        exhaust = true;
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBOGLADIATOR);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) techique();
        exhaust = true;
        applyToSelf(new EnergizedDurationPower(3));
            if (gcombo()) exhaust = false;
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = (gcombo()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        tags.add(ChampMod.TECHNIQUE);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}