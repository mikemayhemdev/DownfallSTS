package charbosses.cards.hermit;

import charbosses.powers.cardpowers.EnemyShadowCloakPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.cards.MementoCard;
import hermit.cards.ShadowCloak;
import hermit.characters.hermit;

import java.util.Iterator;

public class EnMemento extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Memento";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(MementoCard.ID);

    public EnMemento() {

        super(ID, cardStrings.NAME, "hermitResources/images/cards/memento.png", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.CURSE, CardRarity.COMMON, CardTarget.ALL, AbstractMonster.Intent.DEBUFF);
        baseMagicNumber = magicNumber = 1;
        vulnGeneratedIfPlayed = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Iterator var4 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var4.hasNext()) {
            AbstractMonster mo = (AbstractMonster)var4.next();
            if (m.hasPower(VulnerablePower.POWER_ID)){
                this.addToBot(new ApplyPowerAction(mo, m, new VulnerablePower(mo, magicNumber, false), magicNumber+1, true, AbstractGameAction.AttackEffect.NONE));
            } else {
                this.addToBot(new ApplyPowerAction(mo, m, new VulnerablePower(mo, magicNumber+1, false), magicNumber+1, true, AbstractGameAction.AttackEffect.NONE));
            }

            if (this.upgraded)
                this.addToBot(new ApplyPowerAction(mo, m, new WeakPower(mo, magicNumber+1, false), magicNumber+1, true, AbstractGameAction.AttackEffect.NONE));
        }

        this.addToBot(new ApplyPowerAction(p, m, new VulnerablePower(p, magicNumber, false), magicNumber));

        if (this.upgraded)
            this.addToBot(new ApplyPowerAction(p, m, new WeakPower(p, magicNumber, false), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnMemento();
    }
}
