package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.NeutralStance;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class DefensiveTrainingManual extends CustomRelic {

    public static final String ID = ChampMod.makeID("DefensiveTrainingManual");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DefensiveManual.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DefensiveManual.png"));

    public DefensiveTrainingManual() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
