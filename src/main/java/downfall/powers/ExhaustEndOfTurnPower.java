package downfall.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.downfallMod;
import downfall.monsters.AbstractTotemMonster;
import theHexaghost.util.TextureLoader;

public class ExhaustEndOfTurnPower extends AbstractPower implements CloneablePowerInterface {

    public String POWER_ID = downfallMod.makeID("ExhaustCardsPower");

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/TotemExhaust84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/TotemExhaust32.png"));

    public static boolean CANNOT_END = false;

    public ExhaustEndOfTurnPower(AbstractCreature owner) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.POWER_ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.POWER_ID).NAME;

        this.updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer){
            addToBot(new ExhaustAction(1, false, false, false));
        }
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ExhaustEndOfTurnPower(owner);
    }
}