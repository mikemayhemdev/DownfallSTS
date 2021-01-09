package slimebound.orbs;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeAutoAttack;
import slimebound.vfx.SlimeFlareEffect;


public class ShieldSlime
        extends SpawnedSlime {
    public static final String ID = "Slimebound:ShieldSlime";
    public static final String atlasString = SlimeboundMod.getResourcePath("orbs/shield.atlas");
    public static final String skeletonString = "images/monsters/theBottom/slimeS/skeleton.json";


    public ShieldSlime() {

        super(ID, new Color(0f, 1f, 1.00F, 100F), atlasString, skeletonString, false, false, 1, 0, true, new Color(0F, .4F, 1F, 1), SlimeFlareEffect.OrbFlareColor.LICKING, new Texture("slimeboundResources/SlimeboundImages/orbs/attackDefend.png"));
        this.debuffAmount = 3;
        this.extraFontColor = Color.ROYAL;
        spawnVFX();

    }


    public void updateDescription() {
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1] + (this.debuffAmount) + this.descriptions[2];
    }


    public void activateEffectUnique() {

        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player, this.passiveAmount, AbstractGameAction.AttackEffect.BLUNT_LIGHT, this, false, false, false, 0, false, this.debuffAmount, false));

    }


    public AbstractOrb makeCopy() {
        return new ShieldSlime();
    }
}






