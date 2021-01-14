package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.actions.OpenerReduceCostAction;
import champ.util.OnOpenerSubscriber;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.NeutralStance;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class FightingForDummies extends CustomRelic {

    public static final String ID = ChampMod.makeID("FightingForDummies");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("FightingForDummies.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("FightingForDummies.png"));

    public FightingForDummies() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayerEndTurn() {
        if (AbstractDungeon.player.stance instanceof NeutralStance){
            addToBot(new GainBlockAction(AbstractDungeon.player,6));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
