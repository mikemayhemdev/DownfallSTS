package champ.powers;

import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import champ.cards.AbstractChampCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.stances.NeutralStance;
import theHexaghost.HexaMod;
import downfall.util.TextureLoader;

public class GladiatorStylePower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ChampMod.makeID("GladiatorStylePower");

    private static final Texture tex84 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/GladiatorStyle84.png");
    private static final Texture tex32 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/GladiatorStyle32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GladiatorStylePower(final int amount) {
        this.name = NAME;
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
    public void atStartOfTurn() {
        if (AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID)) {
            flash();
            addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
            addToBot(new ApplyPowerAction(owner, owner, new LoseStrengthPower(owner, amount), amount));
            addToBot(new ApplyPowerAction(owner, owner, new DexterityPower(owner, amount), amount));
            addToBot(new ApplyPowerAction(owner, owner, new LoseDexterityPower(owner, amount), amount));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new GladiatorStylePower(amount);
    }
}