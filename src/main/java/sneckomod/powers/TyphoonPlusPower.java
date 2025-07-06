package sneckomod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;
import sneckomod.actions.PlayTyphoonAction;
import sneckomod.cards.PoisonParadise;
import sneckomod.cards.TyphoonFang;
import com.megacrit.cardcrawl.core.Settings;
import sneckomod.relics.D8;

import static sneckomod.SneckoMod.NO_TYPHOON;

public class TyphoonPlusPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = SneckoMod.makeID("TyphoonPlusPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/DemisePlus84.png");
    private static final Texture tex32 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/DemisePlus32.png");

    //this is a near identical copy of TyphoonPower, so held together with the same amount of duct tape and prayers

    public TyphoonPlusPower(int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;  // Store the number of stacks
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    public boolean isOverflowActive(AbstractCard source) {
        boolean OVERFLOW = false;
        if (source.hasTag(SneckoMod.OVERFLOW)) {
            if (AbstractDungeon.player.hand.size() > 5 || (AbstractDungeon.player.hasPower(CheatPower.POWER_ID))) {
                OVERFLOW = true;
            }

            if (source.hasTag(NO_TYPHOON)) {
                return false;
            }

            if (AbstractDungeon.player.hasRelic(D8.ID)) {
                D8 d8Relic = (D8) AbstractDungeon.player.getRelic(D8.ID);
                if (d8Relic != null && d8Relic.card != null) {
                    if (d8Relic.card.uuid.equals(source.uuid)) {
                        OVERFLOW = true;
                    }
                }
            }
        }

        return OVERFLOW;
    }


    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!isOverflowActive(card) || card.hasTag(NO_TYPHOON)) {
            return;
        }

        for (int i = 0; i < this.amount; i++) {
            this.addToBot(new PlayTyphoonAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng), true, true));
        }
    }


    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            addToBot(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        }
        if (this.amount != 1) {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }


    @Override
    public AbstractPower makeCopy() {
        return new TyphoonPlusPower(this.amount);
    }
}
