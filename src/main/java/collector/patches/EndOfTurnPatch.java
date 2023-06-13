package collector.patches;

import basemod.helpers.CardModifierManager;
import collector.cardmods.CollectedCardMod;
import collector.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.Arrays;

public class EndOfTurnPatch {
    @SpirePatch(clz = AbstractRoom.class, method = "endTurn")
    public static class EndTurn {
        @SpirePrefixPatch
        public static void Prefix(AbstractRoom __instance) {
            if (Wiz.isInCombat()) {
                AbstractPlayer p = AbstractDungeon.player;
                for (CardGroup cardGroup : Arrays.asList(p.hand, p.drawPile, p.discardPile)) {
                    for (AbstractCard q : cardGroup.group) {
                        if (CardModifierManager.hasModifier(q, CollectedCardMod.ID)) {
                            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                                @Override
                                public void update() {
                                    isDone = true;
                                    AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(q, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                                    cardGroup.moveToExhaustPile(q);
                                }
                            });
                        }
                    }
                }
            }
        }
    }
}
