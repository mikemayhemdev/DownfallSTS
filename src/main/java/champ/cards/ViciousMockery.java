package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import sneckomod.SneckoMod;

public class ViciousMockery extends AbstractChampCard {

    public final static String ID = makeID("ViciousMockery");

    //stupid intellij stuff skill, enemy, uncommon

    public ViciousMockery() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        tags.add(ChampMod.TECHNIQUE);
        baseMagicNumber = magicNumber = 1;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        // tags.add(ChampMod.COMBO);
        // tags.add(ChampMod.COMBOGLADIATOR);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        {
            if (m.getIntentBaseDmg() > -1) applyToSelf(new DexterityPower(p, magicNumber));
        }
    }


    public void upp() {
        //exhaust = false;
        upgradeMagicNumber(1);
    }
}