package automaton.cards;

import automaton.actions.EasyXCostAction;
import automaton.powers.CloningPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ForLoop extends AbstractBronzeCard {

    public final static String ID = makeID("ForLoop");

    //stupid intellij stuff skill, self, uncommon

    public ForLoop() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        //   this.tags.add(SneckoMod.BANNEDFORSNECKO);
        exhaust = true;
        baseMagicNumber = magicNumber = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            if (effect + params[0] > 0)
                applyToSelfTop(new CloningPower(effect + params[0]));
            return true;
        }, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}