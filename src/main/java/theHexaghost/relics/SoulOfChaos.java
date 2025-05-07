package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.cards.RandomFlameAction;
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

        att(new RandomFlameAction());
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));

        int chosen = AbstractDungeon.relicRng.random(0, 2);

        AbstractGhostflame gf = GhostflameHelper.hexaGhostFlames.get(0);

        if (chosen == 0) {
            gf = new SearingGhostflame(GhostflameHelper.activeGhostFlame.lx, GhostflameHelper.activeGhostFlame.ly);
        }

        if (chosen == 1) {
            gf = new CrushingGhostflame(GhostflameHelper.activeGhostFlame.lx, GhostflameHelper.activeGhostFlame.ly);
        }

        if (chosen == 2) {
            gf = new BolsteringGhostflame(GhostflameHelper.activeGhostFlame.lx, GhostflameHelper.activeGhostFlame.ly);
        }


        GhostflameHelper.hexaGhostFlames.set((GhostflameHelper.hexaGhostFlames.indexOf(GhostflameHelper.activeGhostFlame)), gf);
        gf.activate();
    }


    @Override
    public void onPlayerEndTurn() {
        flash();
        this.addToTop(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng), false));
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
