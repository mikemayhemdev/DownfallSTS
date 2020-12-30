package downfall.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.actions.WaitForEscapeAction;
import downfall.downfallMod;
import downfall.patches.EvilModeCharacterSelect;

public class Hecktoplasm extends CustomRelic {

    public static final String ID = downfallMod.makeID("Hecktoplasm");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/ectoplasmEvil.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/ectoplasmEvil.png"));

    public Hecktoplasm() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    public boolean canSpawn() {
        return (AbstractDungeon.actNum <= 1 && EvilModeCharacterSelect.evilMode);
    }

    public AbstractRelic makeCopy() {
        return new Hecktoplasm();
    }
}
