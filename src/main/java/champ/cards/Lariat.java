package champ.cards;

import automaton.actions.AddToFuncAction;
import automaton.actions.EasyXCostAction;
import automaton.cards.FormatEncoded;
import collector.powers.NextTurnReservePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import downfall.downfallMod;

import static champ.ChampMod.loadJokeCardImage;
import static collector.util.Wiz.applyToSelf;
import static collector.util.Wiz.atb;

public class Lariat extends AbstractChampCard {
    public final static String ID = makeID("Lariat");

    public Lariat() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 5;
        postInit();
        loadJokeCardImage(this, "Lariat.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount;
        }

        addToBot(new com.megacrit.cardcrawl.actions.defect.ReinforcedBodyAction(p, this.block, this.freeToPlayOnce, this.energyOnUse));
        atb(new EasyXCostAction(this, (effect, params) -> {
            for (int i = 0; i < effect + params[0]; i++) {
                techique();
            }
            return true;
        }, 0));

    }

    public void upp() {
        upgradeBlock(2);
    }
}