package theHexaghost.cards.seals;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.util.SealCommonReward;
import theHexaghost.util.SealSealReward;

public class FifthSeal extends AbstractSealCard {
    public final static String ID = makeID("FifthSeal");

    public FifthSeal() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void realUse(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.getCurrRoom().addCardReward(new SealSealReward(AbstractDungeon.player.getCardColor()));
    }
}