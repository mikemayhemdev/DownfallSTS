package hermit.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import hermit.characters.hermit;

@SpirePatch(
        clz= HandCardSelectScreen.class,
        method="updateControllerInput"
)

public class SelectScreenPatch4 {

    @SpirePostfixPatch
    public static void SelectionPostPatch(HandCardSelectScreen reg)
    {
        if (AbstractDungeon.player.chosenClass == hermit.Enums.HERMIT && (InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed())) {
            SelectScreenPatch.ResetHand();
        }
    }


}
