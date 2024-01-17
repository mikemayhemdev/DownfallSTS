package theHexaghost.cards.seals;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.powers.GoldPostCombatPower;

public class SecondSeal extends AbstractSealCard {

    public final static String ID = makeID("SecondSeal");

    //stupid intellij stuff POWER, SELF, UNCOMMON

    public static final int MAGIC = 13;

    public SecondSeal() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        HexaMod.loadJokeCardImage(this, "SecondSeal.png");
    }

    public void realUse(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new GoldPostCombatPower(magicNumber));
    }
}