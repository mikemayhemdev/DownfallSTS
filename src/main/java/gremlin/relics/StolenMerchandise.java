package gremlin.relics;

import charbosses.bosses.Merchant.CharBossMerchant;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.monsters.FleeingMerchant;
import gremlin.actions.MakeEchoAction;

public class StolenMerchandise extends AbstractGremlinRelic {
    private static final String ID = getID("StolenMerchandise");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.SHOP;
    private static final String IMG = "relics/stolen_merchandise.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.HEAVY;

    public static int RAGE = 2;

    public StolenMerchandise() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0] + RAGE + strings.DESCRIPTIONS[1];
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public void onGremlinSwap() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));

        AbstractCard colorless = AbstractDungeon.returnTrulyRandomColorlessCardInCombat();
        AbstractDungeon.actionManager.addToBottom(new MakeEchoAction(colorless));
    }

    public void atBattleStart() {
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                if (mo instanceof FleeingMerchant || mo instanceof CharBossMerchant) {
                    flash();
                    addToBot(new RelicAboveCreatureAction(mo, this));
                    AbstractDungeon.actionManager.addToBottom(
                            new TalkAction(mo, strings.DESCRIPTIONS[MathUtils.random(2,4)]));
                    AbstractDungeon.actionManager.addToBottom(
                            new ApplyPowerAction(mo, mo, new StrengthPower(mo, RAGE), RAGE));
                }
            }
        }
    }
}

