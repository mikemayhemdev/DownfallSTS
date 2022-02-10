package hermit.relics;

import basemod.abstracts.CustomRelic;
import basemod.devcommands.power.Power;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.HermitMod;
import hermit.cards.MementoCard;
import hermit.powers.Concentration;
import hermit.powers.PetGhostPower;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class PetGhost extends CustomRelic implements OnPlayerDeathRelic {

    // ID, images, text.
    public static final String ID = HermitMod.makeID("PetGhost");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("pet_ghost.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("pet_ghost.png"));

    public PetGhost() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.FLAT);
    }

    public boolean canDie = false;


    // Description
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
        if (!canDie) {

            if (p.hasPower(PetGhost.ID))
                p.getPower(PetGhost.ID).flash();
            else {
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                PetGhostPower pow = new PetGhostPower(AbstractDungeon.player,-1);
                pow.parent = this;
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player, pow));
                flash();

            }
            p.heal(1,false);
            return false;
        }
        return true;
    }
}
