package hermit.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import hermit.characters.hermit;

import java.util.Iterator;

@SpirePatch(
        clz= HandCardSelectScreen.class,
        method="selectHoveredCard"
)

public class SelectScreenPatch {
    public static CardGroup handClone;
    public static CardGroup handCloneb;


    @SpirePostfixPatch
    public static void SelectionPostPatch(HandCardSelectScreen reg)
    {
        if (AbstractDungeon.player.chosenClass == hermit.Enums.HERMIT && (InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()))
        {
            //SelectScreenPatch.ResetHand();
        }
    }

    public static void ResetHand() {
        Iterator var1 = handClone.group.iterator();
        handCloneb = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();

            Iterator var3 = AbstractDungeon.player.hand.group.iterator();

            while(var3.hasNext()) {

                AbstractCard d = (AbstractCard)var3.next();
                if (c.equals(d)) {
                    handCloneb.addToBottom(c);
                    break;
                }
            }

            AbstractDungeon.player.hand.removeCard(c);
        }

        Iterator var2 = handCloneb.group.iterator();

        while(var2.hasNext()) {
            AbstractCard c = (AbstractCard)var2.next();
            AbstractDungeon.player.hand.addToTop(c);
        }

        AbstractDungeon.player.hand.refreshHandLayout();
    }

}
