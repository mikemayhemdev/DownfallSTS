package downfall.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;

public class NeowBlessing extends CustomRelic {
    public static final String ID = downfallMod.makeID("NeowBlessing_Player");

    private static Texture texture = new Texture(downfallMod.assetPath("images/relics/blessing.png"));
    private static Texture outline = new Texture(downfallMod.assetPath("images/relics/Outline/blessing.png"));

    public NeowBlessing() {
        super(ID, texture, outline, RelicTier.SPECIAL, CustomRelic.LandingSound.FLAT);

    }


    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster--;
        AbstractDungeon.player.masterHandSize--;
        AbstractDungeon.player.potionSlots = 0;
        AbstractDungeon.player.potions.clear();
        AbstractDungeon.player.increaseMaxHp(100, true);
    }


    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster++;
        AbstractDungeon.player.masterHandSize++;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public CustomRelic makeCopy() {
        return new NeowBlessing();
    }
}