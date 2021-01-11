package downfall.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import downfall.downfallMod;

public class NeowBlessing extends CustomRelic {
    public static final String ID = downfallMod.makeID("NeowBlessing_Player");

    private static Texture texture = new Texture(downfallMod.assetPath("images/relics/blessing.png"));
    private static Texture outline = new Texture(downfallMod.assetPath("images/relics/Outline/blessing.png"));
    private static final int amount = 50;

    public NeowBlessing() {
        super(ID, texture, outline, RelicTier.SPECIAL, CustomRelic.LandingSound.FLAT);
        this.counter = amount;
        this.description = getUpdatedDescription();

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

    public void atBattleStart() {
        boolean isBoss = false;

        for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
            if (m.type == AbstractMonster.EnemyType.BOSS) {
                isBoss = true;
            }
        }

        if (isBoss && this.counter > 0) {

            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                flash();
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

                m.decreaseMaxHealth((int) (m.currentHealth * this.counter * 0.01f));
            }

            this.counter -= 10;

            if(this.counter <= 0) this.counter = -1;
        }
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + amount + this.DESCRIPTIONS[1];
    }


    public CustomRelic makeCopy() {
        return new NeowBlessing();
    }
}