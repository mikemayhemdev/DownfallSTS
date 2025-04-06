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
        AbstractDungeon.eventList.add(0, GremlinWheelGame_Rest.ID);
        MapRoomNode cur = AbstractDungeon.currMapNode;
        MapRoomNode node = new MapRoomNode(cur.x, cur.y);
        node.room = new CustomEventRoom();
        ArrayList<MapEdge> curEdges = cur.getEdges();

        for (MapEdge edge : curEdges) {
            node.addEdge(edge);
        }

        AbstractDungeon.player.releaseCard();
        AbstractDungeon.overlayMenu.hideCombatPanels();
        AbstractDungeon.previousScreen = null;
        AbstractDungeon.dynamicBanner.hide();
        AbstractDungeon.dungeonMapScreen.closeInstantly();
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.topPanel.unhoverHitboxes();
        AbstractDungeon.fadeIn();
        AbstractDungeon.effectList.clear();
        AbstractDungeon.topLevelEffects.clear();
        AbstractDungeon.topLevelEffectsQueue.clear();
        AbstractDungeon.effectsQueue.clear();
        AbstractDungeon.dungeonMapScreen.dismissable = true;
        AbstractDungeon.nextRoom = node;
        AbstractDungeon.setCurrMapNode(node);
        AbstractDungeon.getCurrRoom().onPlayerEntry();
        AbstractDungeon.scene.nextRoom(node.room);
        AbstractDungeon.rs = node.room.event instanceof AbstractImageEvent ? AbstractDungeon.RenderScene.EVENT : AbstractDungeon.RenderScene.NORMAL;
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

