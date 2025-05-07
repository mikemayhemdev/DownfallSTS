package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theHexaghost.HexaMod;
import downfall.util.TextureLoader;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class BolsterEngine extends CustomRelic {

    public static final String ID = HexaMod.makeID("BolsterEngine");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BolsterEngine.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BolsterEngine.png"));

    public BolsterEngine() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }
    //Tricky's Bolster-Rod 高热之杖

    //todo: set variables for block and strength gain

    @Override
    public void atBattleStart() {
        grayscale = false;
        beginLongPulse();
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard.type == AbstractCard.CardType.POWER && !grayscale) {
            flash();
            stopPulse();
            grayscale = true;
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainBlockAction(AbstractDungeon.player, 6));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
        }
    }

    @Override
    public void onVictory() {
        grayscale = false;
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
