package gremlin.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gremlin.characters.GremlinCharacter;

public class LeaderVoucher extends AbstractGremlinRelic {
    public static final String ID = getID("LeaderVoucher");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.BOSS;
    private static final String IMG = "relics/leader_voucher.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.FLAT;

    private int gremIndex = -1;

    public LeaderVoucher() {
        super(ID, IMG, TIER, SOUND);
        updateEnslavedTooltip();
    }

    public void updateEnslavedTooltip() {
        if (AbstractDungeon.player instanceof GremlinCharacter) {
            GremlinCharacter g = (GremlinCharacter) AbstractDungeon.player;
            String enslaved = g.mobState.getVoucher();
            if(enslaved.equals("")) {
                enslaved = g.mobState.getRearLivingGremlin();
            }
            switch (enslaved){
                case "angry":
                    gremIndex = 3;
                    break;
                case "fat":
                    gremIndex = 1;
                    break;
                case "shield":
                    gremIndex = 4;
                    break;
                case "sneak":
                    gremIndex = 5;
                    break;
                case "wizard":
                    gremIndex = 2;
                    break;
                default:
                    gremIndex = -1;
            }
            this.tips.clear();
            this.tips.add(new PowerTip(this.name, getUpdatedDescription()));
            this.initializeTips();
        }
    }

    @Override
    public String getUpdatedDescription() {
        if (gremIndex < 0) {
            return strings.DESCRIPTIONS[0];
        }
        return strings.DESCRIPTIONS[0] + strings.DESCRIPTIONS[gremIndex];
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
                ((GremlinCharacter) AbstractDungeon.player).enslave(enslaved, true);
                updateEnslavedTooltip();
            }
        }

    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }
}
