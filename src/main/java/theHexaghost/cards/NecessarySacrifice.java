package theHexaghost.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.actions.SacrificeAction2;

public class NecessarySacrifice extends AbstractHexaCard {

    public final static String ID = makeID("NecessarySacrifice");

    //worthy sacrifice

    public NecessarySacrifice() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);

        HexaMod.loadJokeCardImage(this, "NecessarySacrifice.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SacrificeAction2(upgraded));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
