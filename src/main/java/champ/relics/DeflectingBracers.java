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

public class DeflectingBracers extends CustomRelic {

    public static final String ID = ChampMod.makeID("DeflectingBracers");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DeflectingBracers.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DeflectingBracers.png"));

    public DeflectingBracers() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
