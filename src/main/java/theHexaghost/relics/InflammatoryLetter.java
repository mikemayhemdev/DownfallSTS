package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHexaghost.HexaMod;
import theHexaghost.powers.EnhancePower;
import downfall.util.TextureLoader;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class InflammatoryLetter extends CustomRelic {

    public static final String ID = HexaMod.makeID("InflammatoryLetter");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("InflammatoryLetter.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("InflammatoryLetter.png"));

    public InflammatoryLetter() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnhancePower(1), 1));
        //grayscale = true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
