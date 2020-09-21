package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class ViciousMockery extends AbstractChampCard {

    public final static String ID = makeID("ViciousMockery");

    //stupid intellij stuff skill, enemy, uncommon

    public ViciousMockery() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        exhaust = true;
        tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        if (gcombo()) {
            if (m.getIntentBaseDmg() > -1) applyToSelf(new DexterityPower(p, 1));
        }
    }

    public void upp() {
        exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}