package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.actions.OpenerReduceCostAction;
import champ.powers.UltimateFormPower;
import champ.stances.UltimateStance;
import champ.util.OnOpenerSubscriber;
import champ.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class ChampionCrownUpgraded extends CustomRelic {

    public static final String ID = ChampMod.makeID("ChampionCrownUpgraded");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("UltimateChampionCrown.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ChampionCrown.png"));


    public ChampionCrownUpgraded() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        addToBot(new ChangeStanceAction(UltimateStance.STANCE_ID));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (AbstractDungeon.player.stance instanceof UltimateStance) {
                    ((UltimateStance) AbstractDungeon.player.stance).timeLeft = 2;
                }
            }
        });
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new UltimateFormPower(2), 2));
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
