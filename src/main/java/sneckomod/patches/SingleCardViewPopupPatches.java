package sneckomod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CtBehavior;
import sneckomod.cards.unknowns.AbstractUnknownCard;


public class SingleCardViewPopupPatches {
    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "updateInput",
            paramtypez = {}

    )
    public static class UpdateInputPatch {
        @SpirePostfixPatch
        public static void __updateInput(SingleCardViewPopup __instance, AbstractCard ___card) {
            if (___card instanceof AbstractUnknownCard) {
                // Allow scroll wheel to roll through Unknown card's previews in the single-card popup
                AbstractUnknownCard unkCard = (AbstractUnknownCard) ___card;
                unkCard.updateInput(true);
            }
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "render",
            paramtypez = {SpriteBatch.class}

    )
    public static class RenderPatch {
        @SpireInsertPatch(
                locator = RenderPatchLocator.class,
                localvars = {"copy"}
        )
        public static void __render(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard ___card, AbstractCard copy) {
            if (copy instanceof AbstractUnknownCard && ___card instanceof AbstractUnknownCard) {
                // Allow preview rotation to work while the 'Show Upgrade' option is checked. The option causes
                // a copy of the card to be created and rendered each frame. So, we need to get the new state
                // of the preview rotation from the copy and move it back to the original.
                ((AbstractUnknownCard) copy).rotationTimer = ((AbstractUnknownCard) ___card).rotationTimer;
                ((AbstractUnknownCard) copy).scrollImpulse = ((AbstractUnknownCard) ___card).scrollImpulse;

            }
        }
    }

    private static class RenderPatchLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.FieldAccessMatcher(SingleCardViewPopup.class, "card");
            int[] lines = LineFinder.findAllInOrder(ctBehavior, matcher);
            return new int[]{lines[lines.length - 1]};  // last access moves 'copy' into 'this.card'
        }
    }
}
