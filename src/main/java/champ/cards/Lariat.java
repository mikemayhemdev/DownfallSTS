package champ.cards;

import champ.ChampMod;
import champ.actions.LariatAction;
import champ.stances.AbstractChampStance;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import downfall.actions.PerformXAction;
import slimebound.SlimeboundMod;

public class Lariat extends AbstractChampCard {

    public final static String ID = makeID("Lariat");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 1;

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 1;

    public Lariat() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        tags.add(ChampMod.TECHNIQUE);
        //baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount;
        }
        LariatAction r = new LariatAction(magicNumber, block);
        atb(new PerformXAction(r, p, energyOnUse, freeToPlayOnce));
        if (upgraded) {
            atb(new GainEnergyAction(1));
        }

    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void initializeDescription() {
        if (EXTENDED_DESCRIPTION != null) {
            String newDesc = "";
            if (upgraded) {
                newDesc = EXTENDED_DESCRIPTION[1];
            } else {
                newDesc = EXTENDED_DESCRIPTION[0];
            }
            initializeDescription(newDesc, "");
        } else {
            super.initializeDescription();
        }
    }
}