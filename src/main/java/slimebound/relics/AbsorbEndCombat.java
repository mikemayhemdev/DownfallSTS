package slimebound.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import slimebound.actions.SlimeSpawnAction;
import slimebound.orbs.AttackSlime;
import slimebound.orbs.PoisonSlime;
import slimebound.orbs.ShieldSlime;
import slimebound.orbs.SlimingSlime;

import java.util.ArrayList;

public class AbsorbEndCombat extends CustomRelic {
    public static final String ID = "Slimebound:AbsorbEndCombat";
    public static final String IMG_PATH = "relics/heartofgoo.png";
    public static final String OUTLINE_IMG_PATH = "relics/heartofgooOutline.png";
    private static final int HP_PER_CARD = 1;

    public AbsorbEndCombat() {
        super(ID, new Texture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(slimebound.SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    /*
    public void atBattleStartPreDraw() {
        //Cheaty hack for testing max orbslots
        //com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction(6));

        this.flash();
        ArrayList<Integer> orbs = new ArrayList();
        orbs.add(1);
        orbs.add(2);
        orbs.add(3);
        orbs.add(4);
        Integer o = orbs.get(AbstractDungeon.cardRng.random(orbs.size() - 1));

        switch (o) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new AttackSlime(), false, true));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new ShieldSlime(), false, true));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new SlimingSlime(), false, true));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new PoisonSlime(), false, true));
                break;
        }


    }
    */

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