package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.cards.StanceDanceCrown;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class ChampionCrown extends CustomRelic  {

    public static final String ID = ChampMod.makeID("ChampionCrown");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ChampionCrown.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ChampionCrown.png"));

    public ChampionCrown() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
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
        this.addToTop(new MakeTempCardInHandAction(new StanceDanceCrown(), 1, false));
    }

//    @Override
//    public void atBattleStart() {
//        super.atBattleStart();
//        if (AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID)) {
//            int x = AbstractDungeon.relicRng.random(1);
//            switch (x) {
//                case 0:
//                    addToBot(new ChangeStanceAction(BerserkerStance.STANCE_ID));
//                    break;
//                case 1:
//                    addToBot(new ChangeStanceAction(DefensiveStance.STANCE_ID));
//                    break;
//            }
//        }
//    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
