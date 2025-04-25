package slimebound.slimes;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeAutoAttack;
import slimebound.orbs.SpawnedSlime;
import slimebound.vfx.SlimeFlareEffect;


public class Cid
        extends AbstractSlime {
    public static final String ID = "Slimebound:ShieldSlime";
    public static final String atlasString = SlimeboundMod.getResourcePath("orbs/shield.atlas");
    public static final String skeletonString = "images/monsters/theBottom/slimeS/skeleton.json";


    public Cid() {

        super(atlasString, skeletonString);

    }

    public void updateDescription() {
        this.description = this.descriptions[0] + this.attackAmount + this.descriptions[1];
    }


    public void activateEffectUnique() {

    }

}







