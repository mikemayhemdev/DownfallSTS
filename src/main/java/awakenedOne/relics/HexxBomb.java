package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.Ceremony;
import awakenedOne.powers.UltimateHexDebuff;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import collector.actions.DrawCardFromCollectionAction;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.vfx.combat.GiantEyeEffect;

import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.*;
import static collector.util.Wiz.atb;

public class HexxBomb extends CustomRelic{

    //Hexx Bomb

    private static final int AMOUNT = 1;

    public static final String ID = AwakenedOneMod.makeID("HexxBomb");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("HexxBomb.png"));
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("HexxBomb.png"));

    public HexxBomb() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }


    @Override
    public void atBattleStart() {
        onTrigger();
    }

    @Override
    public void onTrigger() {
        super.onTrigger();
        this.flash();
        AbstractMonster mo = AbstractDungeon.getRandomMonster();
        if (!mo.hasPower(UltimateHexDebuff.POWER_ID)) {
            HexCurse(AMOUNT, mo, AbstractDungeon.player);
        }
        this.addToBot(new VFXAction(new GiantEyeEffect(mo.hb.cX, mo.hb.cY + 300.0F * Settings.scale, new Color(1.0F, 0.3F, 1.0F, 0.0F))));
        this.addToTop(new RelicAboveCreatureAction(mo, this));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

}
