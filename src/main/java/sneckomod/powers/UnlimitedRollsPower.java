package sneckomod.powers;

import basemod.helpers.CardModifierManager;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.cardmods.EtherealMod;
import sneckomod.SneckoMod;
import sneckomod.cards.SoulRoll;
import downfall.cardmods.ExhaustMod;
import downfall.util.TextureLoader;

public class UnlimitedRollsPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = SneckoMod.makeID("UnlimitedRollsPower");

    private static final Texture tex84 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/Rolls84.png");
    private static final Texture tex32 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/Rolls32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public UnlimitedRollsPower() {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = 1;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    public void atStartOfTurnPostDraw() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {// 27
            this.flash();// 28

            AbstractCard card = new SoulRoll();
            card.exhaust = true;
            CardModifierManager.addModifier(card, new EtherealMod());
            CardModifierManager.addModifier(card, new ExhaustMod());

            this.addToBot(new MakeTempCardInHandAction(card, amount, false));// 30 32 33
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }


    @Override
    public AbstractPower makeCopy() {
        return new UnlimitedRollsPower();
    }
}