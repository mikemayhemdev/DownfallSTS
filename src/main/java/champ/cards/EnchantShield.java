package champ.cards;

import champ.ChampMod;
import champ.powers.EnchantedShieldPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class EnchantShield extends AbstractChampCard {

    public final static String ID = makeID("EnchantShield");

    //stupid intellij stuff skill, self, rare

    public EnchantShield() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
    //    tags.add(ChampMod.FINISHER);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) techique();
        //finisher();
        exhaust = true;
        applyToSelf(new DexterityPower(p, 2));

            if (dcombo()) exhaust = false;

     //   finisher();
    }


    @Override
    public void triggerOnGlowCheck() {
        glowColor = (dcombo()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        tags.add(ChampMod.TECHNIQUE);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}