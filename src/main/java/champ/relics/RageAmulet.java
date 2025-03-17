package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.cards.CrookedStrike;
import champ.powers.CounterPower;
import collector.CollectorCollection;
import collector.actions.DrawCardFromCollectionAction;
import collector.relics.HolidayCoal;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import downfall.powers.NextTurnPowerPower;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.util.Wiz;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.att;

public class RageAmulet extends CustomRelic {

    public static final String ID = ChampMod.makeID("RageAmulet");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CrystallizedMud.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CrystallizedMud.png"));
    private boolean triggeredThisTurn = false;
    int remainingVigor = 0;
    private static final int VIGREQ = 8;
    public RageAmulet() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void onVictory() {
        remainingVigor = 0;
        this.counter = -1;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!(card instanceof CrookedStrike)) {
            if (card.type == AbstractCard.CardType.ATTACK && AbstractDungeon.player.hasPower(VigorPower.POWER_ID))
                addNextTurnPower(AbstractDungeon.player.getPower(VigorPower.POWER_ID));
        }
    }

    public void addNextTurnPower(AbstractPower power) {
        int found = power.amount;
        int totaled = found / VIGREQ;
        int finalized = totaled * 1;
        boolean isVigor = (VigorPower.POWER_ID.equals(power.ID));

        // Add remainder.
        if (isVigor) {
            remainingVigor += found % VIGREQ;
            this.counter = remainingVigor;
        }
        // If remainder is beyond threshold, add to total and subtract.
        if (remainingVigor >= VIGREQ) {
            remainingVigor -= VIGREQ;
            finalized += 1;
            this.counter = remainingVigor;
        }

        // Only do anything if the amount is greater than 0.
        if (finalized > 0) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            if (isVigor)
                Wiz.atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, finalized), finalized));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + VIGREQ + DESCRIPTIONS[1];
    }
}
