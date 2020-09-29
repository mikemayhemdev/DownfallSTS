package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.actions.OpenerReduceCostAction;
import champ.stances.UltimateStance;
import champ.util.OnOpenerSubscriber;
import champ.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class ChampionCrownUpgraded extends CustomRelic implements OnOpenerSubscriber {

    public static final String ID = ChampMod.makeID("ChampionCrownUpgraded");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("UltimateChampionCrown.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ChampionCrown.png"));

    private boolean usedThisCombat;

    public ChampionCrownUpgraded() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        usedThisCombat = false;
    }

    @Override
    public void onOpener(boolean fromNeutral) {
        if (usedThisCombat = false){
            usedThisCombat = true;
            addToBot(new ChangeStanceAction(UltimateStance.STANCE_ID));
        }
    }

    @Override
    public void onVictory() {
        usedThisCombat = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
