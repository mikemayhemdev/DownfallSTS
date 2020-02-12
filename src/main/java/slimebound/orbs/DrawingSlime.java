package slimebound.orbs;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeAutoAttack;
import slimebound.characters.SlimeboundCharacter;
import slimebound.vfx.SlimeFlareEffect;


public class DrawingSlime
        extends SpawnedSlime {
    public static final String ID = "Slimebound:DrawingSlime";
    public static final String atlasString = SlimeboundMod.getResourcePath("orbs/ancient.atlas");
    public static final String skeletonString = "images/monsters/theBottom/slimeAltM/skeleton.json";

    public DrawingSlime() {

        super(ID, new Color(1.0F, 100F / 255F, 100F / 255F, 100F), atlasString, skeletonString, true, true, 3, 0, true, new Color(.45F, .58F, .58F, 1), SlimeFlareEffect.OrbFlareColor.AGGRESSIVE, new Texture("slimeboundResources/SlimeboundImages/orbs/attackBuff.png"));
        this.debuffAmount = 1;
        this.extraFontColor = new Color(.75F, .75F, .75F, 1F);
        spawnVFX();

    }

    public void updateDescription() {
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1] + this.debuffAmount + this.descriptions[2];
    }


    public void activateEffectUnique() {


        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player, this.passiveAmount, AbstractGameAction.AttackEffect.LIGHTNING, this, false, false, false, 0, false, 0, false, false, false, false, false));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
    }


    public AbstractOrb makeCopy() {
        return new DrawingSlime();
    }
}


