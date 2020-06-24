package downfall.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_M;
import com.megacrit.cardcrawl.monsters.exordium.LouseDefensive;
import com.megacrit.cardcrawl.monsters.exordium.LouseNormal;
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_M;
import downfall.monsters.LooterAlt;

import java.util.ArrayList;

//All this just to override Looter to LooterAlt in the whacko one-off randomized encounter.

@SpirePatch(clz = MonsterHelper.class, method = "bottomHumanoid")
public class ExordiumThugsPatch {

    public static MonsterGroup Postfix(MonsterGroup p) {
        if (EvilModeCharacterSelect.evilMode) {
            AbstractMonster[] monsters = new AbstractMonster[2];
            monsters[0] = bottomGetWeakWildlife(randomXOffset(-160.0F), randomYOffset(20.0F));
            monsters[1] = new LooterAlt(randomXOffset(130.0F), randomYOffset(20.0F));
            return new MonsterGroup(monsters);
        }
        return p;
    }

    private static AbstractMonster bottomGetWeakWildlife(float x, float y) {
        ArrayList<AbstractMonster> monsters = new ArrayList<>();
        if (AbstractDungeon.miscRng.randomBoolean()) {
            monsters.add(new LouseNormal(x, y));
        }
        monsters.add(new LouseDefensive(x, y));
        monsters.add(new SpikeSlime_M(x, y));
        monsters.add(new AcidSlime_M(x, y));

        return (AbstractMonster) monsters.get(AbstractDungeon.miscRng.random(0, monsters.size() - 1));
    }

    private static float randomYOffset(float y) {
        return y + MathUtils.random(-20.0F, 20.0F);
    }

    private static float randomXOffset(float x) {
        return x + MathUtils.random(-20.0F, 20.0F);
    }
}