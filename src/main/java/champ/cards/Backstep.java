package champ.cards;

import champ.ChampMod;
import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Backstep extends AbstractChampCard {

    public final static String ID = makeID("Backstep");

    //stupid intellij stuff skill, self, uncommon

    public Backstep() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        tags.add(ChampMod.OPENER);

        baseBlock = 6;
        this.tags.add(ChampMod.OPENERDEFENSIVE);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBOBERSERKER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        defenseOpen();
        blck();
        if (bcombo()) {
            if (AbstractDungeon.player.hasPower(ResolvePower.POWER_ID)) {
                int x = AbstractDungeon.player.getPower(ResolvePower.POWER_ID).amount;
                atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, x));
            }
        }

    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = bcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }


    public void upp() {
        upgradeBaseCost(0);
    }
}