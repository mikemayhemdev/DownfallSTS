package evilWithin.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import evilWithin.EvilWithinMod;

public class HeartBlessingGreen extends CustomRelic {

    public static final String ID = EvilWithinMod.makeID("HeartBlessingGreen");
    private static final Texture IMG = new Texture(EvilWithinMod.assetPath("images/relics/HeartBlessingGreen.png"));
    private static final Texture OUTLINE = new Texture(EvilWithinMod.assetPath("images/relics/HeartBlessingGreen.png"));

    public HeartBlessingGreen() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }


}
