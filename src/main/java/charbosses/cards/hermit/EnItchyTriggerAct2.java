package charbosses.cards.hermit;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.colorless.EnHandOfGreedHermitNecro;
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

public class EnItchyTriggerAct2 extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:ItchyTrigger";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ItchyTrigger.ID);

    public EnItchyTriggerAct2() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/itchy_trigger.png", 2, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 12;
        isMultiDamage = true;
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), EnumPatch.HERMIT_GUN2));
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), EnumPatch.HERMIT_GUN2));
    }


    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        boolean addedWeak = false;
        if (AbstractCharBoss.boss.hand.group.stream().anyMatch(c -> c.cardID.equals(EnHoleUp.ID))) {
            AbstractCharBoss.boss.powers.add(0, new WeakPower(AbstractCharBoss.boss, 1, false));
            addedWeak = true;
        }
        super.calculateCardDamage(mo);
        if (addedWeak) {
            AbstractCharBoss.boss.powers.remove(0);
        }
    }

    @Override
    public void onSpecificTrigger() {
        for (AbstractCard q : AbstractCharBoss.boss.hand.group) {
            if (q instanceof EnHandOfGreedHermitNecro) {
                ((EnHandOfGreedHermitNecro) q).overrideThing();
                ((EnHandOfGreedHermitNecro) q).intentActive = false;
                ((EnHandOfGreedHermitNecro) q).createIntent();
            }
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
        return new EnItchyTriggerAct2();
    }
}
