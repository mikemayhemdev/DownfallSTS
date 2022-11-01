package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.cards.curses.Writhe;
import com.megacrit.cardcrawl.characters.Watcher;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.WaveOfTheHandPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import hermit.HermitMod;
import hermit.actions.RedScarfAction;
import hermit.powers.Concentration;
import hermit.util.TextureLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class RedScarf extends CustomRelic implements OnApplyPowerRelic {

    // ID, images, text.
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

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
