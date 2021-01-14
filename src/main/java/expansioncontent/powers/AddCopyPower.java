package expansioncontent.powers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import expansioncontent.expansionContentMod;
import downfall.util.TextureLoader;


public class AddCopyPower extends AbstractPower implements NonStackablePower {
    public static final String POWER_ID = expansionContentMod.makeID("AddCopyPower");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private AbstractCard held;

    public AddCopyPower(int amount, AbstractCard held, String text84, String text32) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;


        Texture tex84 = TextureLoader.getTexture(text84);
        Texture tex32 = TextureLoader.getTexture(text32);

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.held = held;

        this.updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new ReducePowerAction(owner, owner, this, 1));
        addToBot(new MakeTempCardInHandAction(held.makeStatEquivalentCopy()));
    }

    @Override
    public void updateDescription() {
        if (held == null) {
            description = "???";
        } else {
            description = DESCRIPTIONS[0] + held.name + DESCRIPTIONS[1];
        }
    }

}




