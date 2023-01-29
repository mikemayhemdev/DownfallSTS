package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.HermitMod;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class PetGhost extends CustomRelic implements OnPlayerDeathRelic {
    public static final String ID = HermitMod.makeID("PetGhost");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("pet_ghost.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("pet_ghost.png"));

    public PetGhost() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.SOLID);
    }

    public boolean canDie = false;

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        canDie = false;
    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer p, DamageInfo damageInfo) {
        if (!this.canDie && p.currentHealth <= 0) {
            this.canDie = true;
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            flash();
            p.currentHealth = 1;
            return false;
        }
        return true;
    }
}
