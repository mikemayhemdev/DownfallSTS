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
        this.tags.add(SneckoMod.BANNEDFORSNECKO); //I'm rebanning this, why can you see this as Snecko?
        tags.add(BaseModCardTags.FORM);
        baseMagicNumber = magicNumber = 1;
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("Verify.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new VerifyPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}