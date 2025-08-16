package automaton.patches;

import automaton.AutomatonMod;
import automaton.cards.goodstatus.UsefulSlime;
import automaton.relics.BronzeIdol;
import basemod.devcommands.unlock.Unlock;
import charbosses.cards.AbstractBossCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.MedicalKit;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

@SpirePatch(
        clz = AbstractCard.class,
        method = "makeStatEquivalentCopy"
)
public class StatusReplacePatch {

    @SpirePrefixPatch
    public static SpireReturn<AbstractCard> Prefix(AbstractCard abstractCard) {
        if (abstractCard.type == AbstractCard.CardType.STATUS && !(abstractCard instanceof AbstractBossCard)) {
            if (AbstractDungeon.player != null && CardCrawlGame.isInARun()) {
                if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                    if (AbstractDungeon.player.hasRelic(BronzeIdol.ID)) {
                        if (!abstractCard.hasTag(AutomatonMod.GOOD_STATUS)) {

                            AbstractCard newStatus = AutomatonMod.getGoodStatus(abstractCard);
                                if(AbstractDungeon.player.hasRelic(MedicalKit.ID)&&!(newStatus instanceof UsefulSlime)){
                                    newStatus.cost=-2;
                                    newStatus.costForTurn=-2;
                                    newStatus.isCostModified=true;
                                    newStatus.isCostModifiedForTurn=true;
                                }
                                return SpireReturn.Return(newStatus);
                            }
                        }
                    }
                }
            }
            return SpireReturn.Continue();

        }

    }