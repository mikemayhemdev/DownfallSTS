package gremlin.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gremlin.actions.LoseRearmostGremlinAction;
import gremlin.characters.GremlinCharacter;
import gremlin.orbs.GremlinStandby;

import java.util.Collections;

public class LeaderVoucher extends AbstractGremlinRelic {
    public static final String ID = getID("LeaderVoucher");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.BOSS;
    private static final String IMG = "relics/leader_voucher.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.FLAT;

    public static final int OOMPH = 6;

    public LeaderVoucher() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0];
    }

    @Override
    public boolean canSpawn() {
        if (AbstractDungeon.player instanceof GremlinCharacter) {
            return !((GremlinCharacter)AbstractDungeon.player).mobState.getRearLivingGremlin().equals("");
        }
        return false;
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
        if (AbstractDungeon.player instanceof GremlinCharacter) {
            GremlinCharacter g = (GremlinCharacter)AbstractDungeon.player;
            String enslaved = g.mobState.getRearLivingGremlin();
            if (!enslaved.equals("")) {
                ((GremlinCharacter) AbstractDungeon.player).gremlinDeathSFX(enslaved);
                ((GremlinCharacter) AbstractDungeon.player).enslave(enslaved);
            }
        }
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }
}
