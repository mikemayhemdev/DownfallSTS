package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;

public class VioletPlumage extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("VioletPlumage");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("VioletPlumage.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("VioletPlumage.png"));

    //Hey, you! Go to VioletPlumagePatch! This relic also uses code from Aspiration, the GitHub and relevant patch is linked there!

    //the relic was reworked because mini black hole had to go to shop because its weird rip old effect
    //violet plumage
    public boolean firstTurn = false;
    public boolean activated = false;

    public VioletPlumage() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atPreBattle() {
        this.beginLongPulse();
        activated = false;
    }

    @Override
    public void atTurnStartPostDraw() {
        if (!(this.grayscale)) {
            if (activated) {
                this.stopPulse();
                this.flash();
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new DrawCardAction(AbstractDungeon.player, 3));
                this.grayscale = true;
            }
        }
        activated = false;
    }

    public void onPlayerEndTurn() {
        if (!(this.grayscale)) {
            if (EnergyPanel.totalCount > 0) {
                activated = true;
            }
        }
    }

    @Override
    public void atBattleStart() {
        this.grayscale = false;
    }

    public void onVictory() {
        this.grayscale = false;
        this.stopPulse();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + 3 + DESCRIPTIONS[1];
    }

}
