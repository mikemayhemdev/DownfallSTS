package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import hermit.HermitMod;
import hermit.actions.RedScarfAction;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class RedScarf extends CustomRelic implements OnApplyPowerRelic {
    public static final String ID = HermitMod.makeID("RedScarf");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("red_scarf.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("red_scarf.png"));

    public RedScarf() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public boolean onApplyPower(AbstractPower var1, AbstractCreature var2, AbstractCreature var3)
    {
        if (var1.type == AbstractPower.PowerType.DEBUFF && var1.amount != 0 && var3 == AbstractDungeon.player && var2 != AbstractDungeon.player && !var2.hasPower("Artifact"))
        {
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            flash();

            this.addToBot(new RedScarfAction(2));
        }

        return true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
