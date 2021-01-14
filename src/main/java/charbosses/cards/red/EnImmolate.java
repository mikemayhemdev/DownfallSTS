package charbosses.cards.red;

import charbosses.actions.util.CharbossDoCardQueueAction;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.status.EnBurn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;

public class EnImmolate extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Immolate";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Immolate");
    }

    public EnImmolate() {
        super(ID, cardStrings.NAME, "red/attack/immolate", 2, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.RARE, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 21;
        this.tags.add(CardTags.STRIKE);
        this.cardsToPreview = new Burn();
        this.tags.add(downfallMod.CHARBOSS_ATTACK);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        this.addToBot(new MakeTempCardInDiscardAction(new Burn(), 1));
        //don't need to create status in boss deck, will be added manually
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(7);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnImmolate();
    }
}