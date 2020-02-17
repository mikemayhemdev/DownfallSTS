package evilWithin.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import evilWithin.EvilWithinMod;
import guardian.cards.CrystalShiv;

import java.util.ArrayList;

public class ShatteredFragment extends CustomRelic {

    public static final String ID = EvilWithinMod.makeID("ShatteredFragment");
    private static final Texture IMG = new Texture(EvilWithinMod.assetPath("images/relics/WingShiv.png"));
    private static final Texture OUTLINE = new Texture(EvilWithinMod.assetPath("images/relics/WingShiv.png"));

    public ShatteredFragment() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
            return DESCRIPTIONS[0];
    }

    public void atBattleStartPreDraw() {
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new MakeTempCardInHandAction(new CrystalShiv(), 1, false));
    }

}
