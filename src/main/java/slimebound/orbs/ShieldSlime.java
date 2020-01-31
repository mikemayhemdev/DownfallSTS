package slimebound.orbs;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.actions.SlimeAutoAttack;
import slimebound.vfx.SlimeFlareEffect;


public class ShieldSlime
        extends SpawnedSlime {
    public static final String ID = "Slimebound:ShieldSlime";


    public ShieldSlime() {

        super(ID, -36, new Color(0f, 1f, 1.00F, 100F), "images/monsters/theBottom/slimeS/skeleton.atlas", "images/monsters/theBottom/slimeS/skeleton.json", "idle", .85F, new Color(.4F, .8F, 1F, 2F), 2, 3, true, new Color(0F, .4F, 1F, 1), SlimeFlareEffect.OrbFlareColor.LICKING, new Texture("slimeboundResources/SlimeboundImages/orbs/attackDefend.png"), "slimeboundResources/SlimeboundImages/orbs/licking.png");
        this.extraFontColor = Color.ROYAL;
        this.debuffAmount = 2;
        spawnVFX();

    }


    public void updateDescription() {
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1] + (this.debuffAmount + this.debuffBonusAmount) + this.descriptions[2];
    }


    public void activateEffectUnique() {

        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player, this.passiveAmount, AbstractGameAction.AttackEffect.BLUNT_LIGHT, this, false, false, false, 0, false, this.debuffAmount + this.debuffBonusAmount, false));

    }


    public AbstractOrb makeCopy() {
        return new ShieldSlime();
    }
}






