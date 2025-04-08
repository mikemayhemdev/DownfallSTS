package theHexaghost.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import theHexaghost.HexaMod;
import downfall.util.TextureLoader;
import theHexaghost.cards.EtherealExpedition;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GiftsFromTheDeadPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = HexaMod.makeID("GiftsFromBeyondPower");

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
        this.isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

//    @Override
//    public void atStartOfTurnPostDraw() {
//        this.addToBot(new LoseEnergyAction(amount));
//        flash();
//        addToBot(new AbstractGameAction() {
//            { startDuration = duration = 1.5f; }
//            @Override
//            public void update() {
//                if (duration == startDuration) {
//                    isDone = true;
//                    for (int i = 0; i < GiftsFromTheDeadPower.this.amount; i++) {
//                        if (!AbstractDungeon.player.exhaustPile.isEmpty()) {
//                            ArrayList<AbstractCard> eligible = AbstractDungeon.player.exhaustPile.group.stream().filter(c -> c.hasTag(HexaMod.AFTERLIFE)).collect(Collectors.toCollection(ArrayList::new));  // Very proud of this line
//                            if (!eligible.isEmpty()) {
//                                isDone = false;
//                                AbstractCard q = eligible.get(AbstractDungeon.cardRandomRng.random(eligible.size() - 1));
//                                AbstractDungeon.player.exhaustPile.removeCard(q);
//                                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(q.makeSameInstanceOf()));
//                            }
//                        }
//                    }
//                }
//                tickDuration();
//            }
//        });
//    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        for (int i = 0; i < GiftsFromTheDeadPower.this.amount; i++) {
            AbstractCard q = EtherealExpedition.returnTrulyRandomEtherealCardInCombat().makeCopy();
            addToBot(new MakeTempCardInHandAction(q));
        }
//        this.addToBot(new DrawCardAction(2 * amount));
//        this.addToBot(new ExhaustAction(amount, false, false, false));
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new GiftsFromTheDeadPower(amount);
    }
}
