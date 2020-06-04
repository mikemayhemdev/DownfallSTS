package downfall.ui.campfire;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import downfall.downfallMod;
import downfall.patches.ui.campfire.AddBustKeyButtonPatches;
import downfall.relics.HeartBlessingBlue;
import downfall.relics.HeartBlessingGreen;
import downfall.relics.HeartBlessingRed;
import downfall.util.TextureLoader;
import downfall.vfx.campfire.BustKeyEffect;

public class BustKeyOption extends AbstractCampfireOption {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(downfallMod.makeID("BustKeyButton"));
    public static final String[] TEXT = uiStrings.TEXT;
    private static final int MAXHP_INC = 10;
    private Keys key;

    public BustKeyOption() {
        this(Keys.RUBY);
    }

    public BustKeyOption(Keys key) {
        this.key = key;
        this.description = TEXT[3];
        switch (key) {
            case SAPPHIRE:
                this.label = TEXT[1];
                this.description += TEXT[5];
                this.img = TextureLoader.getTexture(downfallMod.assetPath("images/ui/campfire/sapphire.png"));
                break;
            case EMERALD:
                this.label = TEXT[2];
                this.description += TEXT[6];
                this.img = TextureLoader.getTexture(downfallMod.assetPath("images/ui/campfire/emerald.png"));
                break;
            default:
                this.label = TEXT[0];
                this.description += TEXT[4];
                this.img = TextureLoader.getTexture(downfallMod.assetPath("images/ui/campfire/ruby.png"));
        }

    }

    @Override
    public void useOption() {
        AbstractDungeon.effectList.add(new BustKeyEffect());
        AbstractPlayer p = AbstractDungeon.player;
        switch (key) {
            case SAPPHIRE:
                AddBustKeyButtonPatches.KeyFields.bustedSapphire.set(p, true);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new HeartBlessingBlue());

                break;
            case EMERALD:
                AddBustKeyButtonPatches.KeyFields.bustedEmerald.set(p, true);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new HeartBlessingGreen());
                break;
            default:
                AddBustKeyButtonPatches.KeyFields.bustedRuby.set(p, true);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new HeartBlessingRed());
        }

    }

    public enum Keys {
        RUBY, EMERALD, SAPPHIRE
    }
}
