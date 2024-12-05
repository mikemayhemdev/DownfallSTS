package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EvolvePower;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;
import sneckomod.actions.MuddleRandomCardAction;
import sneckomod.actions.SuperSneckoSoulAction;
import sneckomod.cards.SoulRoll;
import sneckomod.powers.CheapStockPower;


import java.util.ArrayList;

import static hermit.util.Wiz.applyToSelf;
import static hermit.util.Wiz.atb;

public class SneckoTalon extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SneckoTalon");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SneckoTalon.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SneckoTalon.png"));

    public SneckoTalon() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStartPostDraw() {
        AbstractDungeon.actionManager.addToBottom(new SuperSneckoSoulAction());
        AbstractDungeon.actionManager.addToBottom(new SuperSneckoSoulAction());
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
