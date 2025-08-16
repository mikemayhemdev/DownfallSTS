package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import sneckomod.SneckoMod;
import downfall.util.TextureLoader;
import sneckomod.powers.CheatPower;

import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.atb;
import static sneckomod.SneckoMod.NO_TYPHOON;

public class ConfusingCodex extends CustomRelic {

    public static final String ID = SneckoMod.makeID("ConfusingCodex");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("ConfusingCodex.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("ConfusingCodex.png"));

    public ConfusingCodex() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    //Variables
    public static final int AMOUNT = 3;

    @Override
    public void onEquip() {
        this.counter = 0;
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
        if (!isOverflowActive(card)) {
            return;
        }
        ++this.counter;
        if (this.counter % AMOUNT == 0) {
            this.counter = 0;
            AbstractMonster q = AbstractDungeon.getRandomMonster();
            if (q != null) {
                atb(new RelicAboveCreatureAction(q, this));
                applyToEnemy(q, new WeakPower(q, 1, false));
                applyToEnemy(q, new VulnerablePower(q, 1, false));
                flash(); // relic tracking
            }
        }
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }
}
