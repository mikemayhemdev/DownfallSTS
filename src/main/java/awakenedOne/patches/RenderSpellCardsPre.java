package awakenedOne.patches;

import awakenedOne.AwakenedOneChar;
import awakenedOne.actions.ConjureAction;
import awakenedOne.ui.OrbitingSpells;
import awakenedOne.util.Wiz;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(clz = AbstractPlayer.class, method = "render")
public class RenderSpellCardsPre {
    public static void Prefix(AbstractPlayer obj, SpriteBatch sb) {
        if (Wiz.isInCombat() && ((AbstractDungeon.player.chosenClass.equals(AwakenedOneChar.Enums.AWAKENED_ONE)) || Wiz.hasConjure() || ConjureAction.conjuresThisCombat > 0)) {
            OrbitingSpells.prePlayerRender(sb);
        }
    }
}
