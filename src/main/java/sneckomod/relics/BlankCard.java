package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import gremlin.actions.MakeEchoAction;
import sneckomod.SneckoMod;
import downfall.util.TextureLoader;

import java.util.ArrayList;

public class BlankCard extends CustomRelic {

    public static final String ID = SneckoMod.makeID("BlankCard");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("BlankCard.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("BlankCard.png"));

    public BlankCard() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        ArrayList<AbstractCard> possCardsList = new ArrayList<>(AbstractDungeon.player.drawPile.group);
        AbstractCard card2 = possCardsList.get(AbstractDungeon.cardRandomRng.random(possCardsList.size() - 1)).makeStatEquivalentCopy();

        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        card2.freeToPlayOnce = true;
        addToBot(new MakeEchoAction(card2));
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
