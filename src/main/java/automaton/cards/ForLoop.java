package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.EasyXCostAction;
import automaton.powers.CloningPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class ForLoop extends AbstractBronzeCard {

    public final static String ID = makeID("ForLoop");

    //stupid intellij stuff skill, self, uncommon

    public ForLoop() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        exhaust = true;
        baseMagicNumber = magicNumber = 0;
        AutomatonMod.loadJokeCardImage(this, AutomatonMod.makeBetaCardPath("ForLoop.png"));
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