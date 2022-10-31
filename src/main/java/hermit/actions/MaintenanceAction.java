//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hermit.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.Iterator;

public class MaintenanceAction extends AbstractGameAction {
    public MaintenanceAction() {
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
            this.isDone = true;
        }

    }

    private void upgradeAllCardsInGroup(CardGroup cardGroup) {
        Iterator var2 = cardGroup.group.iterator();

        while(var2.hasNext()) {
            AbstractCard c = (AbstractCard)var2.next();
            if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) {
                if (cardGroup.type == CardGroup.CardGroupType.HAND) {
                    c.superFlash();
                }
                if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
                    c.baseDamage+=3;
                    c.upgradedDamage=true;
                }
                else if (c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) {
                    c.baseBlock+=3;
                    c.upgradedBlock=true;
                }
                CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(c.cardID);
                c.timesUpgraded++;
                c.upgraded=true;
                if (c.timesUpgraded>1)
                    c.name = cardStrings.NAME + "+" + c.timesUpgraded;
                else
                    c.name = cardStrings.NAME + "+";
                c.applyPowers();
            }
        }

    }
}

