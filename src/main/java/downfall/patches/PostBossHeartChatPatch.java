package downfall.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.SpeechTextEffect;
import downfall.downfallMod;
import downfall.vfx.CustomAnimatedNPC;
import downfall.vfx.TopLevelInfiniteSpeechBubble;
import slimebound.SlimeboundMod;

import java.util.Iterator;

public class PostBossHeartChatPatch {

    private static CustomAnimatedNPC heart;
    private static CustomAnimatedNPC behindPlayerPortal;
    private static TopLevelInfiniteSpeechBubble speechBubble;
    private static boolean activated;

    public static class Initialize {

        @SpirePatch(
                clz = TreasureRoomBoss.class,
                method = "onPlayerEntry"
        )
        public static class InitPortals {
            @SpirePostfixPatch
            public static void Postfix(TreasureRoomBoss instance) {
                //SlimeboundMod.logger.info("heart chat patch hit init" + AbstractDungeon.actNum + EvilModeCharacterSelect.evilMode);
                if (AbstractDungeon.actNum == 1 && EvilModeCharacterSelect.evilMode) {
                    //SlimeboundMod.logger.info("heart chat patch hit create act 1");
                    heart = new CustomAnimatedNPC(1600.0F * Settings.scale, AbstractDungeon.floorY + 300.0F * Settings.scale, "images/npcs/heart/skeleton.atlas", "images/npcs/heart/skeleton.json", "idle", true, 0, false, 0.75F);
                    behindPlayerPortal = new CustomAnimatedNPC(AbstractDungeon.player.hb.cX + (450F * Settings.scale), AbstractDungeon.player.hb.cY, "images/npcs/heart/skeleton.atlas", "images/npcs/heart/skeleton.json", "idle", true, 1, true, 1F);
                    heart.changeBorderColor(Color.MAROON);
                    behindPlayerPortal.changeBorderColor(Color.MAROON);
                }
                if (AbstractDungeon.actNum == 2 && EvilModeCharacterSelect.evilMode) {
                    //SlimeboundMod.logger.info("heart chat patch hit create act 2");
                    heart = new CustomAnimatedNPC(1600.0F * Settings.scale, AbstractDungeon.floorY + 300.0F * Settings.scale, "images/npcs/heart/skeleton.atlas", "images/npcs/heart/skeleton.json", "idle", true, 0, false, 0.75F);
                    behindPlayerPortal = new CustomAnimatedNPC(AbstractDungeon.player.hb.cX + (450F * Settings.scale), AbstractDungeon.player.hb.cY, "images/npcs/heart/skeleton.atlas", "images/npcs/heart/skeleton.json", "idle", true, 0, true, 1F);
                    heart.changeBorderColor(Color.MAROON);
                    behindPlayerPortal.changeBorderColor(Color.MAROON);

                }
            }
        }
    }

    public static class Trigger {

        @SpirePatch(
                clz = TreasureRoomBoss.class,
                method = "update"
        )
        public static class TriggerOnUpdate {
            @SpirePostfixPatch
            public static void Postfix(TreasureRoomBoss instance) {
                ////SlimeboundMod.logger.info("heart chat patch hit update");
                if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.BOSS_REWARD && instance.chest.isOpen && heart != null && !activated) {
                    ////SlimeboundMod.logger.info("heart chat patch hit activate");
                    activated = true;
                    int Rand = AbstractDungeon.cardRng.random(0, 4);
                    String msg = CardCrawlGame.languagePack.getCharacterString(downfallMod.makeID("HeartProceed")).TEXT[Rand];
                    if (AbstractDungeon.actNum == 1) {
                        msg = msg + CardCrawlGame.languagePack.getCharacterString(downfallMod.makeID("HeartProceed")).OPTIONS[0];
                    }
                    if (AbstractDungeon.actNum == 2) {
                        msg = msg + CardCrawlGame.languagePack.getCharacterString(downfallMod.makeID("HeartProceed")).OPTIONS[1];
                    }
                    heart.portalRenderActive = true;

                    heart.changeBorderColor(Color.MAROON);
                    behindPlayerPortal.portalRenderActive = true;
                    speechBubble = new TopLevelInfiniteSpeechBubble(heart.skeleton.getX() - (200F * Settings.scale), heart.skeleton.getY() + 150F * Settings.scale, msg);
                    AbstractDungeon.topLevelEffects.add(speechBubble);
                }
                if (activated) {
                    if (heart != null) heart.update();
                    if (behindPlayerPortal != null) behindPlayerPortal.update();
                }
            }
        }
    }

    public static class Renderer {

        @SpirePatch(
                clz = TreasureRoomBoss.class,
                method = "render",
                paramtypez = {SpriteBatch.class}
        )
        public static class Render {
            @SpirePostfixPatch
            public static void Postfix(TreasureRoomBoss instance, SpriteBatch sb) {
                if (activated) {
                    if (heart != null) heart.render(sb);
                    if (behindPlayerPortal != null) behindPlayerPortal.render(sb);
                }
            }
        }
    }

    public static class CleanUp {

        @SpirePatch(
                clz = AbstractRoom.class,
                method = "dispose"
        )
        public static class Dispose {
            @SpirePostfixPatch
            public static void Postfix(AbstractRoom instance) {
                //SlimeboundMod.logger.info("disposing a");
                {
                    if (heart != null) {
                        //SlimeboundMod.logger.info("disposing heart");
                        heart.dispose();
                        heart = null;
                        activated = false;
                    }

                    if (behindPlayerPortal != null){
                        behindPlayerPortal.dispose();
                        behindPlayerPortal = null;
                    }

                    //SlimeboundMod.logger.info("disposing text");
                    Iterator var1 = AbstractDungeon.topLevelEffects.iterator();
                    while(var1.hasNext()) {
                        AbstractGameEffect e = (AbstractGameEffect)var1.next();
                        if (e instanceof TopLevelInfiniteSpeechBubble) {
                            ((TopLevelInfiniteSpeechBubble)e).dismiss();
                            ((TopLevelInfiniteSpeechBubble)e).isDone = true;
                        }
                        if (e instanceof SpeechTextEffect) {
                            e.dispose();
                            e.isDone = true;
                        }

                    }
                }
            }
        }


    }
}


