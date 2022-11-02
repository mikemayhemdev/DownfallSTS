package charbosses.cards.hermit;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import hermit.cards.Shortfuse;

public class EnShortFuse extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:ShortFuse";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Shortfuse.ID);

    public EnShortFuse() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/short_fuse.png", 3, cardStrings.DESCRIPTION, CardType.ATTACK, downfallMod.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 18;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new DamageAction(p, new DamageInfo(m, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
    }

    public void updateCostToSpecific(int specific) {
        setCostForTurn(specific);
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
        return new EnShortFuse();
    }
}
