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
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //finisher();
        applyToSelf(new DexterityPower(p, 2));
        if (upgraded) {
            if (dcombo()) exhaust = false;
        }
        finisher();
    }


    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}