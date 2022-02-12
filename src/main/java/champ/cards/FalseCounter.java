package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import champ.powers.FalseCounterPower;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.UltimateStance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class FalseCounter extends AbstractChampCard {

    public final static String ID = makeID("FalseCounter");

    public FalseCounter() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 5;
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CounterPower(magicNumber));
        applyToSelf(new FalseCounterPower(1));
        finisher();
        postInit();
    }

    public void upp() {
        upgradeMagicNumber(5);
        initializeDescription();
    }
}