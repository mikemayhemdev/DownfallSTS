package expansioncontent.powers;


import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnPlayerDeathPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import expansioncontent.expansionContentMod;
import theHexaghost.util.TextureLoader;


public class AwakenDeathPower extends AbstractPower implements OnPlayerDeathPower, CloneablePowerInterface {
    public static final String POWER_ID = expansionContentMod.makeID("AwakenDeathPower");

    private static final Texture tex84 = TextureLoader.getTexture(expansionContentMod.getModID() + "Resources/images/powers/AwakenDeath84.png");
    private static final Texture tex32 = TextureLoader.getTexture(expansionContentMod.getModID() + "Resources/images/powers/AwakenDeath32.png");


    public AwakenDeathPower(AbstractCreature owner, AbstractCreature source, int amount) {


        this.name = "Awakening";
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        this.updateDescription();

    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer abstractPlayer, DamageInfo damageInfo) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_AWAKENEDONE_1"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(this.owner, new IntenseZoomEffect(this.owner.hb.cX, this.owner.hb.cY, true), 0.05F, true));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, AwakenDeathPower.POWER_ID));

        AbstractDungeon.actionManager.addToBottom(new HealAction(abstractPlayer, abstractPlayer, this.amount));

        return false;
    }

    @Override
    public AbstractPower makeCopy() {
        return new AwakenDeathPower(this.owner, this.owner, this.amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

}




