package hermit.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
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
        method="updateSelectedCards"
)

public class SelectScreenPatch3 {

    @SpireInsertPatch(
            rloc=34,
            localvars={}
    )
    public static void Insert()
    {
        if (AbstractDungeon.player.chosenClass == hermit.Enums.HERMIT) {
            SelectScreenPatch.ResetHand();
        }
    }


}
