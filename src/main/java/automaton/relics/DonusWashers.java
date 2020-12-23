package automaton.relics;

import automaton.AutomatonMod;
import automaton.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class DonusWashers extends CustomRelic {

    public static final String ID = AutomatonMod.makeID("DonusWashers");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DonusWashers.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DonusWashers.png"));

    public DonusWashers() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    public boolean firstTurn = false;

    @Override
    public void atPreBattle() {
        firstTurn = true;
    }

    @Override
    public void atTurnStart() {
        if (firstTurn) {
            flash();
            addToTop(new MakeTempCardInDrawPileAction(new VoidCard(), 1, true, true));
            addToTop(new GainEnergyAction(1));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.firstTurn = false;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
