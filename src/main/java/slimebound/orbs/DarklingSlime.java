package slimebound.orbs;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeAutoAttack;
import slimebound.vfx.SlimeFlareEffect;


public class DarklingSlime
        extends SpawnedSlime {
    public static final String ID = "Slimebound:DarklingSlime";
    public static final String atlasString = "images/monsters/theForest/darkling/skeleton.atlas";
    public static final String skeletonString = "images/monsters/theForest/darkling/skeleton.json";

    public DarklingSlime() {

        super(ID, new Color(0F, 0F / 255F, 0F / 255F, 100F), atlasString, skeletonString, false, true, 3, 3, true, new Color(0F, 0F, 0F, 1), SlimeFlareEffect.OrbFlareColor.AGGRESSIVE, new Texture("slimeboundResources/SlimeboundImages/orbs/3.png"));
        spawnVFX();

    }

    public void updateDescription() {
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1];
    }


    public void activateEffectUnique() {


        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player, this.passiveAmount, AbstractGameAction.AttackEffect.BLUNT_LIGHT, this, false, false, false, 0, false, 0, false));

    }


    public AbstractOrb makeCopy() {
        return new DarklingSlime();
    }
}


