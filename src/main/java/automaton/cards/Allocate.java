package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import guardian.orbs.StasisOrb;

import static automaton.AutomatonMod.makeBetaCardPath;

public class Allocate extends AbstractBronzeCard {

    public final static String ID = makeID("Allocate");
    
    public Allocate() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("Allocate.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int statusCount = 0;

        // standard checks
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.type == CardType.STATUS) {
                statusCount++;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == CardType.STATUS) {
                statusCount++;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.type == CardType.STATUS) {
                statusCount++;
            }
        }

        // stasis check for the sake of snecko
        for (AbstractOrb orb : p.orbs) {
            if (orb instanceof StasisOrb) {
                AbstractCard stasisCard = ((StasisOrb) orb).stasisCard;
                if (stasisCard != null && stasisCard.type == CardType.STATUS) {
                    statusCount++;
                }
            }
        }

        atb(new GainEnergyAction(statusCount));
    }

    public void applyPowers() {
        super.applyPowers();

        if (AbstractDungeon.player != null) {
            this.rawDescription = cardStrings.DESCRIPTION;

            int statusCount = 0;

            // standard checks
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c.type == CardType.STATUS) {
                    statusCount++;
                }
            }
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.type == CardType.STATUS) {
                    statusCount++;
                }
            }
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c.type == CardType.STATUS) {
                    statusCount++;
                }
            }

            //stasis check
            for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                if (orb instanceof StasisOrb) {
                    AbstractCard stasisCard = ((StasisOrb) orb).stasisCard;
                    if (stasisCard != null && stasisCard.type == CardType.STATUS) {
                        statusCount++;
                    }
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
