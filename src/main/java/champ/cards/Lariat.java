package champ.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static champ.ChampMod.loadJokeCardImage;

public class Lariat extends AbstractChampCard {
    public final static String ID = makeID("Lariat");

    public Lariat() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 7;
        postInit();
        loadJokeCardImage(this, "Lariat.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount;
        }

        addToBot(new com.megacrit.cardcrawl.actions.defect.ReinforcedBodyAction(p, this.block, this.freeToPlayOnce, this.energyOnUse));
    }

    public void upp() {
        upgradeBlock(2);
    }
}