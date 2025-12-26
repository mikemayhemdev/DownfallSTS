package awakenedOne.actions;

import awakenedOne.cards.Caw;
import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import guardian.orbs.StasisOrb;

import java.util.Iterator;


public class SpellApotheosisAction extends AbstractGameAction {
    public SpellApotheosisAction() {
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            AbstractPlayer p = AbstractDungeon.player;
            this.upgradeAllCardsInGroup(p.hand);
            this.upgradeAllCardsInGroup(p.drawPile);
            this.upgradeAllCardsInGroup(p.discardPile);
            this.upgradeAllCardsInGroup(p.exhaustPile);

            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o instanceof StasisOrb) {
                    if (((StasisOrb) o).stasisCard instanceof AbstractSpellCard) {
                        ((StasisOrb) o).stasisCard.upgrade();
                    }
                }
            }

            this.isDone = true;
        }

    }

    private void upgradeAllCardsInGroup(CardGroup cardGroup) {
        Iterator var2 = cardGroup.group.iterator();

        while(var2.hasNext()) {
            AbstractCard c = (AbstractCard)var2.next();
            if (c.canUpgrade() && c instanceof AbstractSpellCard) {
                if (cardGroup.type == CardGroup.CardGroupType.HAND) {
                    c.superFlash();
                }

                c.upgrade();
                c.applyPowers();
            }
        }

    }
}
