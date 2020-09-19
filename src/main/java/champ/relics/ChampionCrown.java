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

public class ChampionCrown extends CustomRelic {

    public static final String ID = ChampMod.makeID("ChampionCrown");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ChampionCrown.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ChampionCrown.png"));

    public boolean activated = false;

    public ChampionCrown() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        int x = AbstractDungeon.cardRandomRng.random(2);
        switch (x) {
            case 0:
                SlimeboundMod.logger.info("Switching to Berserker (Abstract)");
                addToBot(new ChangeStanceAction(BerserkerStance.STANCE_ID));
                break;
            case 1:
                SlimeboundMod.logger.info("Switching to Gladiator (Abstract)");
                addToBot(new ChangeStanceAction(GladiatorStance.STANCE_ID));
                break;
            case 2:
                SlimeboundMod.logger.info("Switching to Defensive (Abstract)");
                addToBot(new ChangeStanceAction(DefensiveStance.STANCE_ID));
                break;
        }
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        activated = true;
        grayscale = true;
    }

    @Override
    public void onVictory() {
        grayscale = false;
        activated = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
