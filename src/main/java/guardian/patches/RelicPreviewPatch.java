package guardian.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.ui.buttons.LargeDialogOptionButton;
import guardian.ui.RelicPreviewButton;

@SpirePatch(clz = GenericEventDialog.class, method = "render")
public class RelicPreviewPatch {
    @SpireInsertPatch(
            rloc = 70
    )
    public static void Insert(GenericEventDialog __instance, SpriteBatch sb) {
        if (!Loader.isModLoaded("halation")) {
            for (LargeDialogOptionButton b : __instance.optionList) {
                if (b instanceof RelicPreviewButton) {
                    RelicPreviewButton rb = (RelicPreviewButton) b;
                    rb.renderRelicPreview(sb);
                }
            }
        }
    }
}