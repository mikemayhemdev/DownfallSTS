package slimebound.orbs;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.actions.SlimeAutoAttack;
import slimebound.vfx.SlimeFlareEffect;


public class PoisonSlime
        extends SpawnedSlime {
    public static final String ID = "Slimebound:PoisonSlime";
    public static final String atlasString = "images/monsters/theBottom/slimeS/skeleton.atlas";
    public static final String skeletonString = "images/monsters/theBottom/slimeS/skeleton.json";

    public PoisonSlime() {
        this(false);
    }

    public PoisonSlime(boolean topLevelVFX) {
        super(ID, new Color(.5F, 1.0F, .5F, 100F), atlasString, skeletonString, false, false, 2, 1, true, new Color(.58F, .81F, .35F, 1), SlimeFlareEffect.OrbFlareColor.POISON, new Texture("slimeboundResources/SlimeboundImages/orbs/4.png"));
        this.topSpawnVFX = topLevelVFX;
        spawnVFX();
    }


    public void updateDescription() {

        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1];
    }


    public void activateEffectUnique() {


        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player, this.passiveAmount, AbstractGameAction.AttackEffect.BLUNT_LIGHT, this, false, false, false, 0, false, 0, false, true));

    }


    public AbstractOrb makeCopy() {
        return new PoisonSlime();
    }
}


