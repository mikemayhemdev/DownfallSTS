package automaton.cards;

import automaton.AutomatonMod;
import automaton.powers.HardcodePower;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.AutomatonMod.makeBetaCardPath;

public class Hardcode extends AbstractBronzeCard {

    public final static String ID = makeID("Hardcode");

    //stupid intellij stuff skill, self, rare

    public Hardcode() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        FleetingField.fleeting.set(this, true);
        //  this.tags.add(SneckoMod.BANNEDFORSNECKO);
        this.tags.add(CardTags.HEALING);
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("Hardcode.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new HardcodePower(1, this));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}