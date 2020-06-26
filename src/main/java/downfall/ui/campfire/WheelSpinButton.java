package downfall.ui.campfire;

import basemod.CustomEventRoom;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import downfall.downfallMod;
import downfall.events.GremlinWheelGame_Rest;
import downfall.relics.GremlinWheel;
import downfall.util.TextureLoader;
import expansioncontent.expansionContentMod;

import java.util.ArrayList;

public class WheelSpinButton extends AbstractCampfireOption {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(downfallMod.makeID("WheelSpinButton"));
    public static final String[] TEXT = uiStrings.TEXT;

    public WheelSpinButton(boolean bruh) {
        label = TEXT[0];

        this.usable = bruh;
        if (this.usable){
            description = TEXT[1];
        } else {
            description = TEXT[2];
        }
        this.img = TextureLoader.getTexture("downfallResources/images/ui/campfire/wheel.png");

        if (AbstractDungeon.player.hasRelic(GremlinWheel.ID)) {
            GremlinWheel gw = (GremlinWheel)AbstractDungeon.player.getRelic(GremlinWheel.ID);
            gw.justFailed = false;
        }
    }

    public static void doStuff() {
        AbstractDungeon.eventList.add(0, GremlinWheelGame_Rest.ID);// 50
        MapRoomNode cur = AbstractDungeon.currMapNode;// 52
        MapRoomNode node = new MapRoomNode(cur.x, cur.y);// 53
        node.room = new CustomEventRoom();// 54
        ArrayList<MapEdge> curEdges = cur.getEdges();// 56

        for (MapEdge edge : curEdges) {
            node.addEdge(edge);// 58
        }

        AbstractDungeon.player.releaseCard();// 61
        AbstractDungeon.overlayMenu.hideCombatPanels();// 62
        AbstractDungeon.previousScreen = null;// 63
        AbstractDungeon.dynamicBanner.hide();// 64
        AbstractDungeon.dungeonMapScreen.closeInstantly();// 65
        AbstractDungeon.closeCurrentScreen();// 66
        AbstractDungeon.topPanel.unhoverHitboxes();// 67
        AbstractDungeon.fadeIn();// 68
        AbstractDungeon.effectList.clear();// 69
        AbstractDungeon.topLevelEffects.clear();// 70
        AbstractDungeon.topLevelEffectsQueue.clear();// 71
        AbstractDungeon.effectsQueue.clear();// 72
        AbstractDungeon.dungeonMapScreen.dismissable = true;// 73
        AbstractDungeon.nextRoom = node;// 74
        AbstractDungeon.setCurrMapNode(node);// 75
        AbstractDungeon.getCurrRoom().onPlayerEntry();// 76
        AbstractDungeon.scene.nextRoom(node.room);// 77
        AbstractDungeon.rs = node.room.event instanceof AbstractImageEvent ? AbstractDungeon.RenderScene.EVENT : AbstractDungeon.RenderScene.NORMAL;// 78
    }

    @Override
    public void useOption() {
        expansionContentMod.teleportToWheelTime = true;
    }

    @Override
    public void update() {
        float hackScale = (float) ReflectionHacks.getPrivate(this, AbstractCampfireOption.class, "scale");

        if (this.hb.hovered) {

            if (!this.hb.clickStarted) {
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, Settings.scale));
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, Settings.scale));

            } else {
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, 0.9F * Settings.scale));

            }
        } else {
            ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, 0.9F * Settings.scale));
        }
        super.update();
    }
}

