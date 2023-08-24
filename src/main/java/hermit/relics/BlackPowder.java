package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.HermitMod;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class BlackPowder extends CustomRelic {
    public static final String ID = HermitMod.makeID("BlackPowder");

    public int PowderCharge = 0;

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("black_powder.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("black_powder_outline.png"));

    public BlackPowder() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    public void atTurnStart() {
        this.PowderCharge = 0;
        this.counter = 0;
    }

    public void onPlayerEndTurn(){
        if (this.PowderCharge > 0) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new DamageAllEnemiesAction((AbstractCreature) null, DamageInfo.createDamageMatrix(this.PowderCharge, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            this.PowderCharge = 0;
        }
    }

    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
