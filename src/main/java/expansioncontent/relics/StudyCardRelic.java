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
        super(ID, IMG, OUTLINE, RelicTier.DEPRECATED, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        this.counter = -1;
    }

    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        if (this.counter < 2 && this.counter != -1) {
            this.counter++;
            flash();
            if (this.counter == 2) {
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new BossToolboxAction(1));
                this.counter = -1;
            }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new StudyCardRelic();
    }

}