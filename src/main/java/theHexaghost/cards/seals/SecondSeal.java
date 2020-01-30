package theHexaghost.cards.seals;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.GoldPostCombatPower;
import theHexaghost.powers.SealPostCombatPower;

public class SecondSeal extends AbstractSealCard {

    public final static String ID = makeID("SecondSeal");

    //stupid intellij stuff POWER, SELF, UNCOMMON

    private static final int MAGIC = 8;

    public SecondSeal() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void realUse(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new GoldPostCombatPower(magicNumber));
        AbstractDungeon.getCurrRoom().addGoldToRewards(magicNumber);
    }
}