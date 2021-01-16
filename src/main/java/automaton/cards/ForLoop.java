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
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            if (effect > 0)
                applyToSelfTop(new CloningPower(effect));
            return true;
        }));
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}