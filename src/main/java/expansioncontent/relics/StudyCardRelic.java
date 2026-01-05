package expansioncontent.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import expansioncontent.actions.BossToolboxAction;
import expansioncontent.expansionContentMod;
import downfall.util.TextureLoader;

import static expansioncontent.expansionContentMod.makeRelicOutlinePath;
import static expansioncontent.expansionContentMod.makeRelicPath;

public class StudyCardRelic extends CustomRelic {

    public static final String ID = expansionContentMod.makeID("StudyCardRelic");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("tinybowlerhat.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("tinybowlerhatOutline.png"));

    public StudyCardRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStartPreDraw() {
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new BossToolboxAction(1));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new StudyCardRelic();
    }

}