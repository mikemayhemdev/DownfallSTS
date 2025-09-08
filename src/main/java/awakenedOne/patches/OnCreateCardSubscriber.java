package awakenedOne.patches;

import automaton.cards.goodstatus.IntoTheVoid;
import awakenedOne.AwakenedTextHelper;
import awakenedOne.cards.AbstractAwakenedCard;
import awakenedOne.powers.SongOfSorrowPower;
import awakenedOne.relics.TomeOfPortalmancy;
import champ.relics.RageAmulet;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static awakenedOne.AwakenedOneMod.ACTIVECHANT;

@SpirePatch(clz = StSLib.class, method = "onCreateCard", paramtypez = {AbstractCard.class})
public class OnCreateCardSubscriber {
    public static int CardsCreatedThisCombat = 0;
    public static int CardsCreatedThisTurn = 0;
    public static boolean VoidCreatedThisTurn = false;

    @SpirePostfixPatch
    public static void onCreateCard(AbstractCard c) {
        CardsCreatedThisCombat++;
        CardsCreatedThisTurn++;
        if (!AbstractDungeon.actionManager.turnHasEnded) {

            if (c instanceof VoidCard || c instanceof IntoTheVoid) {
                if (!VoidCreatedThisTurn) {
                    //             VoidCreatedThisTurn = true;
                    //                    if (AbstractDungeon.player.hasPower(SongOfSorrowPower.POWER_ID)) {
                    //                        for (int i = 0; i < AbstractDungeon.player.drawPile.size(); ) {
                    //                            AbstractCard q = AbstractDungeon.player.drawPile.group.get(i);
                    //                            if (c.uuid == q.uuid) {
                    //                                AbstractDungeon.player.drawPile.removeCard(c);
                    //                                AbstractDungeon.player.limbo.addToTop(c);
                    //                                atb(new ExhaustSpecificCardAction(c, AbstractDungeon.player.limbo));
                    //                                atb(new WaitAction(0.1F));
                    //                            } else {
                    //                                i++;
                    //                            }
                    //                        }
                    //
                    //                        for (int i = 0; i < AbstractDungeon.player.discardPile.size(); ) {
                    //                            AbstractCard q = AbstractDungeon.player.discardPile.group.get(i);
                    //                            if (c.uuid == q.uuid) {
                    //                                AbstractDungeon.player.discardPile.removeCard(c);
                    //                                AbstractDungeon.player.limbo.addToTop(c);
                    //                                atb(new ExhaustSpecificCardAction(c, AbstractDungeon.player.limbo));
                    //                                atb(new WaitAction(0.1F));
                    //                            } else {
                    //                                i++;
                    //                            }
                    //                        }
                    //
                    //                        for (int i = 0; i < AbstractDungeon.player.hand.size(); ) {
                    //                            AbstractCard q = AbstractDungeon.player.hand.group.get(i);
                    //                            if (c.uuid == q.uuid) {
                    //                                AbstractDungeon.player.hand.removeCard(c);
                    //                                AbstractDungeon.player.limbo.addToTop(c);
                    //                                atb(new ExhaustSpecificCardAction(q, AbstractDungeon.player.limbo));
                    //                                atb(new WaitAction(0.1F));
                    //                            }
                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        if (r instanceof TomeOfPortalmancy) {
                            ((TomeOfPortalmancy) r).onSpecificTrigger();
                        }
                    }
                    if (AbstractDungeon.player.hasPower(SongOfSorrowPower.POWER_ID)) {
                        AbstractDungeon.player.getPower(SongOfSorrowPower.POWER_ID).onSpecificTrigger();
                    }
                }
            }
        }
        if (c.hasTag(ACTIVECHANT)) {
            AwakenedTextHelper.colorCombos((AbstractAwakenedCard) c, false);
            c.initializeDescription();
        }
    }
}