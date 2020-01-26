package slimebound.orbs;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.actions.SlimeAutoAttack;
import slimebound.vfx.SlimeFlareEffect;


public class zzzDebuffSlime
        extends SpawnedSlime {
    public static final String ID = "Slimebound:ShieldSlime";


    public zzzDebuffSlime() {

        super(ID, -32,new Color (1.0F,1.0F,0F,100F),"images/monsters/theBottom/slimeS/skeleton.atlas","images/monsters/theBottom/slimeS/skeleton.json","idle",.85F,new Color(.5f,0.5F,0.9F,2F),2, 3,true, new Color(.4F, .4F, .8F, 1), SlimeFlareEffect.OrbFlareColor.LICKING, new Texture("slimeboundResources/SlimeboundImages/orbs/attackDebuff.png"), "slimeboundResources/SlimeboundImages/orbs/licking.png");
        this.extraFontColor = Color.GOLDENROD;
        this.debuffAmount=1;

    }


    public void updateDescription() {
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1];
    }


    public void activateEffectUnique() {




        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player,this.passiveAmount, AbstractGameAction.AttackEffect.BLUNT_LIGHT,this,false,false,true,1,false,0,false));


    }


    public AbstractOrb makeCopy() {
        return new zzzDebuffSlime();
    }
}






