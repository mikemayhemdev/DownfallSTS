package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnRemoveCardFromMasterDeckRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.HermitMod;
import hermit.util.TextureLoader;

import static downfall.patches.EvilModeCharacterSelect.evilMode;
import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class StraightRazor extends CustomRelic implements OnRemoveCardFromMasterDeckRelic {
    public static final String ID = HermitMod.makeID("StraightRazor");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("straight_razor.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("straight_razor_outline.png"));

    public StraightRazor() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void onRemoveCardFromMasterDeck(AbstractCard var1){
        this.flash();
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.player.heal(15);
    }

    public boolean canSpawn() {
        return Settings.isEndless || (AbstractDungeon.floorNum <= 54 && AbstractDungeon.ascensionLevel >= 20) || (AbstractDungeon.floorNum <= 53 && AbstractDungeon.ascensionLevel <= 19);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
