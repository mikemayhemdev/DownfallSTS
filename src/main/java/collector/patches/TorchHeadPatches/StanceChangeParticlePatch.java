package collector.patches.TorchHeadPatches;

import collector.CollectorChar;
import collector.TorchChar;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import com.megacrit.cardcrawl.vfx.stance.WrathStanceChangeParticle;

public class StanceChangeParticlePatch {
    @SpirePatch(
            clz = StanceChangeParticleGenerator.class,
            method = SpirePatch.CLASS
    )
    public static class DragonField {
        public static SpireField<Boolean> isDragon = new SpireField<>(() -> false);
    }

    @SpirePatch(clz = StanceChangeParticleGenerator.class, method = SpirePatch.CONSTRUCTOR)
    public static class ParticleGeneratorConstructorPatch {
        @SpirePostfixPatch
        public static void Postfix(StanceChangeParticleGenerator __instance, float x, float y, String stanceId) {
            if (AbstractDungeon.player instanceof TorchChar) {
                DragonField.isDragon.set(__instance, true);
            }
        }
    }

    @SpirePatch(clz = StanceChangeParticleGenerator.class, method = "update")
    public static class ParticleGeneratorRenderPatch {
        public static AbstractPlayer prevPlayer = null;

        @SpirePrefixPatch
        public static void Prefix(StanceChangeParticleGenerator __instance) {
            if (DragonField.isDragon.get(__instance)) {
                if (prevPlayer != null) {
                    System.out.println("BUG - ParticleGeneratorRenderPatch!");
                }
                prevPlayer = AbstractDungeon.player;
                AbstractDungeon.player = ((CollectorChar) AbstractDungeon.player).torch;
            }
        }

        @SpirePostfixPatch
        public static void Postfix(StanceChangeParticleGenerator __instance) {
            if (prevPlayer != null) {
                AbstractDungeon.player = prevPlayer;
                prevPlayer = null;
            }
        }
    }


    @SpirePatch(
            clz = WrathStanceChangeParticle.class,
            method = SpirePatch.CLASS
    )
    public static class WrathDragonField {
        public static SpireField<Boolean> isDragon = new SpireField<>(() -> false);
    }

    @SpirePatch(clz = WrathStanceChangeParticle.class, method = SpirePatch.CONSTRUCTOR)
    public static class WrathParticleConstructorPatch {
        @SpirePostfixPatch
        public static void Postfix(WrathStanceChangeParticle __instance, float playerX) {
            if (AbstractDungeon.player instanceof TorchChar) {
                WrathDragonField.isDragon.set(__instance, true);
            }
        }
    }

    @SpirePatch(clz = WrathStanceChangeParticle.class, method = "render")
    public static class WrathParticleRenderPatch {
        public static AbstractPlayer prevPlayer = null;

        @SpirePrefixPatch
        public static void Prefix(WrathStanceChangeParticle __instance, SpriteBatch sb) {
            if (WrathDragonField.isDragon.get(__instance)) {
                if (prevPlayer != null) {
                    System.out.println("BUG - WrathStanceChangeParticle!");
                }
                prevPlayer = AbstractDungeon.player;
                AbstractDungeon.player = ((CollectorChar) AbstractDungeon.player).torch;
            }
        }

        @SpirePostfixPatch
        public static void Postfix(WrathStanceChangeParticle __instance, SpriteBatch sb) {
            if (prevPlayer != null) {
                AbstractDungeon.player = prevPlayer;
                prevPlayer = null;
            }
        }
    }
}