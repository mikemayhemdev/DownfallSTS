package slimebound.slimes;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeAutoAttack;
import slimebound.orbs.SpawnedSlime;
import slimebound.vfx.SlimeFlareEffect;


public class Pike extends AbstractSlime {
    public static final String ID = "Slimebound:Pike";

    public Pike() {
        super(SlimeboundMod.getResourcePath("orbs/attack.atlas"), "images/monsters/theBottom/slimeAltS/skeleton.json");
    }

    public void updateDescription() {

    }


    public void activateEffect() {
        for (int i = 0; i < 2; i++) {
            atb(new DamageRandomEnemyAction(new DamageInfo(null, attackAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }
}


