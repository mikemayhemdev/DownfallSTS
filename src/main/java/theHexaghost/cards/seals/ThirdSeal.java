package theHexaghost.cards.seals;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.TheHexaghost;
import theHexaghost.util.SealCommonReward;

public class ThirdSeal extends AbstractSealCard {
    public final static String ID = makeID("ThirdSeal");

    public ThirdSeal() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void realUse(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.getCurrRoom().addCardReward(new SealCommonReward(AbstractDungeon.player.getCardColor()));
    }
}