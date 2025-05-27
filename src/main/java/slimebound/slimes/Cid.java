package slimebound.slimes;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeAutoAttack;
import slimebound.orbs.SpawnedSlime;
import slimebound.vfx.SlimeFlareEffect;


public class Cid extends AbstractSlime {
    public static final String ID = "Slimebound:Cid";
//    public static final String atlasString = SlimeboundMod.getResourcePath("orbs/shield.atlas");
//    public static final String skeletonString = "images/monsters/theBottom/slimeS/skeleton.json";

    public Cid() {
        super();
    }

    public void updateDescription() {

    }

    public void activateEffect() {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                att(new DamageCallbackAction(AbstractDungeon.getRandomMonster(), new DamageInfo(null, attackAmount, DamageInfo.DamageType.THORNS), AttackEffect.BLUNT_HEAVY, (dealt) -> {
                    att(new GainBlockAction(AbstractDungeon.player, dealt));
                }));
            }
        });
    }
}







