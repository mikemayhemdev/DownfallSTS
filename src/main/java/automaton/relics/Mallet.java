package automaton.relics;

import automaton.AutomatonMod;
import automaton.cards.FunctionCard;
import automaton.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class Mallet extends CustomRelic {

    public static final String ID = AutomatonMod.makeID("Mallet");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Mallet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Mallet.png"));

    public Mallet() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        counter = 0;
    }

    @Override
    public void onVictory() {
        counter = -1;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof FunctionCard) {
            ++counter;
            if (counter == 3) {
                counter = 0;
                flash();
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
