package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hermit.HermitMod;
import hermit.util.TextureLoader;

import java.util.Iterator;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class RyeStalk extends CustomRelic {
    public static final String ID = HermitMod.makeID("RyeStalk");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("rye_stalk.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("rye_stalk.png"));

    public RyeStalk() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        flash();

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new RelicAboveCreatureAction(mo, this));
            this.addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new StrengthPower(mo, -2), -2, true));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
