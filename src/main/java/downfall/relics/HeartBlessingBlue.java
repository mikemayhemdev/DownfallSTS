package downfall.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import gremlin.characters.GremlinCharacter;
import gremlin.patches.GremlinEnum;

public class HeartBlessingBlue extends CustomRelic {

    public static final String ID = downfallMod.makeID("HeartBlessingBlue");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/HeartBlessingBlue.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/HeartBlessing.png"));

    public HeartBlessingBlue() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        if (AbstractDungeon.getCurrRoom().monsters.monsters.stream().anyMatch(q -> q.type == AbstractMonster.EnemyType.BOSS)) {
            flash();
            //addToBot(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, 10));
            if (AbstractDungeon.player.chosenClass != GremlinEnum.GREMLIN) {
                this.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 10, 0.0F));
            }
            else {
                ((GremlinCharacter)AbstractDungeon.player).healGremlins(2);
            }
        }
    }
}
