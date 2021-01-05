/*
package automaton.cards;

import automaton.powers.CopyPastePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class CopyPaste extends AbstractBronzeCard {

    public final static String ID = makeID("CopyPaste");

    //stupid intellij stuff skill, self, rare

    public CopyPaste() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CopyPastePower(1));
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}
*/