package evilWithin.ui.campfire;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import evilWithin.EvilWithinMod;
import evilWithin.patches.ui.campfire.AddBustKeyButtonPatches;
import evilWithin.util.TextureLoader;
import evilWithin.vfx.campfire.BustKeyEffect;

public class BustKeyOption extends AbstractCampfireOption {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(EvilWithinMod.makeID("BustKeyButton"));
    public static final String[] TEXT = uiStrings.TEXT;
    private Keys key;
    private static final int MAXHP_INC = 10;

    public enum Keys {
        RUBY, EMERALD, SAPPHIRE
    }

    public BustKeyOption() { this(Keys.RUBY); }

    public BustKeyOption(Keys key) {
        this.key = key;
        this.description = TEXT[3];
        switch (key) {
            case SAPPHIRE:
                this.label = TEXT[1];
                this.description+= TEXT[5];
                this.img = TextureLoader.getTexture(EvilWithinMod.assetPath("images/ui/campfire/sapphire.png"));
                break;
            case EMERALD:
                this.label = TEXT[2];
                this.description+= TEXT[6];
                this.img = TextureLoader.getTexture(EvilWithinMod.assetPath("images/ui/campfire/emerald.png"));
                break;
            default:
                this.label = TEXT[0];
                this.description+= TEXT[4];
                this.img = TextureLoader.getTexture(EvilWithinMod.assetPath("images/ui/campfire/ruby.png"));
        }
        this.description += "\n"+TEXT[7];
    }

    @Override
    public void useOption() {
        AbstractDungeon.effectList.add(new BustKeyEffect());
        AbstractPlayer p = AbstractDungeon.player;
        switch(key) {
            case SAPPHIRE:
                AddBustKeyButtonPatches.KeyFields.bustedSapphire.set(p, true);
                p.increaseMaxHp(MAXHP_INC, false);
                break;
            case EMERALD:
                AddBustKeyButtonPatches.KeyFields.bustedEmerald.set(p, true);
                break;
            default:
                AddBustKeyButtonPatches.KeyFields.bustedRuby.set(p, true);
        }
        if(AddBustKeyButtonPatches.KeyFields.bustedRuby.get(p) && AddBustKeyButtonPatches.KeyFields.bustedEmerald.get(p) && AddBustKeyButtonPatches.KeyFields.bustedSapphire.get(p)) {
            p.increaseMaxHp(MAXHP_INC, false);
        }
    }
}
