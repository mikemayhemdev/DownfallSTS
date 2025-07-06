package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.RandomFlameAction;
import theHexaghost.actions.RandomFlameActionRelicRng;
import theHexaghost.actions.RandomizeFlameAction;

import theHexaghost.ghostflames.*;
import downfall.util.TextureLoader;

import static hermit.util.Wiz.atb;
import static hermit.util.Wiz.att;
import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class SoulOfChaos extends CustomRelic {

    public static final String ID = HexaMod.makeID("SoulOfChaos");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SoulOfChaos.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SoulOfChaos.png"));

    public SoulOfChaos() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }


    @Override
    public void atTurnStart() {
        super.atTurnStart();
        flash();

        att(new RandomFlameActionRelicRng());

        atb(new RandomizeFlameAction());

        att(new RelicAboveCreatureAction(AbstractDungeon.player, this));

    }


    @Override
    public void onPlayerEndTurn() {
        flash();
        this.addToTop(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster) null, true, AbstractDungeon.relicRng), false));
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
