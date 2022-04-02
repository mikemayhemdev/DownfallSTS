package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.SuspendAction;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.suspend.SuspendHelper;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class DistantDodge extends AbstractTimeEaterCard {
    public final static String ID = makeID("DistantDodge");
    // intellij stuff skill, self, common, , , 5, 2, , 

    public DistantDodge() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        AbstractCard q = this;
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                SuspendHelper.suspend(q);
                AbstractDungeon.player.limbo.removeCard(q);
            }
        });
    }

    public void upp() {
        upgradeBlock(2);
    }
}