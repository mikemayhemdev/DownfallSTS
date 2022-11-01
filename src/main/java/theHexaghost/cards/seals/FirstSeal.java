package theHexaghost.cards.seals;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RepairPower;
import theHexaghost.HexaMod;

public class FirstSeal extends AbstractSealCard {

    public final static String ID = makeID("FirstSeal");

    //stupid intellij stuff POWER, SELF, UNCOMMON

    public static final int MAGIC = 7;

    public FirstSeal() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(AbstractCard.CardTags.HEALING);
        HexaMod.loadJokeCardImage(this, "FirstSeal.png");
    }

    @Override
    public void realUse(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new RepairPower(p, magicNumber));
    }
}