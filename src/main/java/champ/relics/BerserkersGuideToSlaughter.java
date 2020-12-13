package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class BerserkersGuideToSlaughter extends CustomRelic {

    public static final String ID = ChampMod.makeID("BerserkersGuideToSlaughter");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BerserkersGuide.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BerserkersGuide.png"));

    public BerserkersGuideToSlaughter() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    private boolean isActive;

    @Override
    public void atBattleStart() {
        counter = 10;
    }

    //TODO: Doesn't work... seems to be the same setup as Tungesten Rod

    @Override
    public int onLoseHpLast(int damageAmount) {
        if (!isActive){
            return damageAmount;
        }
        if (counter > 0){
            if (counter > damageAmount){
                counter = counter - damageAmount;
                return 0;
            } else {
                int temp = counter;
                counter = 0;
                return damageAmount - temp;
            }
        }
        return damageAmount;
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.isActive = false;
    }

    public void atStartOfTurn() {
        this.isActive = true;
    }

    @Override
    public void onVictory() {
        counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
