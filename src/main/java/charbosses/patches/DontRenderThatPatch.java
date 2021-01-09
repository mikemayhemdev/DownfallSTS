package charbosses.patches;


import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Silent.CharBossSilent;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.vfx.combat.PowerExpireTextEffect;


public class DontRenderThatPatch {
    @SpirePatch(
            clz = PowerExpireTextEffect.class,
            method = "render"
    )
    public static class Noooooo {
        @SpirePrefixPatch
        public static SpireReturn Prefix() {
            if (AbstractCharBoss.boss != null){
                if (AbstractCharBoss.boss instanceof CharBossSilent){
                    if (!((CharBossSilent)AbstractCharBoss.boss).foggy) {
                        return SpireReturn.Return(null);
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }
}



