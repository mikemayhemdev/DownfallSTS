package slimebound.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class AbsorbEndCombat extends CustomRelic {
    public static final String ID = "Slimebound:AbsorbEndCombat";
    public static final String IMG_PATH = "relics/heartofgoo.png";
    public static final String OUTLINE_IMG_PATH = "relics/heartofgooOutline.png";
    private static final int HP_PER_SLURP = 2;
    private static final int HP_PER_COMBAT = 8;

    //todo: change description to use the two above variables

    public AbsorbEndCombat() {
        super(ID, new Texture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(slimebound.SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        grayscale = false;
        counter = HP_PER_COMBAT;
    }

    @Override
    public void onTrigger() {
        int realAmount = Math.min(HP_PER_SLURP, counter);
        if (realAmount > 0) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new com.megacrit.cardcrawl.actions.common.HealAction(AbstractDungeon.player, AbstractDungeon.player, realAmount));
            counter -= realAmount;
            if (counter == 0) {
                grayscale = true;
            }
        }
    }

    @Override
    public void onUnequip() {
        for (AbstractRelic relicInBossPool : RelicLibrary.bossList) {
            if (relicInBossPool instanceof AbsorbEndCombatUpgraded) {
                RelicLibrary.bossList.remove(relicInBossPool);
                break;
            }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new AbsorbEndCombat();
    }
}