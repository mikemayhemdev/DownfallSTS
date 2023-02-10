package hermit.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.rewards.RewardItem;

// Another patch that can be deleted?

public class RewardItemPatch {
    @SpirePatch(clz = RewardItem.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {})
    public static class GimmeSomeCursesBaby
    {
        /*
        public static void Postfix(RewardItem __instance)
        {
            if (ReflectionHacks.getPrivate(__instance, RewardItem.class,"isBoss"))
                return;

            int i;
            for(i = 0; i < __instance.cards.size(); i++) {
                if (AbstractDungeon.cardRng.random(1,12) == 1 && (AbstractDungeon.player.hasRelic(Memento.ID) || AbstractDungeon.player.hasRelic(ClaspedLocket.ID))) {
                    __instance.cards.set(i, AbstractDungeon.returnRandomCurse());

                    return;
                }
            }
        }
         */
    }

}
