package automaton.cards;

import automaton.AutomatonMod;
import automaton.powers.VerifyPower;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static automaton.AutomatonMod.makeBetaCardPath;

public class Verify extends AbstractBronzeCard {

    public final static String ID = makeID("Verify");

    //stupid intellij stuff power, self, rare

    public Verify() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        tags.add(BaseModCardTags.FORM);
        isEthereal = true;
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("Verify.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new VerifyPower(1));
    }

    public void upp() {
        this.isEthereal = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}