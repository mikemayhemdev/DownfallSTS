package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.NeutralStance;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class ChampStancesModRelic extends CustomRelic {

    public static final String ID = ChampMod.makeID("ChampStancesModRelic");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ChampStances.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ChampionCrown.png"));

    public ChampStancesModRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }


    @Override
    public void atBattleStart() {
        super.atBattleStart();
        if (AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID)) {
            int x = AbstractDungeon.cardRandomRng.random(1);
            switch (x) {
                case 0:
                    //SlimeboundMod.logger.info("Switching to Berserker (Mod Relic)");
                        addToBot(new ChangeStanceAction(BerserkerStance.STANCE_ID));
                    break;
                case 1:
                    //SlimeboundMod.logger.info("Switching to Defensive (Mod Relic)");
                        addToBot(new ChangeStanceAction(DefensiveStance.STANCE_ID));
                    break;
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
