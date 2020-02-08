package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import guardian.GuardianMod;

@SpirePatch(clz = CancelButton.class, method = "update")
public class SocketGemGridPatchCancelAction {

    @SpirePrefixPatch
    public static void Prefix(CancelButton obj) {
        if (!obj.isHidden) {
            obj.hb.update();
            if (obj.hb.clicked || (InputHelper.pressedEscape || CInputActionSet.cancel.isJustPressed()) && obj.current_x != obj.HIDE_X) {

                if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID && GuardianMod.gridScreenForSockets) {
                    if (!AbstractDungeon.gridSelectScreen.confirmScreenUp) {
                        GuardianMod.logger.info("cancel from grid for sockets");
                        CardGroup gemCards = GuardianMod.getGemCards();
                        GuardianMod.currSocketGemEffect.gemSelect = true;
                        GuardianMod.currSocketGemEffect.socketSelect = false;
                        GuardianMod.gridScreenForSockets = false;
                        GuardianMod.gridScreenForGems = true;
                        GuardianMod.currSocketGemEffect.gemChosen = null;
                        GuardianMod.gridScreenForGems = true;
                        AbstractDungeon.gridSelectScreen.selectedCards.clear();

                        AbstractDungeon.gridSelectScreen.open(gemCards, 1, GuardianMod.currSocketGemEffect.TEXT[3], false, false, true, false);

                    }

                }

                if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID && GuardianMod.gridScreenForGems) {
                    if (!AbstractDungeon.gridSelectScreen.confirmScreenUp) {
                        GuardianMod.logger.info("cancel from grid for gems");
                        GuardianMod.gridScreenForGems = false;
                        GuardianMod.gridScreenForSockets = false;
                        AbstractDungeon.closeCurrentScreen();
                        if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
                            RestRoom r = (RestRoom) AbstractDungeon.getCurrRoom();
                            r.campfireUI.reopen();
                        }


                    }

                }
            }


        }
    }
}