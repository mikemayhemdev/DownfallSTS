package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import theHexaghost.actions.RetractAction;

public class RetreatToShadows extends AbstractHexaCard {

    public final static String ID = makeID("Incorporeal");

    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = -3;

    public RetreatToShadows() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "RetreatToShadows.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new RetractAction());
        atb(new LoseHPAction(p, p, magicNumber));
        applyToSelf(new IntangiblePlayerPower(p, 1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}