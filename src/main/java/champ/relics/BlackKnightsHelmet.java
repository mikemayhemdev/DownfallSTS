package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.GladiatorStance;
import champ.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import slimebound.SlimeboundMod;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class BlackKnightsHelmet extends CustomRelic {

    public static final String ID = ChampMod.makeID("BlackKnightHelmet");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("WarlordsHelmet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("WarlordsHelmet.png"));

    public boolean activated = false;

    public BlackKnightsHelmet() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
