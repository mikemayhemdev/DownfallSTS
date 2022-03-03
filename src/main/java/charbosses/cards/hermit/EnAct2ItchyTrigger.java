package charbosses.cards.hermit;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.cards.ItchyTrigger;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;

public class EnAct2ItchyTrigger extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:ItchyTrigger";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ItchyTrigger.ID);

    public EnAct2ItchyTrigger() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/itchy_trigger.png", 2, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 12;
        this.baseMagicNumber = magicNumber = 2;
        this.isMultiDamage = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), EnumPatch.HERMIT_GUN2));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        boolean addedWeak = false;
        if (AbstractCharBoss.boss.hand.group.stream().anyMatch(c -> c.cardID.equals(EnHoleUp.ID))) {
            AbstractDungeon.player.powers.add(0, new WeakPower(AbstractDungeon.player, 1, false));
            addedWeak = true;
        }
        super.calculateCardDamage(mo);
        if (addedWeak) {
            AbstractDungeon.player.powers.remove(0);
        }
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
        return new EnAct2ItchyTrigger();
    }
}
