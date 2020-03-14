package charbosses.actions.unique;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class EnemyCrushJointsAction extends AbstractGameAction {
    private AbstractPlayer m;
    private int magicNumber;

    public EnemyCrushJointsAction(AbstractPlayer monster, int vulnAmount) {
        this.m = monster;
        this.magicNumber = vulnAmount;
    }

    public void update() {
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2 && ((AbstractCard)AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2)).type == CardType.SKILL) {
            this.addToTop(new ApplyPowerAction(this.m, AbstractCharBoss.boss, new VulnerablePower(this.m, this.magicNumber, true), this.magicNumber));
        }

        this.isDone = true;
    }
}
