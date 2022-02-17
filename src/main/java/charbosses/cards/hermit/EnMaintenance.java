package charbosses.cards.hermit;

import charbosses.bosses.Hermit.NewAge.ArchetypeAct2WheelOfFateNewAge;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.cards.Maintenance;
import hermit.cards.Virtue;
import hermit.characters.hermit;

public class EnMaintenance extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Maintenance";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Maintenance.ID);

    public EnMaintenance() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/maintenance.png", 2, cardStrings.DESCRIPTION, CardType.SKILL, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (owner.chosenArchetype instanceof ArchetypeAct2WheelOfFateNewAge) {
                    ((ArchetypeAct2WheelOfFateNewAge) owner.chosenArchetype).maintenanceLevels += 1;
                }
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnMaintenance();
    }
}
