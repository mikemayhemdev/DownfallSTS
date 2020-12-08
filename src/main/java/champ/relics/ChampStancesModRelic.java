package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.actions.OpenerReduceCostAction;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.GladiatorStance;
import champ.stances.UltimateStance;
import champ.util.OnOpenerSubscriber;
import champ.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.NeutralStance;
import slimebound.SlimeboundMod;

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
            int x = AbstractDungeon.cardRandomRng.random(2);
            switch (x) {
                case 0:
                    SlimeboundMod.logger.info("Switching to Berserker (Mod Relic)");
                        addToBot(new ChangeStanceAction(BerserkerStance.STANCE_ID));
                    break;
                case 1:
                    SlimeboundMod.logger.info("Switching to Gladiator (Mod Relic)");
                        addToBot(new ChangeStanceAction(GladiatorStance.STANCE_ID));
                    break;
                case 2:
                    SlimeboundMod.logger.info("Switching to Defensive (Mod Relic)");
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
