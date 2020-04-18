package evilWithin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import evilWithin.vfx.CustomAnimatedNPC;
import evilWithin.vfx.TopLevelInfiniteSpeechBubble;
import slimebound.SlimeboundMod;

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
                SlimeboundMod.logger.info("heart chat patch hit init" + AbstractDungeon.actNum + EvilModeCharacterSelect.evilMode);
                if (AbstractDungeon.actNum == 0 && EvilModeCharacterSelect.evilMode){
                    SlimeboundMod.logger.info("heart chat patch hit create");
                    heart = new CustomAnimatedNPC(1350.0F * Settings.scale, AbstractDungeon.floorY - 30.0F * Settings.scale, "images/npcs/heart/skeleton.atlas", "images/npcs/heart/skeleton.json", "idle", false,0);
                    behindPlayerPortal = new CustomAnimatedNPC(AbstractDungeon.player.hb.cX + (150F * Settings.scale), AbstractDungeon.floorY - 30.0F * Settings.scale, "images/npcs/heart/skeleton.atlas", "images/npcs/heart/skeleton.json", "idle", true,1);
                    //behindPlayerPortal.dontShowHeart = true;
                }
                if (AbstractDungeon.actNum == 1 && EvilModeCharacterSelect.evilMode){
                    SlimeboundMod.logger.info("heart chat patch hit create");
                    heart = new CustomAnimatedNPC(1350.0F * Settings.scale, AbstractDungeon.player.drawY, "images/npcs/heart/skeleton.atlas", "images/npcs/heart/skeleton.json", "idle", false,0);
                    behindPlayerPortal = new CustomAnimatedNPC(AbstractDungeon.player.hb.cY + (150F * Settings.scale), AbstractDungeon.floorY - 30.0F * Settings.scale, "images/npcs/heart/skeleton.atlas", "images/npcs/heart/skeleton.json", "idle", true,0);
                    //behindPlayerPortal.dontShowHeart = true;

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
                SlimeboundMod.logger.info("heart chat patch hit update");
                if (AbstractDungeon.overlayMenu.cancelButton.isHidden && instance.chest.isOpen && heart != null && !activated){
                    SlimeboundMod.logger.info("heart chat patch hit activate");
                    activated = true;
                    int Rand = AbstractDungeon.cardRng.random(0,4);
                    String msg = CardCrawlGame.languagePack.getCharacterString("HeartProceed").TEXT[Rand];
                    if (AbstractDungeon.floorNum == 0){
                        msg = msg + CardCrawlGame.languagePack.getCharacterString("HeartProceed").OPTIONS[0];
                    }
                    if (AbstractDungeon.floorNum == 1){
                        msg = msg + CardCrawlGame.languagePack.getCharacterString("HeartProceed").OPTIONS[1];
                    }
                    heart.portalRenderActive = true;
                    behindPlayerPortal.portalRenderActive = true;
                    speechBubble = new TopLevelInfiniteSpeechBubble(heart.skeleton.getX(), heart.skeleton.getY(), msg);
                    AbstractDungeon.topLevelEffects.add(speechBubble);
                }
                if (activated){
                    heart.update();
                    behindPlayerPortal.update();
                }
            }
        }
    }

    public static void dispose(){
        if (heart != null){
            heart.dispose();
            behindPlayerPortal.dispose();
            speechBubble.dismiss();
            activated = false;
            heart = null;
            speechBubble = null;
            behindPlayerPortal = null;
        }
    }

}


