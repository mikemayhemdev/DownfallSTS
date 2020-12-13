//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers;

import basemod.interfaces.CloneablePowerInterface;
import charbosses.powers.general.PoisonProtectionPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.powers.FocusPower;
import downfall.downfallMod;
import theHexaghost.util.TextureLoader;

public class InverseBiasPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = downfallMod.makeID("InverseBias");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/InverseBias84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/InverseBias32.png"));


    public InverseBiasPower(AbstractCreature owner, int setAmount) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.name = NAME;
        this.amount = 5;

        this.updateDescription();
    }

    @Override
    public void atEndOfRound() {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new FocusPower(this.owner, 2), 2));
        this.amount = amount - 1;
        if (this.amount <= 0){
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
        super.atEndOfRound();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }




    @Override
    public AbstractPower makeCopy() {
        return new InverseBiasPower(owner, this.amount);
    }
}
