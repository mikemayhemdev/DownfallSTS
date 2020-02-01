package expansioncontent.powers;


import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import expansioncontent.expansionContentMod;
import theHexaghost.util.TextureLoader;


public class ChronoBoostPlusPower extends TwoAmountPower implements CloneablePowerInterface {
    public static final String POWER_ID = expansionContentMod.makeID("ChronoBoostPlusPower");

    private static final Texture tex84 = TextureLoader.getTexture(expansionContentMod.getModID() + "Resources/images/powers/ChronoBoost84.png");
    private static final Texture tex32 = TextureLoader.getTexture(expansionContentMod.getModID() + "Resources/images/powers/ChronoBoost32.png");


    public ChronoBoostPlusPower(AbstractCreature owner, AbstractCreature source, int amount) {


        this.name = "ChronoBoost";
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();

    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (this.amount2 == 10){
            this.amount2=0;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));

        } else {
            this.amount2++;
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new ChronoBoostPlusPower(this.owner, this.owner, this.amount);
    }

    @Override
    public void updateDescription() {
        description = "After playing " + amount2 + " more cards, gain " + amount + " Strength.";
    }

}




