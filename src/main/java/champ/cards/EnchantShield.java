package champ.cards;

import champ.powers.EnchantedShieldPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnchantShield extends AbstractChampCard {

    public final static String ID = makeID("EnchantShield");

    //stupid intellij stuff skill, self, rare

    public EnchantShield() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        finisher();
        applyToSelf(new EnchantedShieldPower(1));
        if (upgraded) {
            if (dcombo()) exhaust = false;
        }
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}