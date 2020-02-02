package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.powers.CheatPower;

public class Cheat extends AbstractSneckoCard {

    public final static String ID = makeID("Cheat");

    //stupid intellij stuff SKILL, SELF, RARE

    public Cheat() {
        super(ID,  0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        tags.add(SneckoMod.SNEKPROOF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CheatPower(1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            exhaust = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}