package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;
import theHexaghost.actions.RetractAction;
import theHexaghost.powers.PastPower;

import static automaton.AutomatonMod.makeBetaCardPath;

public class GhostOfSpirePast extends AbstractHexaCard {

    public final static String ID = makeID("DevilsDance");

    //Devil's Dance devils dance devil dance

    private static final int MAGIC = 1;

    public GhostOfSpirePast() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "GhostOfSpirePast.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new PastPower(magicNumber));
//        atb(new RetractAction());
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}