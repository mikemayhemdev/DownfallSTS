package charbosses.cards.hermit;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Hermit.NewAge.ArchetypeAct2WheelOfFateNewAge;
import charbosses.bosses.Hermit.Simpler.ArchetypeAct2WheelOfFateSimple;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.Desperado;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;

public class EnDesperado extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Desperado";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Desperado.ID);

    public EnDesperado() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/desperado.png", 1, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 10;
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new DamageAction(p, new DamageInfo(m, damage, damageTypeForTurn), EnumPatch.HERMIT_GUN2));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (AbstractCharBoss.boss.chosenArchetype instanceof ArchetypeAct2WheelOfFateNewAge) {
                    AbstractBossCard c = ((ArchetypeAct2WheelOfFateNewAge) AbstractCharBoss.boss.chosenArchetype).getCardFromDeck(uuid);
                    if (c != null) c.baseDamage *= 2;
                } else if (AbstractCharBoss.boss.chosenArchetype instanceof ArchetypeAct2WheelOfFateSimple) {
                    AbstractBossCard c = ((ArchetypeAct2WheelOfFateSimple) AbstractCharBoss.boss.chosenArchetype).getCardFromDeck(uuid);
                    if (c != null) c.baseDamage *= 2;
                }
                isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnDesperado();
    }
}
