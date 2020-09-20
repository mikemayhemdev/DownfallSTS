package champ.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class EnchantSword extends AbstractChampCard {

    public final static String ID = makeID("EnchantSword");

    //stupid intellij stuff skill, self, uncommon

    public EnchantSword() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        finisher();
        applyToSelf(new StrengthPower(p, 1));
        if (upgraded && gcombo()) exhaust = false;
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}