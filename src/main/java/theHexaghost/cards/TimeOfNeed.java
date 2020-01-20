package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TimeOfNeed extends AbstractHexaCard {

    public final static String ID = makeID("TimeOfNeed");

    //stupid intellij stuff SKILL, SELF, RARE

    public TimeOfNeed() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        selfRetain = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER).makeCopy();// 32
        q.freeToPlayOnce = true;// 33
        this.addToBot(new MakeTempCardInHandAction(q, true));// 34
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}