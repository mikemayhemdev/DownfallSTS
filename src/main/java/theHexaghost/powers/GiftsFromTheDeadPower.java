package theHexaghost.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import theHexaghost.HexaMod;
import downfall.util.TextureLoader;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GiftsFromTheDeadPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = HexaMod.makeID("GiftsFromTheDeadPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/GiftsFromTheDead84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/GiftsFromTheDead32.png");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GiftsFromTheDeadPower(final int amount) {
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
    public void atStartOfTurnPostDraw() {
        flash();
        addToBot(new AbstractGameAction() {
            { startDuration = duration = 1.5f; }
            @Override
            public void update() {
                if (duration == startDuration) {
                    isDone = true;
                    for (int i = 0; i < GiftsFromTheDeadPower.this.amount; i++) {
                        if (!AbstractDungeon.player.exhaustPile.isEmpty()) {
                            ArrayList<AbstractCard> eligible = AbstractDungeon.player.exhaustPile.group.stream().filter(c -> c.hasTag(HexaMod.AFTERLIFE)).collect(Collectors.toCollection(ArrayList::new));  // Very proud of this line
                            if (!eligible.isEmpty()) {
                                isDone = false;
                                AbstractCard q = eligible.get(AbstractDungeon.cardRandomRng.random(eligible.size() - 1));
                                AbstractDungeon.player.exhaustPile.removeCard(q);
                                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(q.makeSameInstanceOf()));
                            }
                        }
                    }
                }
                tickDuration();
            }
        });
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + DESCRIPTIONS[2];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + DESCRIPTIONS[3];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new GiftsFromTheDeadPower(amount);
    }
}