package slimebound.orbs;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeAutoAttack;
import slimebound.vfx.SlimeFlareEffect;


public class ProtectorSlime
        extends SpawnedSlime {
    public static final String ID = "Slimebound:ProtectorSlime";
    public static final String atlasString = SlimeboundMod.getResourcePath("orbs/protector.atlas");
    public static final String skeletonString = "images/monsters/theBottom/slimeM/skeleton.json";

    public ProtectorSlime() {

        super(ID, new Color(1.0F, 217F / 255F, 70F / 255F, 100F), atlasString, skeletonString, true, false, 2, 0, true, new Color(.63F, .58F, .41F, 1), SlimeFlareEffect.OrbFlareColor.BRONZE, new Texture("slimeboundResources/SlimeboundImages/orbs/attackDefend.png"));
        this.extraFontColor = Color.ROYAL;
        this.debuffAmount = 6;
        spawnVFX();

    }


    public void updateDescription() {
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1] + (this.debuffAmount) + this.descriptions[2];
    }


    public void activateEffectUnique() {

        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player, this.passiveAmount, AbstractGameAction.AttackEffect.SLASH_VERTICAL, this, false, false, false, 0, false, this.debuffAmount, false));

    }

    public AbstractOrb makeCopy() {
        return new ProtectorSlime();
    }
}


