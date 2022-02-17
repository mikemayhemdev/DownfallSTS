package charbosses.cards.hermit;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.bossmechanicpowers.HermitConcentrationPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.cards.Headshot;
import hermit.cards.ItchyTrigger;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;

public class EnItchyTrigger extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:ItchyTrigger";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ItchyTrigger.ID);

    public EnItchyTrigger() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/itchy_trigger.png", 2, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 12;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), EnumPatch.HERMIT_GUN2));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnItchyTrigger();
    }
}
