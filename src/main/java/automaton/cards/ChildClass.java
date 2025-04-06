package automaton.cards;/*
package automaton.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class ChildClass extends AbstractBronzeCard {

    public final static String ID = makeID("ChildClass");

    //stupid intellij stuff skill, self, uncommon

    public ChildClass() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        thisEncodes();
       // this.tags.add(SneckoMod.BANNEDFORSNECKO);
        baseBlock = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void onCompileLast(AbstractCard function, boolean forGameplay) {
        if (function instanceof FunctionCard && forGameplay) {
            int x = 0;
            for (AbstractCard c : ((FunctionCard) function).cards()) {
                x += c.cost;
            }
            atb(new GainEnergyAction(x));
        }
    }

    public void upp() {
        upgradeBlock(2);
    }
}

 */