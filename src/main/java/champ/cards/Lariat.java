package champ.cards;

import champ.ChampMod;
import champ.actions.LariatAction;
import champ.vfx.StanceDanceEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import downfall.actions.PerformXAction;

public class Lariat extends AbstractChampCard {

    public final static String ID = makeID("Lariat");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 1;

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 1;

    public Lariat() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        tags.add(ChampMod.TECHNIQUE);
        techniqueLast = true;
        //baseMagicNumber = magicNumber = MAGIC;
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
     //   techique();
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount;
        }
        LariatAction r = new LariatAction(magicNumber, block);
        atb(new PerformXAction(r, p, energyOnUse, freeToPlayOnce));
        if (upgraded) {
            atb(new GainEnergyAction(1));
        }

        atb(new VFXAction(new StanceDanceEffect(AbstractDungeon.player, false, false, false, true), 0.7F));

    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}