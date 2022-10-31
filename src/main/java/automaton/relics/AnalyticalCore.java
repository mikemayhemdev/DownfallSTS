package automaton.relics;

import automaton.AutomatonMod;
import automaton.cards.SpaghettiCode;
import automaton.powers.LibraryModPower;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.cardmods.EtherealMod;
import downfall.util.TextureLoader;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class AnalyticalCore extends CustomRelic {

    public static final String ID = AutomatonMod.makeID("AnalyticalCore");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BronzeCore.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BronzeCore.png"));

    public AnalyticalCore() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LibraryModPower(1), 1));
        AbstractCard qCardGet = SpaghettiCode.getRandomEncode();
        //qCardGet.modifyCostForCombat(-99);
        CardModifierManager.addModifier(qCardGet, new EtherealMod());
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(qCardGet, true));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
