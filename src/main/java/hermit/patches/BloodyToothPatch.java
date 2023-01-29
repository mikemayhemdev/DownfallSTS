package hermit.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hermit.relics.*;

import java.util.Iterator;

public class BloodyToothPatch {
    @SpirePatch(clz=AbstractDungeon.class, method="dungeonTransitionSetup")
    public static class BloodyToothReset
    {
        @SpirePostfixPatch
        public static void Postfix()
        {
            Iterator findPow = AbstractDungeon.player.relics.iterator();

            while(findPow.hasNext())
            {
                AbstractRelic c = (AbstractRelic)findPow.next();
                if (c.relicId.equals(BloodyTooth.ID))
                {
                    c.counter = 0;
                }
            }
        }
    }

}