//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package expansioncontent.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Sozu;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import downfall.util.SelectCardsCenteredAction;

public class ManipulateTimeAction extends AbstractGameAction {
    private final boolean setCostForCombat;

    public ManipulateTimeAction(boolean upgraded) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.setCostForCombat = upgraded;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        if (!p.discardPile.isEmpty()) {
            this.addToTop(new SelectCardsCenteredAction(
                    p.discardPile.group,
                    1,
                    "Choose.",
                    (selectedCards) -> {
                        AbstractCard selectedCard = selectedCards.get(0);
                        p.discardPile.removeCard(selectedCard);
                        p.drawPile.addToBottom(selectedCard);

                        if (setCostForCombat) {
                            selectedCard.modifyCostForCombat(-999);
                        } else {
                            if (selectedCard.cost > 0) {
                                selectedCard.freeToPlayOnce = true;
                            }
                        }

                        selectedCard.unhover();
                        selectedCard.applyPowers();
                    }
            ));
        }

        this.isDone = true;
    }
}
