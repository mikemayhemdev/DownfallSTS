package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.actions.OpenerReduceCostAction;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.UltimateStance;
import champ.util.OnOpenerSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class ChampionCrown extends CustomRelic  {

    public static final String ID = ChampMod.makeID("ChampionCrown");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ChampionCrown.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ChampionCrown.png"));

    public ChampionCrown() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    /*
    @Override
    public void onChangeStance(AbstractStance prevStance, AbstractStance newStance) {
        if (!newStance.ID.equals(NeutralStance.STANCE_ID)) {
            flash();
            addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, 3, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

     */
    @Override
    public void atBattleStart() {
        super.atBattleStart();
       // AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
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
