package theHexaghost.cards.seals;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import theHexaghost.powers.PotionPostCombatPower;
import theHexaghost.powers.SealPostCombatPower;

public class FourthSeal extends AbstractSealCard {
    public final static String ID = makeID("FourthSeal");

    public FourthSeal() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void realUse(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new PotionPostCombatPower(1));
    }
}