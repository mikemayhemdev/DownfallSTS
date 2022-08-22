package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.EasyXCostAction;
import automaton.powers.CloningPower;
import automaton.powers.NextFunctionDupedPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ForLoop extends AbstractBronzeCard {

    public final static String ID = makeID("ForLoop");

    //stupid intellij stuff skill, self, uncommon

    public ForLoop() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        //   this.tags.add(SneckoMod.BANNEDFORSNECKO);
        exhaust = true;
        baseMagicNumber = magicNumber = 0;
        AutomatonMod.loadJokeCardImage(this, AutomatonMod.makeBetaCardPath("ForLoop.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new NextFunctionDupedPower(1));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}