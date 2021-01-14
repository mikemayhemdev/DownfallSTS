package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import downfall.util.TextureLoader;
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

    private boolean isActive = false;

    @Override
    public void atBattleStart() {
        counter = 10;
    }

    @Override
    public int onLoseHpLast(int damageAmount) {
        System.out.println("Yes, this is active. Status: " + isActive);
        if (!isActive){
            return damageAmount;
        }
        else if (counter > 0){
            this.flash();
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

    @Override
    public void onPlayerEndTurn() {
        System.out.println("Zerker status set to FALSE");
        this.isActive = false;
    }

    public void atTurnStart() {
        System.out.println("Zerker status set to TRUE");
        this.isActive = true;
    }

    @Override
    public void onVictory() {
        counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
