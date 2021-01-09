package slimebound.orbs;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeAutoAttack;
import slimebound.vfx.SlimeFlareEffect;


public class BronzeSlime
        extends SpawnedSlime {
    public static final String ID = "Slimebound:BronzeSlime";
    public static final String atlasString = SlimeboundMod.getResourcePath("orbs/bronze.atlas");
    public static final String skeletonString = "images/monsters/theBottom/slimeAltM/skeleton.json";

    public BronzeSlime() {

        super(ID, new Color(1.0F, 217F / 255F, 70F / 255F, 100F), atlasString, skeletonString, true, true, 4, 0, false, new Color(.63F, .58F, .41F, 1), SlimeFlareEffect.OrbFlareColor.BRONZE, new Texture("slimeboundResources/SlimeboundImages/orbs/attackDefend.png"));
        this.extraFontColor = Color.ROYAL;
        this.debuffAmount = 4;
        spawnVFX();

    }


    public void updateDescription() {
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1] + (this.debuffAmount) + this.descriptions[2];
    }


    public void activateEffectUnique() {

        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player, this.passiveAmount, AbstractGameAction.AttackEffect.BLUNT_LIGHT, this, false, false, false, 0, true, this.debuffAmount, false));

    }

    public AbstractOrb makeCopy() {
        return new BronzeSlime();
    }
}


