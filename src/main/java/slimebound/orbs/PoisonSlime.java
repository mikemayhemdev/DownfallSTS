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

    public PoisonSlime() {
        this(false);
    }

    public PoisonSlime(boolean topLevelVFX) {
        super(ID, -36, new Color(.5F, 1.0F, .5F, 100F), "images/monsters/theBottom/slimeS/skeleton.atlas", "images/monsters/theBottom/slimeS/skeleton.json", "idle", .85F, new Color(0.6F, .9F, .6F, 2F), 2, 1, true, new Color(.58F, .81F, .35F, 1), SlimeFlareEffect.OrbFlareColor.POISON, new Texture("slimeboundResources/SlimeboundImages/orbs/debuff1.png"), "slimeboundResources/SlimeboundImages/orbs/poisonous.png");
        this.extraFontColor = Color.FOREST;
        this.debuffAmount = 1;
        this.topSpawnVFX = topLevelVFX;
        spawnVFX();
    }


    public void updateDescription() {

        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1] + (this.debuffAmount + this.debuffBonusAmount) + this.descriptions[2];
    }


    public void activateEffectUnique() {


        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player, this.passiveAmount, AbstractGameAction.AttackEffect.BLUNT_LIGHT, this, true, false, false, 1 + this.debuffBonusAmount, false, 0, false));

    }


    public AbstractOrb makeCopy() {
        return new PoisonSlime();
    }
}


