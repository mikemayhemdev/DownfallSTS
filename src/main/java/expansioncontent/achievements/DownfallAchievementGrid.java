package expansioncontent.achievements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.downfallMod;

import java.util.ArrayList;

public class DownfallAchievementGrid {
    public ArrayList<DownfallAchievementItem> items = new ArrayList<>();
    private static final float SPACING = 200.0F * Settings.scale;
    private static final int ITEMS_PER_ROW = 5;

    public DownfallAchievementGrid() {
        DownfallAchievementItem.atlas = new TextureAtlas(Gdx.files.internal("expansioncontentResources/images/achievements/DownfallAchievements.atlas"));
        loadAchievement("BULLY");
        loadAchievement("CORRUPTED");
        loadAchievement("UPRISE");
        loadAchievement("LONGSHOT");
        loadAchievement("BOUNTY_HUNTER");
        loadAchievement("WIELDER_OF_WOE");
        loadAchievement("GORGED");
        loadAchievement("FOR_THE_BOSS");
        loadAchievement("PAYMENT_RECEIVED");
        loadAchievement("HYPER_ACCELERATION");
        loadAchievement("IMPENETRABLE");
        loadAchievement("BEJEWELED");
        loadAchievement("HEXABURN");
        loadAchievement("GHOSTLY");
        loadAchievement("THE_BROKEN_SEAL");
        loadAchievement("MY_BELT");
        loadAchievement("I_AM_THE_ONE");
        loadAchievement("OVER_OVERKILL");
        loadAchievement("TRIPLE_SETTER");
        loadAchievement("MECHANICAL_GAUNTLET");
        loadAchievement("CODERS_BLOCK");
        loadAchievement("PICKPOCKET_100");
        loadAchievement("GLASS_BONES");
        loadAchievement("OUR_TRUE_FORM");
        loadAchievement("NOODLE_CODE");
        loadAchievement("MASTER_OF_CONFUSION");
        loadAchievement("UNBOUND");
        loadAchievement("SAYONARA");
        loadAchievement("THE_BIGGER_THEY_ARE");
        loadAchievement("HOARDER");
        loadAchievement("TOPAZ");
        loadAchievement("JADE");
        loadAchievement("CITRINE");
        loadAchievement("TANZANITE");
        loadAchievement("KYANITE");
        loadAchievement("RUTILE");
        loadAchievement("SPINEL");
        loadAchievement("CHRYSOCOLLA");
        loadAchievement("MALACHITE");
        loadAchievement("TOPAZ+");
        loadAchievement("JADE+");
        loadAchievement("CITRINE+");
        loadAchievement("TANZANITE+");
        loadAchievement("KYANITE+");
        loadAchievement("RUTILE+");
        loadAchievement("SPINEL+");
        loadAchievement("CHRYSOCOLLA+");
        loadAchievement("MALACHITE+");
        loadAchievement("EVIL_ONE");
    }

    private void loadAchievement(String id) {
        String fullId = downfallMod.makeID(id);
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(fullId);
        String name = uiStrings.TEXT[0];
        String description = uiStrings.TEXT[1];
        TextureAtlas.AtlasRegion AchievementImageUnlocked = DownfallAchievementItem.atlas.findRegion("unlocked/" + id);
        TextureAtlas.AtlasRegion AchievementImageLocked = DownfallAchievementItem.atlas.findRegion("locked/" + id);
        items.add(new DownfallAchievementItem(name, description, fullId, AchievementImageUnlocked, AchievementImageLocked));
    }

    public void updateAchievementStatus() {
        for (DownfallAchievementItem item : items) {
            String achievementKey = item.getKey();
            boolean isUnlocked = UnlockTracker.isAchievementUnlocked(achievementKey);
            item.isUnlocked = isUnlocked;
            item.reloadImg();
        }
    }

    public void render(SpriteBatch sb, float renderY) {
        for (int i = 0; i < items.size(); ++i) {
            items.get(i).render(sb, 560.0F * Settings.scale + (i % ITEMS_PER_ROW) * SPACING, renderY - (i / ITEMS_PER_ROW) * SPACING + 680.0F * Settings.yScale);
        }
    }

    public float calculateHeight() {
        int numRows = (items.size() + ITEMS_PER_ROW - 1) / ITEMS_PER_ROW;
        return numRows * SPACING + 50.0F * Settings.scale;
    }

    public void update() {
        updateAchievementStatus();
        for (DownfallAchievementItem item : items) {
            item.update();
        }
    }

    public boolean areAllAchievementsUnlockedExceptEvilOne() {
        for (DownfallAchievementItem item : items) {
            if (!item.isUnlocked && !item.getKey().equals(downfallMod.makeID("EVIL_ONE"))) {
                return false;
            }
        }
        return true;
    }

}