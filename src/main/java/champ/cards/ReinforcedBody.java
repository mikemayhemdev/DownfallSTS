package champ.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ReinforcedBody extends AbstractChampCard {

    public final static String ID = makeID("ReinforcedBody");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 1;

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 1;

    public ReinforcedBody() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        //tags.add(ChampMod.TECHNIQUE);
        //techniqueLast = true;
        //baseMagicNumber = magicNumber = MAGIC;
       
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //   techique();
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount;
        }

        addToBot(new com.megacrit.cardcrawl.actions.defect.ReinforcedBodyAction(p, this.block, this.freeToPlayOnce, this.energyOnUse));

        //  atb(new VFXAction(new StanceDanceEffect(AbstractDungeon.player, false, false, false, true), 0.7F));

    }

    public void upp() {
        upgradeBlock(2);
    }
}