package automaton.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Allocate extends AbstractBronzeCard {

    public final static String ID = makeID("Allocate");

    //stupid intellij stuff skill, self, uncommon

    public Allocate() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        //thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        int statusCount = 0;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.type == CardType.STATUS) {
                statusCount++;
            }
        }

        atb(new GainEnergyAction(statusCount));
    }


    public void applyPowers() {
        super.applyPowers();

        if (AbstractDungeon.player != null) {
            this.rawDescription = cardStrings.DESCRIPTION;

            int statusCount = 0;
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c.type == CardType.STATUS) {
                    statusCount++;
                }
            }

            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + statusCount;
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[1];

            this.initializeDescription();
        }
    }


    public void upp() {
        upgradeBaseCost(0);
    }
}