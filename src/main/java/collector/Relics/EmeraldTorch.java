package collector.Relics;

import automaton.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import collector.CollectorChar;
import collector.CollectorMod;
import collector.powers.ProtectionPower;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static collector.CollectorMod.makeRelicOutlinePath;
import static collector.CollectorMod.makeRelicPath;

public class EmeraldTorch extends CustomRelic {
    public static final String ID = CollectorMod.makeID("EmeraldTorch");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("EmeraldTorch.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("EmeraldTorch.png"));

    public EmeraldTorch() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }
    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ProtectionPower(CollectorChar.TorchHead, 1), 1));
        this.addToBot(new GainBlockAction(CollectorChar.TorchHead,5));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
}

